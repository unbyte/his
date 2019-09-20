package controller;

import com.alibaba.fastjson.JSONObject;
import database.Database;
import lib.MessageUtils;
import lib.Tuple;
import model.MedicalRecords;
import model.Registration;
import model.Staff;

import java.util.List;
import java.util.stream.Collectors;

public class QueryController implements Controller {
    @Override
    public Tuple process(String methodName, JSONObject params, Staff user) {

        // 转发到具体的处理方法
        switch (methodName) {
            case "query-medical-record-by-id":
                return queryMedicalRecordByID(params);
            case "query-cancelable-by-medical-record-id":
                return queryCancelableByMedicalRecordID(params);
        }
        return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "目的行为不存在"));
    }

    private Tuple queryMedicalRecordByID(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        MedicalRecords medicalRecord = Database.INSTANCE.select("medicalRecords", Long.class, MedicalRecords.class).getRaw().get(id);

        // 病历对象不存在
        if (medicalRecord == null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该病历号不存在"));

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS,
                new JSONObject().fluentPut("id", medicalRecord.getId())
                        .fluentPut("name", medicalRecord.getName())
                        .fluentPut("gender", medicalRecord.getGender())
                        .fluentPut("birthday", medicalRecord.getBirthday())
                        .fluentPut("IDNumber", medicalRecord.getIDNumber())
                        .fluentPut("address", medicalRecord.getAddress())
        ));

    }

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
}
