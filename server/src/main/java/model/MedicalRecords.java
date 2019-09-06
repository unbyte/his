package model;

import lib.IDGenerator;

/**
 * 病历
 */
public class MedicalRecords {
    private long id;
    private String name;
    private long birthDate;
    private int gender;
    private String idCard;
    private String address;
    private String pastHistory;
    private String presentIllnessHistory;
    private String allergyHistory;
    private String currentIllnessTreatment;

    /**
     * 获取病历号
     *
     * @return 病历号
     */
    public long getId() {
        return id;
    }

    /**
     * 获取患者姓名
     *
     * @return 患者姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 获取出生日期
     *
     * @return 出生日期
     */
    public long getBirthDate() {
        return birthDate;
    }

    /**
     * 获取性别
     *
     * @return 性别
     */
    public int getGender() {
        return gender;
    }

    /**
     * 获取身份证号码
     *
     * @return 身份证号码
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 获取家庭住址
     *
     * @return 家庭住址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 获取既往史
     *
     * @return 既往史
     */
    public String getPastHistory() {
        return pastHistory;
    }

    /**
     * 获取现病史
     *
     * @return 现病史
     */
    public String getPresentIllnessHistory() {
        return presentIllnessHistory;
    }

    /**
     * 获取过敏史
     *
     * @return 过敏史
     */
    public String getAllergyHistory() {
        return allergyHistory;
    }

    /**
     * 获取现病治疗情况
     *
     * @return 现病治疗情况
     */
    public String getCurrentIllnessTreatment() {
        return currentIllnessTreatment;
    }

    private MedicalRecords(long id, String name, long birthDate, int gender, String idCard, String address, String pastHistory, String presentIllnessHistory, String allergyHistory, String currentIllnessTreatment) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.idCard = idCard;
        this.address = address;
        this.pastHistory = pastHistory;
        this.presentIllnessHistory = presentIllnessHistory;
        this.allergyHistory = allergyHistory;
        this.currentIllnessTreatment = currentIllnessTreatment;
    }

    /**
     * 新建一个病历对象并获取
     *
     * @param name                    患者姓名
     * @param birthDate               出生日期
     * @param gender                  性别 0为女，1为男
     * @param idCard                  身份证号码
     * @param address                 家庭住址
     * @param pastHistory             既往史
     * @param presentIllnessHistory   现病史
     * @param allergyHistory          过敏史
     * @param currentIllnessTreatment 现病治疗情况
     * @return 一个病历号自动生成的病历对象
     */
    public static MedicalRecords insert(String name, long birthDate, int gender, String idCard, String address, String pastHistory, String presentIllnessHistory, String allergyHistory, String currentIllnessTreatment) {
        return new MedicalRecords(IDGenerator.generate(), name, birthDate, gender, idCard, address, pastHistory, presentIllnessHistory, allergyHistory, currentIllnessTreatment);
    }
}
