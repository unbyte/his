package controller;

import com.alibaba.fastjson.JSONObject;
import database.Database;
import lib.LogUtils;
import lib.MessageUtils;
import lib.Security;
import lib.Tuple;
import model.Registration;
import model.Staff;
import net.ChannelPool;
import net.message.MessageType;

import javax.swing.*;


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
    public Tuple process(JSONObject params, Staff user) {
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
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "密码错误", MessageType.CONNECT_RES));
        // 登陆成功，构造返回消息体
        JSONObject msg = new JSONObject().fluentPut("user",
                new JSONObject().fluentPut("name", staff.getName())
                        .fluentPut("title", staff.getTitle())
                        .fluentPut("department", staff.getDepartment()))
                .fluentPut("inspectionItems", database.selectAll("inspectionItems"))
                .fluentPut("medicines", database.selectAll("medicines"))
                .fluentPut("diseases", database.selectAll("diseases"))
                .fluentPut("registrationLevels", database.selectAll("registrationLevels"))
                .fluentPut("departments", database.selectAll("departments"))
                .fluentPut("titles", database.selectAll("titles"))
                .fluentPut("patients", database.select("newRegistrations", Long.class, Registration.class).filter(
                        (map, res) -> map.values().forEach(i -> {
                            if (i.getDoctorID() == staff.getId())
                                res.add(i);
                        })).toJSONString());
        LogUtils.info(msg);
        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, msg, MessageType.CONNECT_RES), staff);
    }


}
