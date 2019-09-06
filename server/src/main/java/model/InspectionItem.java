package model;

/**
 * 检查/检验项目
 */
public class InspectionItem {
    private int id;
    private String code;
    private String name;


    private double fee;

    private InspectionItem(int id, String code, String name, double fee) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.fee = fee;
    }

    /**
     * 新建一个检查检验项目并获取
     *
     * @param code 助记码
     * @param name 名称
     * @param fee  费用
     * @return id自动生成的检查检验项目
     */
    public static InspectionItem insert(String code, String name, double fee) {
        // todo 生成Id
        int id = 0;
        return new InspectionItem(id, code, name, fee);
    }

    /**
     * 获取id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * 获取助记码
     *
     * @return 助记码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
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
     * 修改费用
     *
     * @param fee 新费用
     */
    public void setFee(double fee) {
        this.fee = fee;
    }


}
