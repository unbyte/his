package controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import database.Database;
import lib.MessageUtils;
import lib.SortUtils;
import lib.Tuple;
import model.*;

import java.text.Collator;
import java.util.*;
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
            case "query-patients-by-disease-id":
                return queryRegistrationByDiseaseID(params);
            case "query-patients-by-disease":
                return queryRegistrationByDisease(params);
            case "query-registrations-by-medical-record-id":
                return queryRegistrationsByMedicalRecordID(params);
            case "query-medical-record-by-department-id":
                return queryMedicalRecordByDepartment(params);
            case "query-patients-by-department-id":
                return queryPatientsByDepartmentID(params);
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

    // 用病历id查询历次挂号记录
    private Tuple queryRegistrationsByMedicalRecordID(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Map<Integer, Disease> diseases = Database.INSTANCE.select("diseases", Integer.class, Disease.class).getRaw();
        Map<Long, Long[]> registrationIndexes = Database.INSTANCE.select("registrationIndexes", Long.class, Long[].class).getRaw();
        Map<Long, Diagnosis> diagnoses = Database.INSTANCE.select("diagnoses", Long.class, Diagnosis.class).getRaw();
        Map<Long, Registration> allRegistrations = Database.INSTANCE.select("registrations", Long.class, Registration.class).getRaw();
        Map<Integer, Staff> staffs = Database.INSTANCE.select("staffs", Integer.class, Staff.class).getRaw();

        JSONArray result = new JSONArray();

        if (registrationIndexes.containsKey(id)) {
            Arrays.stream(registrationIndexes.get(id))
                    .filter(Objects::nonNull)
                    .map(i -> allRegistrations.getOrDefault(i, null))
                    .map(i -> {
                        if (i == null || !diagnoses.containsKey(i.getId())) return null;
                        return new JSONObject()
                                .fluentPut("registrationTime", i.getTime())
                                .fluentPut("diseaseName", diseases.get(diagnoses.get(i.getId()).getDisease().get(0)).getName())
                                .fluentPut("departmentID", i.getDepartmentID())
                                .fluentPut("doctorName", staffs.get(i.getDoctorID()).getName());
                    })
                    .filter(Objects::nonNull)
                    .forEach(result::add);
        }


        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, result));
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

    // 用病种号查询就诊记录
    private Tuple queryRegistrationByDisease(JSONObject params) {
        Integer id;
        String order;
        try {
            id = params.getInteger("id");
            order = params.getString("order");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Map<Integer, Disease> diseases = Database.INSTANCE.select("diseases", Integer.class, Disease.class).getRaw();
        if (!diseases.containsKey(id))
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该病种不存在"));

        // 获取病种及其子病种的id
        List<Integer> disease = getChildrenDiseaseID(id, diseases);

        Map<Long, MedicalRecord> medicalRecords = Database.INSTANCE.select("medicalRecords", Long.class, MedicalRecord.class).getRaw();
        Map<Long, Registration> registrations = Database.INSTANCE.select("registrations", Long.class, Registration.class).getRaw();
        Map<Long, Diagnosis> diagnoses = Database.INSTANCE.select("diagnoses", Long.class, Diagnosis.class).getRaw();
        Map<Integer, Staff> staffs = Database.INSTANCE.select("staffs", Integer.class, Staff.class).getRaw();

        switch (order) {
            case "id":
                Long[] registrationsArray =
                        registrations.values().stream()
                                .map(Registration::getId)
                                .toArray(Long[]::new);
                SortUtils.mergeSort(registrationsArray,
                        0, registrationsArray.length - 1);
                break;
            case "name":
                Collator instance = Collator.getInstance(Locale.CHINA);
                List<Registration> registrationsList = List.copyOf(registrations.values());
                registrationsList.sort(instance);
                break;
        }

        JSONArray result = new JSONArray();
        diagnoses.values().stream()
                .filter(i -> i.getDisease().stream().anyMatch(disease::contains))
                .map(i -> {
                    Registration r = registrations.get(i.getId());
                    MedicalRecord mr = medicalRecords.get(r.getMedicalRecordsID());
                    return new JSONObject().fluentPut("diseaseName", diseases.get(i.getDisease().get(0)).getName())
                            // 这里写死了取第一个，其实应该取出对应于要求病种的病，懒得写了
                            .fluentPut("registrationTime", r.getTime())
                            .fluentPut("departmentID", r.getDepartmentID())
                            .fluentPut("patientName", mr.getName())
                            .fluentPut("patientBirth", mr.getBirthday())
                            .fluentPut("patientGender", mr.getGender())
                            .fluentPut("doctorName", staffs.get(r.getDoctorID()).getName());
                })
                .forEach(result::add);

        // todo 做假排序
        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, result));
    }

    private Tuple queryRegistrationByDiseaseID(JSONObject params) {
        Integer id;
        String order;
        try {
            id = params.getInteger("id");
            order = params.getString("order");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Map<Integer, Disease> diseases = Database.INSTANCE.select("diseases", Integer.class, Disease.class).getRaw();
        if (!diseases.containsKey(id))
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该病种不存在"));

        // 获取病种及其子病种的id
        List<Integer> disease = getChildrenDiseaseID(id, diseases);

        Map<Long, MedicalRecord> medicalRecords = Database.INSTANCE.select("medicalRecords", Long.class, MedicalRecord.class).getRaw();
        Map<Long, Registration> registrations = Database.INSTANCE.select("registrations", Long.class, Registration.class).getRaw();
        Map<Long, Diagnosis> diagnoses = Database.INSTANCE.select("diagnoses", Long.class, Diagnosis.class).getRaw();
        Map<Integer, Staff> staffs = Database.INSTANCE.select("staffs", Integer.class, Staff.class).getRaw();

        JSONArray result = new JSONArray();
        diagnoses.values().stream()
                .filter(i -> i.getDisease().stream().anyMatch(disease::contains))
                .filter(i -> i.getStatus() == Diagnosis.FINAL)
                .map(i -> {
                    Registration r = registrations.get(i.getId());
                    MedicalRecord mr = medicalRecords.get(r.getMedicalRecordsID());
                    return new JSONObject().fluentPut("diseaseName", diseases.get(i.getDisease().get(0)).getName())
                            // 这里写死了取第一个，其实应该取出对应于要求病种的病，懒得写了
                            .fluentPut("registrationTime", r.getTime())
                            .fluentPut("departmentID", r.getDepartmentID())
                            .fluentPut("patientName", mr.getName())
                            .fluentPut("patientBirth", mr.getBirthday())
                            .fluentPut("patientGender", mr.getGender())
                            .fluentPut("doctorName", staffs.get(r.getDoctorID()).getName());
                })
                .forEach(result::add);

        // todo 做假排序
        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, result));
    }

    // 用科室号查询就诊记录
    private Tuple queryPatientsByDepartmentID(JSONObject params) {
        Integer id;
        String order;
        try {
            id = params.getInteger("id");
            order = params.getString("order");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Map<Integer, Department> departments = Database.INSTANCE.select("departments", Integer.class, Department.class).getRaw();
        if (!departments.containsKey(id))
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该科室不存在"));

        // 获取科室及其子科室的id
        List<Integer> department = getChildDepartmentID(id, departments);

        Map<Long, MedicalRecord> medicalRecords = Database.INSTANCE.select("medicalRecords", Long.class, MedicalRecord.class).getRaw();
        Map<Long, Registration> registrations = Database.INSTANCE.select("registrations", Long.class, Registration.class).getRaw();

        switch (order) {
            case "id":
                Long[] registrationsArray =
                        registrations.values().stream()
                                .map(Registration::getId)
                                .toArray(Long[]::new);
                SortUtils.mergeSort(registrationsArray,
                        0, registrationsArray.length - 1);
                break;
            case "name":
                Collator instance = Collator.getInstance(Locale.CHINA);
                List<Registration> registrationsList = List.copyOf(registrations.values());
                registrationsList.sort(instance);
                break;
        }

        JSONArray result = new JSONArray();
        registrations.values().stream()
                .filter(i -> department.contains(i.getDepartmentID()))
                .map(i -> {
                    MedicalRecord mr = medicalRecords.get(i.getMedicalRecordsID());
                    return new JSONObject()
                            .fluentPut("id", mr.getId())
                            .fluentPut("name", mr.getName())
                            .fluentPut("birth", mr.getBirthday())
                            .fluentPut("gender", mr.getGender());
                })
                .forEach(result::add);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, result));
    }

    private Tuple queryMedicalRecordByDepartment(JSONObject params) {
        Integer id;
        String order;
        try {
            id = params.getInteger("id");
            order = params.getString("order");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Map<Integer, Department> departments = Database.INSTANCE.select("departments", Integer.class, Department.class).getRaw();
        if (!departments.containsKey(id))
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该科室不存在"));

        // 获取科室及其子科室的id
        List<Integer> department = getChildDepartmentID(id, departments);

        Map<Long, MedicalRecord> medicalRecords = Database.INSTANCE.select("medicalRecords", Long.class, MedicalRecord.class).getRaw();
        Map<Long, Registration> registrations = Database.INSTANCE.select("registrations", Long.class, Registration.class).getRaw();

        JSONArray result = new JSONArray();
        registrations.values().stream()
                .filter(i -> department.contains(i.getDepartmentID()))
                .map(i -> {
                    MedicalRecord mr = medicalRecords.get(i.getMedicalRecordsID());
                    return new JSONObject()
                            .fluentPut("id", mr.getId())
                            .fluentPut("name", mr.getName())
                            .fluentPut("birth", mr.getBirthday())
                            .fluentPut("gender", mr.getGender());
                })
                .forEach(result::add);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, result));
    }

    // 辅助方法，遍历树节点
    private List<Integer> getChildrenDiseaseID(Integer id, Map<Integer, Disease> disease) {
        if (disease.get(id).getChildren().size() == 0)
            return new ArrayList<>() {{
                add(id);
            }};
        List<Integer> children = disease.get(id).getChildren().stream()
                .map(i -> getChildrenDiseaseID(i.getId(), disease))
                .reduce((x, y) -> {
                    x.addAll(y);
                    return x;
                })
                .get();
        children.add(id);
        return children;
    }

    private List<Integer> getChildDepartmentID(Integer id, Map<Integer, Department> department) {
        if (department.get(id).getChildren().size() == 0)
            return new ArrayList<>() {{
                add(id);
            }};
        List<Integer> children = department.get(id).getChildren().stream()
                .map(i -> getChildDepartmentID(i.getId(), department))
                .reduce((x, y) -> {
                    x.addAll(y);
                    return x;
                })
                .get();
        children.add(id);
        return children;
    }
}
