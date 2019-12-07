package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import database.Database;
import lib.MessageUtils;
import lib.Tuple;
import model.*;
import net.ChannelPool;
import net.message.MessageType;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 返回一个Tuple
 * 若登陆成功，则Tuple中包含有两个对象，一个为Message，一个为Staff对象
 * 若登录失败，则Tuple中包含有一个对象，为Message
 */
public class LoginController implements Controller {
    /**
     * message body example:
     * {
     * "username": int,
     * "password": str
     * }
     **/
    @Override
    public Tuple process(String methodName, JSONObject params, Staff user) {
        // 判断是否手动构造业务login请求
        if (user != null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "禁止重复登陆", MessageType.CONNECT_RES));

        Database database = Database.INSTANCE;
        // 获取staff对象
        Staff staff = database.select("staffs", Integer.class, Staff.class)
                .filter(
                        (map, res) -> res.add(map.get(params.getInteger("username")))
                ).get();
        if (staff == null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "用户不存在", MessageType.CONNECT_RES));
        // 判断是否多端重复登陆
        if (ChannelPool.containStaff(staff) && ChannelPool.getChannel(staff).isActive())
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "禁止重复登陆", MessageType.CONNECT_RES));
        // 判断密码是否一致
        if (!staff.comparePassword(params.getString("password")))
            return new Tuple(MessageUtils.buildResponse(MessageUtils.FAIL, "密码错误", MessageType.CONNECT_RES));

        // 获取所在科室信息
        Department department = database.select("departments", Integer.class, Department.class).filter(
                (map, res) -> res.add(map.get(staff.getDepartment()))
        ).get();


        // 若科室有误，则账号不正常
        if (department == null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.FAIL, "账号内部错误", MessageType.CONNECT_RES));

        // 登陆成功，构造返回消息体
        Map<Integer, Department> departments = database.select("departments", Integer.class, Department.class).getRaw();

        JSONObject msg = new JSONObject().fluentPut("user",
                new JSONObject().fluentPut("name", staff.getName())
                        .fluentPut("title", staff.getTitle())
                        .fluentPut("department", staff.getDepartment()))
                .fluentPut("titles", database.selectAll("titles"))
                .fluentPut("departments", getDepartmentTree())
                .fluentPut("registrationLevels", database.selectAll("registrationLevels"));
        if (department.getClazz() == Department.OUTPATIENT) {
            Map<Long, MedicalRecord> medicalRecords = database.select("medicalRecords", Long.class, MedicalRecord.class).getRaw();
            msg.fluentPut("inspectionItems", database.selectAll("inspectionItems"))
                    .fluentPut("medicines", database.selectAll("medicines"))
                    .fluentPut("diseases", getDiseaseTree())
                    .fluentPut("patients",
                            database.select("visitingQueues", Integer.class, VisitingQueue.class).getRaw().getOrDefault(staff.getId(), new VisitingQueue())
                                    .getAllElement().stream()
                                    .filter(Objects::nonNull)
                                    .map(i -> {
                                                MedicalRecord medicalRecord = medicalRecords.get(i.getRegistration().getMedicalRecordsID());
                                                return new JSONObject()
                                                        .fluentPut("id", i.getRegistration().getId())
                                                        .fluentPut("medicalRecord", medicalRecord.getId())
                                                        .fluentPut("name", medicalRecord.getName())
                                                        .fluentPut("gender", medicalRecord.getGender())
                                                        .fluentPut("birthday", medicalRecord.getBirthday())
                                                        .fluentPut("urgent", i.getRegistration().isUrgent());
                                            }
                                    ).collect(Collectors.toList()));
        }

        if (department.getClazz() == Department.MEDICAL_TECHNIQUE)
            msg.fluentPut("inspectionItems", database.selectAll("inspectionItems"));

        if (department.getClazz() == Department.PHARMACY)
            msg.fluentPut("medicines", database.selectAll("medicines"))
                    .fluentPut("diseases", database.selectAll("diseases"));

        if (department.getClazz() == Department.FRONT_DESK)
            msg.fluentPut("doctors",
                    JSON.parse("{" + database.select("staffs", Integer.class, Staff.class).getRaw().values().stream().filter(
                            i -> departments.get(i.getDepartment()).getClazz() == Department.OUTPATIENT)
                            .map(Staff::toString).collect(Collectors.joining(",")) + "}"))
                    .fluentPut("inspectionItems", database.selectAll("inspectionItems"));

        if (department.getClazz() == Department.ADMIN)
            msg.fluentPut("diseases", getDiseaseTree());

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, msg, MessageType.CONNECT_RES), staff);
    }

    private JSONArray getDiseaseTree() {
        Map<Integer, Disease> diseases = Database.INSTANCE.select("diseases", Integer.class, Disease.class).getRaw();
        // 防止循环引用
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Disease.class, "id", "code", "name", "clazz", "children");
        return JSON.parseArray(JSON.toJSONString(diseases.values().stream()
                .filter(i -> i.getParent() == null).collect(Collectors.toList()), filter));
    }

    private JSONObject getDepartmentTree() {
        Map<Integer, Department> departments = Database.INSTANCE.select("departments", Integer.class, Department.class).getRaw();
        // 防止循环引用
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Department.class, "id", "name", "clazz");
        return JSON.parseObject(JSON.toJSONString(departments, filter));
    }
}
