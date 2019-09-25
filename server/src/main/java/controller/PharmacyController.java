package controller;

import com.alibaba.fastjson.JSONObject;
import database.Database;
import lib.MessageUtils;
import lib.Tuple;
import model.Department;
import model.Prescription;
import model.Staff;
import model.Status;

public class PharmacyController implements Controller {

    @Override
    public Tuple process(String methodName, JSONObject params, Staff user) {
        // 检查权限
        if (Database.INSTANCE.select("departments", Integer.class, Department.class)
                .getRaw().get(user.getDepartment()).getClazz() != Department.PHARMACY)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "越权操作"));

        // 转发到具体的处理方法
        switch (methodName) {
            case "pharmacy-dispense":
                dispense(params);
            case "pharmacy-withdrawal":
                withdrawal(params);
        }
        return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "目的行为不存在"));
    }

    private Tuple dispense(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Prescription prescription = Database.INSTANCE.select("prescriptions", Long.class, Prescription.class).getRaw().get(id);
        if (prescription == null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "目标处方不存在"));

        if (prescription.getStatus() != Status.UNCONSUMED)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "该处方状态不允许取药"));

        prescription.setStatus(Status.CONSUMED);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "开药成功"));
    }

    private Tuple withdrawal(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Prescription prescription = Database.INSTANCE.select("prescriptions", Long.class, Prescription.class).getRaw().get(id);

        if (prescription == null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "目标处方不存在"));

        if (prescription.getStatus() != Status.CONSUMED)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "该处方状态不允许取药"));

        prescription.setStatus(Status.CANCELED);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "退药成功"));
    }
}
