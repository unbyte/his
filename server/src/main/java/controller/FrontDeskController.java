package controller;

import com.alibaba.fastjson.JSONObject;
import database.Database;
import lib.MessageUtils;
import lib.Tuple;
import model.*;

public class FrontDeskController implements Controller {
    @Override
    public Tuple process(String methodName, JSONObject params, Staff user) {
        // 先检查用户权限
        if (Database.INSTANCE.select("departments", Integer.class, Department.class).getRaw().get(user.getDepartment()).getClazz() != Department.FRONT_DESK)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "越权操作"));

        // 转发到具体的处理方法
        switch (methodName) {
            case "front-desk-register-new":
                return registerNew(params, user);
            case "front-desk-register-exist":
                return registerExist(params, user);
        }
        return null;
    }

    private Tuple registerNew(JSONObject params, Staff user) {
        // todo
        JSONObject medicalRecordParams, registrationParams;
        try {
            medicalRecordParams = params.getJSONObject("medicalRecord");
            registrationParams = params.getJSONObject("registration");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数非法"));
        }
        MedicalRecords newMedicalRecords = MedicalRecords.insert(medicalRecordParams.getString("name"),
                medicalRecordParams.getLong("birthday"),
                medicalRecordParams.getByte("gender"),
                medicalRecordParams.getString("IDNumber"),
                medicalRecordParams.getString("address"),
                null, null, null, null
        );

        Registration newRegistration = Registration.insert(newMedicalRecords.getId(), System.currentTimeMillis(),
                registrationParams.getInteger("department"),
                registrationParams.getInteger("doctor"),
                registrationParams.getDouble("cost"),
                Status.UNCONSUMED
        );

        Database.INSTANCE.insert("medicalRecords", newMedicalRecords.getId(), newMedicalRecords);
        Database.INSTANCE.insert("registrations", newRegistration.getId(), newRegistration);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, new JSONObject()
                .fluentPut("medicalRecordID", newMedicalRecords.getId())
                .fluentPut("registrationID", newRegistration.getId())));

    }

    private Tuple registerExist(JSONObject params, Staff user) {
        // todo 完成已有病历的挂号请求
        return new Tuple(null);
    }
}
