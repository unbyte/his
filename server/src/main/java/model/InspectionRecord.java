package model;

import lib.IDGenerator;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 检查/检验记录
 */
@Accessors(chain = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
@Getter
@Setter
public class InspectionRecord {

    private long id;
    /*病历号*/
    private long medicalRecordID;

    /*挂号记录id*/
    private long registrationID;

    private long time;
    /*检查项目*/
    private int item;
    /*检查部位*/
    private String part;
    /*检查要求*/
    private String request;

    /*检查结果*/
    private String result;

    private double fee;

    private byte status;

    /**
     * 新建一个检查记录对象并获取
     *
     * @param medicalRecordID 病历号
     * @param registrationID  检查记录所属的挂号记录id
     * @param time            检查时间
     * @param item            检查项目
     * @param part            检查部位
     * @param request         检查要求
     * @param result          检查结果
     * @param fee             费用
     * @param status          状态id
     * @return 一个id自动生成的检查记录对象
     */
    public static InspectionRecord insert(long medicalRecordID, long registrationID, long time, int item, String part, String request, String result, double fee, byte status) {
        return new InspectionRecord(medicalRecordID, IDGenerator.generate(), registrationID, time, item, part, request, result, fee, status);
    }
}
