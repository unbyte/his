package model;

/**
 * 处方中的药物清单组成
 */
public class PrescriptionItem {
    private int medicineID;
    private String dosage;
    private String usage;
    private String frequency;
    private int amount;

    /**
     * 新建一个清单组成对象并获取
     *
     * @param medicineID 药物id
     * @param dosage     用量
     * @param usage      用法
     * @param frequency  频次
     * @param amount     数量
     */
    public PrescriptionItem(int medicineID, String dosage, String usage, String frequency, int amount) {
        this.medicineID = medicineID;
        this.dosage = dosage;
        this.usage = usage;
        this.frequency = frequency;
        this.amount = amount;
    }

    /**
     * 获取药物id
     *
     * @return 药物id
     */
    public int getMedicineID() {
        return medicineID;
    }

    /**
     * 获取用量
     *
     * @return 用量
     */
    public String getDosage() {
        return dosage;
    }

    /**
     * 获取用法
     *
     * @return 用法
     */
    public String getUsage() {
        return usage;
    }

    /**
     * 获取频次
     *
     * @return 频次
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * 获取数量
     *
     * @return 数量
     */
    public int getAmount() {
        return amount;
    }
}
