package controller;

import com.alibaba.fastjson.JSONObject;
import database.Database;
import lib.MessageUtils;
import lib.Tuple;
import model.*;

import java.util.*;
import java.util.stream.Collectors;

public class TechniqueController implements Controller {
    @Override
    public Tuple process(String methodName, JSONObject params, Staff user) {
        // 先检查用户权限
        if (Database.INSTANCE.select("departments", Integer.class, Department.class).getRaw().get(user.getDepartment()).getClazz() != Department.MEDICAL_TECHNIQUE)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "越权操作"));

        // 转发到具体的处理方法
        switch (methodName) {
            case "medical-technique-check":
                return check(params);
        }
        return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "目的行为不存在"));
    }

    private Tuple check(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Map<Long, Diagnosis> diagnoses = Database.INSTANCE.select("diagnoses", Long.class, Diagnosis.class).getRaw();
        Map<Long, Long[]> registrationIndexes = Database.INSTANCE.select("registrationIndexes", Long.class, Long[].class).getRaw();
        Map<Long, Registration> allRegistrations = Database.INSTANCE.select("registrations", Long.class, Registration.class).getRaw();

        Long[] registrations = registrationIndexes.get(id);

        if (registrations == null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "无就诊记录"));

        Diagnosis diagnosis = Arrays.stream(registrations)
                .map(i -> diagnoses.getOrDefault(i, null))
                .filter(Objects::nonNull)
                .filter(i -> i.getStatus() == Diagnosis.PRESUMPTIVE)
                .findFirst()
                .orElse(null);

        if (diagnosis == null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "没有待检查项目"));


        // 把挂号记录加回队列里，b类
        Map<Long, MedicalRecord> medicalRecords = Database.INSTANCE.select("medicalRecords", Long.class, MedicalRecord.class).getRaw();
        Map<Integer, VisitingQueue> visitingQueues = Database.INSTANCE.select("visitingQueues", Integer.class, VisitingQueue.class).getRaw();

        Registration registration = allRegistrations.get(diagnosis.getId());

        if (!visitingQueues.containsKey(registration.getDoctorID()))
            visitingQueues.put(registration.getDoctorID(), new VisitingQueue());

        VisitingQueue visitingQueue = visitingQueues.get(registration.getDoctorID());
        visitingQueue.insert(registration);

        List<VisitingQueueElement> allElement = new ArrayList<>(visitingQueue.getAllElement());

        List<JSONObject> collect = allElement.stream()
                .filter(Objects::nonNull)
                .map(i -> {
                            MedicalRecord medicalRecord = medicalRecords.get(i.getRegistration().getMedicalRecordsID());
                            return new JSONObject()
                                    .fluentPut("id", i.getRegistration().getId())
                                    .fluentPut("medicalRecord", medicalRecord.getId())
                                    .fluentPut("name", medicalRecord.getName())
                                    .fluentPut("gender", medicalRecord.getGender())
                                    .fluentPut("birthday", medicalRecord.getBirthday())
                                    .fluentPut("power", i.getPower())
                                    .fluentPut("urgent", i.getRegistration().isUrgent());
                        }
                ).collect(Collectors.toList());

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "检查成功，请病人复诊"),
                MessageUtils.buildPush("new registration",
                        collect),
                Database.INSTANCE.select("staffs", Integer.class, Staff.class).getRaw().get(registration.getDoctorID()));
    }
}
