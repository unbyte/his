package model;

import lib.IDGenerator;

/**
 * 挂号记录
 */
public class Registration {
    private long id;
    private long medicalRecordsID;
    private long time;
    private int departmentID;
    private int doctorID;
    private int level;
    private double fee;
    private int status;

    /**
     * 新建一个挂号记录对象并获取
     *
     * @param medicalRecordsID 病历号
     * @param time             挂号时间
     * @param departmentID     科室id
     * @param doctorID         医生id
     * @param level            挂号等级
     * @param fee              挂号费用
     * @param status           挂号状态
     * @return 一个id自动生成的挂号记录对象
     */
    public static Registration insert(long medicalRecordsID, long time, int departmentID, int doctorID, int level, double fee, int status) {
        return new Registration(IDGenerator.generate(), medicalRecordsID, time, departmentID, doctorID, level, fee, status);
    }

    private Registration(long id, long medicalRecordsID, long time, int departmentID, int doctorID, int level, double fee, int status) {
        this.id = id;
        this.medicalRecordsID = medicalRecordsID;
        this.time = time;
        this.departmentID = departmentID;
        this.doctorID = doctorID;
        this.level = level;
        this.fee = fee;
        this.status = status;
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
     * 获取挂号记录所属的病历号
     *
     * @return 病历号
     */
    public long getMedicalRecordsID() {
        return medicalRecordsID;
    }

    /**
     * 获取挂号时间
     *
     * @return 挂号时间
     */
    public long getTime() {
        return time;
    }

    /**
     * 获取挂号科室的id
     *
     * @return 科室id
     */
    public int getDepartmentID() {
        return departmentID;
    }

    /**
     * 获取挂号医生的id
     *
     * @return 医生id
     */
    public int getDoctorID() {
        return doctorID;
    }

    /**
     * 获取挂号等级id
     *
     * @return 挂号等级
     */
    public int getLevel() {
        return level;
    }

    /**
     * 获取挂号费用
     *
     * @return 挂号费用
     */
    public double getFee() {
        return fee;
    }

    /**
     * 获取挂号状态id
     *
     * @return 挂号状态id
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
