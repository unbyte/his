package model;

import lib.IDGenerator;

import java.util.HashSet;

/**
 * 处方
 */
public class Prescription {
    private long id;
    private long registrationID;
    private int clazz;
    private HashSet<PrescriptionItem> medicineList;
    private double fee;
    private int status;

    private Prescription(long id, long registrationID, int clazz, HashSet<PrescriptionItem> medicineList, double fee, int status) {
        this.id = id;
        this.registrationID = registrationID;
        this.clazz = clazz;
        this.medicineList = medicineList;
        this.fee = fee;
        this.status = status;
    }

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

    /**
     * 获取id
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * 获取所属的挂号记录id
     *
     * @return 挂号记录id
     */
    public long getRegistrationID() {
        return registrationID;
    }

    /**
     * 获取分类
     *
     * @return 分类 0中 / 1西
     */
    public int getClazz() {
        return clazz;
    }

    /**
     * 获取药物清单
     *
     * @return 药物清单
     */
    public HashSet<PrescriptionItem> getMedicineList() {
        return medicineList;
    }

    /**
     * 获取费用
     *
     * @return 费用
     */
    public double getFee() {
        return fee;
    }

    /**
     * 获取状态id
     *
     * @return 状态id
     */
    public int getStatus() {
        return status;
    }

    /**
     * 更新状态id
     *
     * @param status 新的状态id
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
