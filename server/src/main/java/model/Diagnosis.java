package model;

import lib.IDGenerator;

/**
 * 诊断信息
 */
public class Diagnosis {
    private long id;
    private long registrationID;
    private String complaint;
    private int clazz;
    private String disease;
    private String suggestion;
    private String note;
    private String judgement;
    private int status;

    private Diagnosis(long id, long registrationID, String complaint, int clazz, String disease, String suggestion, String note, String judgement, int status) {
        this.id = id;
        this.registrationID = registrationID;
        this.complaint = complaint;
        this.clazz = clazz;
        this.disease = disease;
        this.suggestion = suggestion;
        this.note = note;
        this.judgement = judgement;
        this.status = status;
    }

    /**
     * 新建一个诊断信息对象并获取
     *
     * @param registrationID 挂号记录id
     * @param complaint      主诉
     * @param clazz          分类 0中医 1西医
     * @param disease        疾病诊断
     * @param suggestion     检查建议
     * @param note           注意事项
     * @param judgement      最终诊断结果
     * @param status         诊断状态
     * @return 一个id自动生成的诊断信息对象
     */
    public static Diagnosis insert(long registrationID, String complaint, int clazz, String disease, String suggestion, String note, String judgement, int status) {
        return new Diagnosis(IDGenerator.generate(), registrationID, complaint, clazz, disease, suggestion, note, judgement, status);
    }

    /**
     * 获取id
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * 获取诊断信息所属的挂号记录id
     *
     * @return 挂号记录id
     */
    public long getRegistrationID() {
        return registrationID;
    }

    /**
     * 获取主诉情况
     *
     * @return 主诉
     */
    public String getComplaint() {
        return complaint;
    }

    /**
     * 获取分类
     *
     * @return 0 中医 / 1 西医
     */
    public int getClazz() {
        return clazz;
    }

    /**
     * 获取疾病诊断信息
     *
     * @return 疾病诊断信息
     */
    public String getDisease() {
        return disease;
    }

    /**
     * 获取检查建议
     *
     * @return 检查建议
     */
    public String getSuggestion() {
        return suggestion;
    }

    /**
     * 获取注意事项
     *
     * @return 注意事项
     */
    public String getNote() {
        return note;
    }

    /**
     * 获取最终诊断信息
     *
     * @return 最终诊断信息
     */
    public String getJudgement() {
        return judgement;
    }

    /**
     * 获取诊断状态id
     *
     * @return 诊断状态id
     */
    public int getStatus() {
        return status;
    }

    /**
     * 更新状态id
     *
     * @param status 新状态id
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
