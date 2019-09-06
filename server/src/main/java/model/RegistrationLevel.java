package model;

/**
 * 挂号等级
 */
public class RegistrationLevel {
    private int id;
    private String name;
    private int limit;
    private double fee;

    private RegistrationLevel(int id, String name, int limit, double fee) {
        this.id = id;
        this.name = name;
        this.limit = limit;
        this.fee = fee;
    }

    /**
     * 新建一个挂号等级对象并获取
     *
     * @param name  等级名称
     * @param limit 每日挂号数限制
     * @param fee   费用
     * @return id自动生成的挂号等级对象
     */
    public static RegistrationLevel insert(String name, int limit, double fee) {
        // todo id生成
        int id = 0;
        return new RegistrationLevel(id, name, limit, fee);
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
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取每日限制数
     *
     * @return 每日限制数
     */
    public int getLimit() {
        return limit;
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
