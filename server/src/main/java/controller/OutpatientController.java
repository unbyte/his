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

    private Tuple complete(JSONObject params) {
        long id;
        try {
            id = params.getLong("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Diagnosis diagnoses = Database.INSTANCE.select("diagnoses", Long.class, Diagnosis.class).getRaw()
                .get(id);

        if (diagnoses == null || diagnoses.getStatus() == Diagnosis.PRESUMPTIVE)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "尚未完成诊断"));

        Registration registration = Database.INSTANCE.select("newRegistrations", Long.class, Registration.class).getRaw()
                .get(id);

        registration.setStatus(Status.CONSUMED);

        // 移动
        Database.INSTANCE.select("newRegistrations", Long.class, Registration.class).getRaw().remove(registration.getId());
        Database.INSTANCE.insert("registrations", registration.getId(), registration);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "已完成诊断"));
    }


    private Tuple saveTempPrescription(JSONObject params) {
        long id, medicalRecord, prescriptionID;
        HashSet<PrescriptionItem> prescriptionItems;
        try {
            id = params.getLong("id");
            medicalRecord = params.getLong("medicalRecord");
            prescriptionItems = params.getObject("prescription", new TypeReference<HashSet<PrescriptionItem>>() {
            });
            prescriptionID = params.getLong("prescriptionID");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Map<Integer, Medicine> medicines = Database.INSTANCE.select("medicines", Integer.class, Medicine.class).getRaw();

        double fee = prescriptionItems.stream().map(i -> i.getAmount() * medicines.get(i.getMedicineID()).getPrice()).mapToDouble(i -> i).sum();

        if (prescriptionID == -1) {
            Prescription prescription = Prescription.insert(medicalRecord, id, 1, prescriptionItems, fee, Status.TEMPORARY);
            if (prescription == null)
                return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该药单不存在"));
            Database.INSTANCE.insert("prescriptions", prescription.getId(), prescription);
        } else {
            Prescription prescription = Database.INSTANCE.select("prescriptions", Long.class, Prescription.class).getRaw().get(prescriptionID);
            if (prescription == null)
                return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该药单不存在"));

            if (prescription.getStatus() != Status.TEMPORARY)
                return new Tuple(MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "状态无法作废"));
            prescription.setMedicineList(prescriptionItems).setFee(fee);
        }

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "暂存成功"));
    }

    private Tuple publishPrescription(JSONObject params) {
        long id, medicalRecord, prescriptionID;
        HashSet<PrescriptionItem> prescriptionItems;
        try {
            id = params.getLong("id");
            medicalRecord = params.getLong("medicalRecord");
            prescriptionID = params.getLong("prescriptionID");
            prescriptionItems = params.getObject("prescription", new TypeReference<HashSet<PrescriptionItem>>() {
            });
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Map<Integer, Medicine> medicines = Database.INSTANCE.select("medicines", Integer.class, Medicine.class).getRaw();

        double fee = prescriptionItems.stream().map(i -> i.getAmount() * medicines.get(i.getMedicineID()).getPrice()).mapToDouble(i -> i).sum();

        Prescription prescription;
        if (prescriptionID == -1)
            prescription = Prescription.insert(medicalRecord, id, 1, prescriptionItems, fee, Status.UNPAID);
        else
            prescription = Database.INSTANCE.select("prescriptions", Long.class, Prescription.class).getRaw().get(prescriptionID);

        if (prescription != null) {
            Database.INSTANCE.insert("prescriptions", prescription.getId(), prescription);
        }

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "开立成功"));
    }

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

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "作废成功"));
    }
}
