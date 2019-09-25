package model;

import lib.IDGenerator;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;

/**
 * 处方
 */
@Accessors(chain = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Prescription {

    private long id;
    /*病历号*/
    private long medicalRecordID;
    /*挂号记录id*/
    private long registrationID;

    private String name;
    /*0-中 1-西*/
    private byte clazz;
    /*药物清单*/
    private HashSet<PrescriptionItem> medicineList;
    /*费用*/
    private double fee;

    private byte status;


    /**
     * 新建一个处方对象并获取
     *
     * @param medicalRecordID 病历号
     * @param registrationID  所属的挂号记录id
     * @param clazz           分类 0中 / 1西
     * @param medicineList    药物清单
     * @param fee             费用
     * @param status          状态id
     * @return id自动生成的处方对象，若药物超出5种则返回null
     */
    public static Prescription insert(long medicalRecordID, long registrationID, String name, byte clazz, HashSet<PrescriptionItem> medicineList, double fee, byte status) {
        return new Prescription(IDGenerator.generate(), medicalRecordID, registrationID, name, clazz, medicineList, fee, status);
    }

}
