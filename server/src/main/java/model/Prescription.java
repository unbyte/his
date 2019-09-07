package model;

import lib.IDGenerator;
import lombok.*;

import java.util.HashSet;

/**
 * 处方
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Prescription {
    @Getter
    private long id;
    @Getter /*挂号记录id*/
    private long registrationID;
    @Getter /*0-中 1-西*/
    private int clazz;
    @Getter /*药物清单*/
    private HashSet<PrescriptionItem> medicineList;
    @Getter /*费用*/
    private double fee;
    @Getter
    @Setter
    private int status;


    /**
     * 新建一个处方对象并获取
     *
     * @param registrationID 所属的挂号记录id
     * @param clazz          分类 0中 / 1西
     * @param medicineList   药物清单
     * @param fee            费用
     * @param status         状态id
     * @return id自动生成的处方对象，若药物超出5种则返回null
     */
    public static Prescription insert(long registrationID, int clazz, HashSet<PrescriptionItem> medicineList, double fee, int status) {
        // 根据国家规定，每个处方中最多只能包含5种药品，如果超过需要新增处方
        // todo 更详细的检验
        if (medicineList.size() > 5)
            return null;
        return new Prescription(IDGenerator.generate(), registrationID, clazz, medicineList, fee, status);
    }

}
