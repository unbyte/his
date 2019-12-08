package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import database.Database;
import lib.MessageUtils;
import lib.Tuple;
import model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class OutpatientController implements Controller {
    @Override
    public Tuple process(String methodName, JSONObject params, Staff user) {
        // 检查权限
        if (Database.INSTANCE.select("departments", Integer.class, Department.class)
                .getRaw().get(user.getDepartment()).getClazz() != Department.OUTPATIENT)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "越权操作"));

        // 转发到具体的处理方法
        switch (methodName) {
            case "outpatient-update-medical-record-info":
                return updateMedicalRecordInfo(params);
            case "outpatient-presumptive-diagnosis":
                return presumptiveDiagnosis(params);
            case "outpatient-final-diagnosis":
                return finalDiagnosis(params);
            case "outpatient-complete":
                return complete(params);
            case "outpatient-publish-prescription":
                return publishPrescription(params);
            case "outpatient-save-temp-prescription":
                return saveTempPrescription(params);
            case "outpatient-cancel-prescription":
                return cancelPrescription(params);
        }
        return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "目的行为不存在"));
    }


    // 更新病历信息
    private Tuple updateMedicalRecordInfo(JSONObject params) {
        long id;
        String pastHistory, allergyHistory, presentIllnessHistory, currentIllnessTreatment;
        try {
            id = params.getLong("id");
            pastHistory = params.getString("pastHistory");
            allergyHistory = params.getString("allergyHistory");
            presentIllnessHistory = params.getString("presentIllnessHistory");
            currentIllnessTreatment = params.getString("currentIllnessTreatment");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        // 获取病历对象
        MedicalRecord medicalRecord = Database.INSTANCE.select("medicalRecords", Long.class, MedicalRecord.class).getRaw().get(id);

        if (medicalRecord == null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该病历不存在"));

        medicalRecord.setPastHistory(pastHistory)
                .setAllergyHistory(allergyHistory)
                .setCurrentIllnessTreatment(currentIllnessTreatment)
                .setPresentIllnessHistory(presentIllnessHistory);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "保存成功"));
    }

    // 初诊
    private Tuple presumptiveDiagnosis(JSONObject params) {
        long id;
        String complaint;
        byte clazz;
        List<Integer> disease;
        try {
            id = params.getLong("id");
            complaint = params.getString("complaint");
            clazz = params.getByte("clazz");
            disease = JSON.parseArray(params.getJSONArray("disease").toJSONString(), Integer.class);
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        // 新建一个诊断对象
        Diagnosis diagnosis = new Diagnosis(id, complaint, clazz, disease, "", "", "", Diagnosis.PRESUMPTIVE);
        Database.INSTANCE.insert("diagnoses", diagnosis.getId(), diagnosis);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "初诊成功"));
    }

    // 终诊
    private Tuple finalDiagnosis(JSONObject params) {
        long id;
        List<Integer> disease;
        String suggestion;
        String note;
        String judgement;
        try {
            id = params.getLong("id");
            suggestion = params.getString("suggestion");
            note = params.getString("note");
            judgement = params.getString("judgement");
            disease = JSON.parseArray(params.getJSONArray("disease").toJSONString(), Integer.class);
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Diagnosis diagnosis = Database.INSTANCE.select("diagnoses", Long.class, Diagnosis.class).getRaw()
                .get(id);

        if (diagnosis == null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该诊断不存在"));

        diagnosis.setDisease(disease).setSuggestion(suggestion).setNote(note).setJudgement(judgement).setStatus(Diagnosis.FINAL);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "保存成功"));
    }

    // 完成诊断
    private Tuple complete(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Diagnosis diagnoses = Database.INSTANCE.select("diagnoses", Long.class, Diagnosis.class).getRaw()
                .get(id);

        if (diagnoses == null /*|| diagnoses.getStatus() == Diagnosis.PRESUMPTIVE*/)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.FAIL, "尚未完成诊断"));

        Map<Long, Registration> newRegistrations = Database.INSTANCE.select("newRegistrations", Long.class, Registration.class).getRaw();
        Map<Long, Registration> registrations = Database.INSTANCE.select("registrations", Long.class, Registration.class).getRaw();

        Registration registration;
        // 移动
        if (newRegistrations.containsKey(id)) {
            registration = newRegistrations.get(id);
            registration.setStatus(Status.CONSUMED);
            newRegistrations.remove(id);
            registrations.put(id, registration);
        } else {
            registration = registrations.get(id);
        }

        // 从队列中移除
        VisitingQueue visitingQueue = Database.INSTANCE.select("visitingQueues", Integer.class, VisitingQueue.class).getRaw().get(registration.getDoctorID());
        if (visitingQueue.peek().getId() == registration.getId()) {
            visitingQueue.get();
        } else {
            visitingQueue.remove(registration);
        }


        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "已完成诊断"));
    }


    // 保存药单（尚未发布）
    private Tuple saveTempPrescription(JSONObject params) {
        long id, medicalRecord, registration;
        String name;
        byte clazz;
        HashSet<PrescriptionItem> medicineList;
        try {
            id = params.getLong("id");
            medicalRecord = params.getLong("medicalRecord");
            name = params.getString("name");
            clazz = params.getByte("clazz");
            medicineList = params.getObject("medicineList", new TypeReference<HashSet<PrescriptionItem>>() {
            });
            registration = params.getLong("registration");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        // 根据国家规定，每个处方中最多只能包含5种药品，如果超过需要新增处方
        if (medicineList.size() > 5)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "单个处方中最多包含5种药物"));

        Map<Integer, Medicine> medicines = Database.INSTANCE.select("medicines", Integer.class, Medicine.class).getRaw();

        double fee = medicineList.stream().map(i -> i.getAmount() * medicines.get(i.getMedicineID()).getPrice()).mapToDouble(i -> i).sum();

        if (id == -1) {
            // 药单id不存在，即是新建的药单
            Prescription prescription = Prescription.insert(medicalRecord, registration, name, clazz, medicineList, fee, Status.TEMPORARY);
            Database.INSTANCE.insert("prescriptions", prescription.getId(), prescription);
        } else {
            // 有药单id，是已有的药单
            Prescription prescription = Database.INSTANCE.select("prescriptions", Long.class, Prescription.class).getRaw().get(id);
            if (prescription == null)
                return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该药单不存在"));

            if (prescription.getStatus() != Status.TEMPORARY)
                return new Tuple(MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "不可暂存状态"));
            prescription.setMedicineList(medicineList).setFee(fee).setName(name);
        }

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "暂存成功"));
    }

    // 发布药单
    private Tuple publishPrescription(JSONObject params) {
        long id, medicalRecord, registration;
        byte clazz;
        String name;
        HashSet<PrescriptionItem> medicineList;
        try {
            id = params.getLong("id");
            medicalRecord = params.getLong("medicalRecord");
            clazz = params.getByte("clazz");
            name = params.getString("name");
            registration = params.getLong("registration");
            medicineList = params.getObject("medicineList", new TypeReference<HashSet<PrescriptionItem>>() {
            });
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        // 根据国家规定，每个处方中最多只能包含5种药品，如果超过需要新增处方
        if (medicineList.size() > 5)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "单个处方中最多包含5种药物"));


        Map<Integer, Medicine> medicines = Database.INSTANCE.select("medicines", Integer.class, Medicine.class).getRaw();

        // 减掉库存
        AtomicBoolean usedOut = new AtomicBoolean(false);
        medicineList.forEach(i -> {
            Medicine medicine = medicines.get(i.getMedicineID());
            if (medicine.getStock() < i.getAmount()) {
                usedOut.set(true);
                i.setAmount(medicine.getStock());
            }

            medicine.setStock(medicine.getStock() - i.getAmount());
        });

        double fee = medicineList.stream().map(i -> i.getAmount() * medicines.get(i.getMedicineID()).getPrice()).mapToDouble(i -> i).sum();

        if (id == -1) {
            // 是新建药单
            Prescription prescription = Prescription.insert(medicalRecord, registration, name, clazz, medicineList, fee, Status.UNPAID);
            System.out.println(prescription);
            Database.INSTANCE.insert("prescriptions", prescription.getId(), prescription);
        } else {
            Prescription prescription = Database.INSTANCE.select("prescriptions", Long.class, Prescription.class).getRaw().get(id);
            if (prescription.getStatus() != Status.TEMPORARY)
                return new Tuple(MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "不可二次开立"));

            prescription.setName(name).setMedicineList(medicineList).setFee(fee).setStatus(Status.UNPAID);
        }

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "开立成功".concat(usedOut.get() ? "，但药物库存不足，已自动修改数量" : "！")));
    }

    // 取消药单
    private Tuple cancelPrescription(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Prescription prescription = Database.INSTANCE.select("prescriptions", Long.class, Prescription.class).getRaw()
                .get(id);
        if (prescription == null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该药单不存在"));

        if (prescription.getStatus() <= Status.UNPAID)
            prescription.setStatus(Status.CANCELED_WITHOUT_PAID);
        else
            prescription.setStatus(Status.CANCELED);

        Map<Integer, Medicine> medicines = Database.INSTANCE.select("medicines", Integer.class, Medicine.class).getRaw();

        // 把库存加回去
        prescription.getMedicineList().forEach(i -> {
            Medicine medicine = medicines.get(i.getMedicineID());
            medicine.setStock(medicines.get(i.getMedicineID()).getStock() + i.getAmount());
        });

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "作废成功"));
    }
}
