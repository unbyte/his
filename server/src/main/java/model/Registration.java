package model;

import lib.IDGenerator;
import lombok.*;

/**
 * 挂号记录
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Registration {

    private long id;

    private long medicalRecordsID;

    private long time;

    private int departmentID;

    private int doctorID;

    private int level;

    private double fee;

    @Setter
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

}
