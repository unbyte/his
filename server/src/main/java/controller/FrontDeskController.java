package controller;

import com.alibaba.fastjson.JSONObject;
import database.Database;
import lib.MessageUtils;
import lib.Tuple;
import model.*;

import java.util.Map;

public class FrontDeskController implements Controller {
    @Override
    public Tuple process(String methodName, JSONObject params, Staff user) {
        // 先检查用户权限
        if (Database.INSTANCE.select("departments", Integer.class, Department.class).getRaw().get(user.getDepartment()).getClazz() != Department.FRONT_DESK)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "越权操作"));

        // 转发到具体的处理方法
        switch (methodName) {
            case "front-desk-register-new":
                return registerNew(params);
            case "front-desk-register-exist":
                return registerExist(params);
            case "front-desk-cancel":
                return cancelRegistration(params);
            case "front-desk-charge":
                return chargeItems(params);
        }
        return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "目的行为不存在"));
    }

    /* 处理新建病历的挂号请求 */
    private Tuple registerNew(JSONObject params) {
        // todo
        JSONObject medicalRecordParams, registrationParams;
        try {
            medicalRecordParams = params.getJSONObject("medicalRecord");
            registrationParams = params.getJSONObject("registration");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }
        // 新建病历
        MedicalRecords newMedicalRecords = MedicalRecords.insert(medicalRecordParams.getString("name"),
                medicalRecordParams.getLong("birthday"),
                medicalRecordParams.getByte("gender"),
                medicalRecordParams.getString("IDNumber"),
                medicalRecordParams.getString("address"),
                null, null, null, null
        );

        // 新建挂号
        Registration newRegistration = Registration.insert(newMedicalRecords.getId(), System.currentTimeMillis(),
                registrationParams.getInteger("department"),
                registrationParams.getInteger("doctor"),
                registrationParams.getDouble("cost"),
                Status.UNPAID
        );

        Database.INSTANCE.insert("medicalRecords", newMedicalRecords.getId(), newMedicalRecords);
        Database.INSTANCE.insert("newRegistrations", newRegistration.getId(), newRegistration);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, new JSONObject()
                .fluentPut("medicalRecordID", newMedicalRecords.getId())
                .fluentPut("registrationID", newRegistration.getId())
                .fluentPut("cost", registrationParams.getDouble("cost"))));

    }

    /* 处理已有病历的挂号请求 */
    private Tuple registerExist(JSONObject params) {
        JSONObject registrationParams;
        long medicalRecordID;
        try {
            medicalRecordID = params.getLong("medicalRecordID");
            registrationParams = params.getJSONObject("registration");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        // 新建挂号
        Registration newRegistration = Registration.insert(medicalRecordID, System.currentTimeMillis(),
                registrationParams.getInteger("department"),
                registrationParams.getInteger("doctor"),
                registrationParams.getDouble("cost"),
                Status.UNPAID
        );

        Database.INSTANCE.insert("newRegistrations", newRegistration.getId(), newRegistration);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, new JSONObject()
                .fluentPut("medicalRecordID", medicalRecordID)
                .fluentPut("registrationID", newRegistration.getId())
                .fluentPut("cost", registrationParams.getDouble("cost"))
        ));
    }

    /* 取消挂号 */
    private Tuple cancelRegistration(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Map<Long, Registration> registrations = Database.INSTANCE.select("newRegistrations", Long.class, Registration.class).getRaw();
        Registration registration = registrations.get(id);

        if (registration == null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该挂号记录不存在"));

        registration.setStatus(Status.REFUNDED);

        // 从newRegistrations挪到registrations里
        registrations.remove(registration.getId());
        Database.INSTANCE.insert("registrations", registration.getId(), registration);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "退号成功"));
    }

    /* 收费 */
    private Tuple chargeItems(JSONObject params) {
        byte type; // 0 - prescription/1 - inspectionRecords
        long id;
        try {
            type = params.getByte("type");
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        if (type == 0) {
            Prescription prescriptions = Database.INSTANCE.select("prescriptions", Long.class, Prescription.class).getRaw()
                    .get(id);
            if (prescriptions == null)
                return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该收费记录不存在"));
            prescriptions.setStatus(Status.UNCONSUMED);
        } else if (type == 1) {
            InspectionRecord inspectionRecords = Database.INSTANCE.select("inspectionRecords", Long.class, InspectionRecord.class).getRaw()
                    .get(id);
            if (inspectionRecords == null)
                return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该收费记录不存在"));
            inspectionRecords.setStatus(Status.UNCONSUMED);
        } else
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "该收费类型不存在"));
        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "收费成功"));
    }
}
