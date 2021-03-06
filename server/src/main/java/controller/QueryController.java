package controller;

import com.alibaba.fastjson.JSONObject;
import database.Database;
import lib.MessageUtils;
import lib.Tuple;
import model.*;

import java.util.List;
import java.util.stream.Collectors;

public class QueryController implements Controller {
    @Override
    public Tuple process(String methodName, JSONObject params, Staff user) {

        // 转发到具体的处理方法
        switch (methodName) {
            case "query-medical-record-by-id":
                return queryMedicalRecordByID(params, user);
            case "query-cancelable-by-medical-record-id":
                return queryCancelableByMedicalRecordID(params);
            case "query-uncharged-items-by-medical-record-id":
                return queryUnchargedItemsByMedicalRecordID(params);
            case "query-paid-items-by-medical-record-id":
                return queryPaidItemsByMedicalRecordID(params);
            case "query-diagnosis-by-registration-id":
                return queryDiagnosisByRegistrationID(params);
            case "query-prescriptions-by-registration-id":
                return queryPrescriptionsByRegistrationID(params);
            case "query-prescriptions-by-medical-record-id":
                return queryPrescriptionsByMedicalRecordID(params);
        }
        return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "目的行为不存在"));
    }

    // 用病历id查询病历
    private Tuple queryMedicalRecordByID(JSONObject params, Staff user) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        MedicalRecord medicalRecord = Database.INSTANCE.select("medicalRecords", Long.class, MedicalRecord.class).getRaw().get(id);

        // 病历对象不存在
        if (medicalRecord == null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该病历号不存在"));

        byte userClazz = Database.INSTANCE.select("departments", Integer.class, Department.class).getRaw().get(user.getDepartment()).getClazz();

        if (userClazz == Department.OUTPATIENT)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS,
                    new JSONObject().fluentPut("id", medicalRecord.getId())
                            .fluentPut("name", medicalRecord.getName())
                            .fluentPut("gender", medicalRecord.getGender())
                            .fluentPut("birthday", medicalRecord.getBirthday())
                            .fluentPut("pastHistory", medicalRecord.getPastHistory())
                            .fluentPut("presentIllnessHistory", medicalRecord.getPresentIllnessHistory())
                            .fluentPut("allergyHistory", medicalRecord.getAllergyHistory())
                            .fluentPut("currentIllnessTreatment", medicalRecord.getCurrentIllnessTreatment())
            ));

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS,
                new JSONObject().fluentPut("id", medicalRecord.getId())
                        .fluentPut("name", medicalRecord.getName())
                        .fluentPut("gender", medicalRecord.getGender())
                        .fluentPut("birthday", medicalRecord.getBirthday())
                        .fluentPut("IDNumber", medicalRecord.getIDNumber())
                        .fluentPut("address", medicalRecord.getAddress())
        ));

    }

    // 用病历id查可退号挂号记录
    private Tuple queryCancelableByMedicalRecordID(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        // 查询挂号记录
        List<JSONObject> cancelableList = Database.INSTANCE.select("newRegistrations", Long.class, Registration.class).getRaw().values().stream()
                .filter(i -> i.getMedicalRecordsID() == id)
                .map(i -> new JSONObject().fluentPut("id", i.getId())
                        .fluentPut("time", i.getTime())
                        .fluentPut("doctor", i.getDoctorID())
                        .fluentPut("department", i.getDepartmentID())
                        .fluentPut("cost", i.getFee())
                        .fluentPut("status", i.getStatus()))
                .collect(Collectors.toList());


        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, cancelableList));
    }

    // 用病历id查询未收费项目
    private Tuple queryUnchargedItemsByMedicalRecordID(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }


        // 查询未缴费的检查检验项目
        List<JSONObject> unchargedInspectionRecords = Database.INSTANCE.select("inspectionRecords", Long.class, InspectionRecord.class)
                .getRaw().values().stream()
                .filter(i -> i.getMedicalRecordID() == id)
                .filter(i -> i.getStatus() == Status.UNPAID)
                .map(i -> new JSONObject()
                        .fluentPut("id", i.getId())
                        .fluentPut("clazz", i.getTime())
                        .fluentPut("item", i.getItem())
                        .fluentPut("cost", i.getFee())
                )
                .collect(Collectors.toList());

        List<JSONObject> unchargedPrescriptions = Database.INSTANCE.select("prescriptions", Long.class, Prescription.class).getRaw().values().stream()
                .filter(i -> i.getMedicalRecordID() == id)
                .filter(i -> i.getStatus() == Status.UNPAID)
                .map(i -> new JSONObject()
                        .fluentPut("id", i.getId())
                        .fluentPut("clazz", i.getClazz())
                        .fluentPut("medicineListSize", i.getMedicineList().size())
                        .fluentPut("cost", i.getFee())
                )
                .collect(Collectors.toList());

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, new JSONObject()
                .fluentPut("inspectionRecords", unchargedInspectionRecords)
                .fluentPut("prescriptions", unchargedPrescriptions)));
    }

    // 用病历id查询已缴费项目
    private Tuple queryPaidItemsByMedicalRecordID(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }
        // 查询已付费的检查检验项目
        List<JSONObject> chargedInspectionRecords = Database.INSTANCE.select("inspectionRecords", Long.class, InspectionRecord.class).getRaw().values().stream()
                .filter(i -> i.getMedicalRecordID() == id)
                .filter(i -> i.getStatus() > Status.UNPAID && i.getStatus() < Status.REFUNDED)
                .map(i -> new JSONObject()
                        .fluentPut("id", i.getId())
                        .fluentPut("clazz", i.getTime())
                        .fluentPut("item", i.getItem())
                        .fluentPut("cost", i.getFee())
                        .fluentPut("status", i.getStatus())
                )
                .collect(Collectors.toList());

        // 查询已付费的处方
        List<JSONObject> chargedPrescriptions = Database.INSTANCE.select("prescriptions", Long.class, Prescription.class).getRaw().values().stream()
                .filter(i -> i.getMedicalRecordID() == id)
                .filter(i -> i.getStatus() > Status.UNPAID && i.getStatus() < Status.REFUNDED)
                .map(i -> new JSONObject()
                        .fluentPut("id", i.getId())
                        .fluentPut("clazz", i.getClazz())
                        .fluentPut("medicineListSize", i.getMedicineList().size())
                        .fluentPut("cost", i.getFee())
                        .fluentPut("status", i.getStatus())
                )
                .collect(Collectors.toList());

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, new JSONObject()
                .fluentPut("inspectionRecords", chargedInspectionRecords)
                .fluentPut("prescriptions", chargedPrescriptions)));
    }

    // 用挂号记录查询诊断信息
    private Tuple queryDiagnosisByRegistrationID(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Diagnosis diagnosis = Database.INSTANCE.select("diagnoses", Long.class, Diagnosis.class).getRaw()
                .get(id);

        if (diagnosis == null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该诊断不存在"));

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, diagnosis));
    }

    // 用挂号记录查询药单
    private Tuple queryPrescriptionsByRegistrationID(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        List<Prescription> prescriptions = Database.INSTANCE.select("prescriptions", Long.class, Prescription.class).getRaw().values().stream()
                .filter(i -> i.getRegistrationID() == id)
                .filter(i -> i.getStatus() < Status.REFUNDED)
                .collect(Collectors.toList());

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, prescriptions));
    }

    // 用病历号查询药单
    private Tuple queryPrescriptionsByMedicalRecordID(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        List<Prescription> prescriptions = Database.INSTANCE.select("prescriptions", Long.class, Prescription.class).getRaw().values().stream()
                .filter(i -> i.getMedicalRecordID() == id)
                .filter(i -> i.getStatus() < Status.REFUNDED)
                .collect(Collectors.toList());

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, prescriptions));
    }
}
