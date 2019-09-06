package model;

/**
 * 药物
 */
public class Medicine {
    private int id;
    private String code;
    private String name;
    private String format;
    private double price;

    private Medicine(int id, String code, String name, String format, double price) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.format = format;
        this.price = price;
    }

    /**
     * 新建一个药物对象并获取
     *
     * @param code   助记码
     * @param name   名称
     * @param format 规格
     * @param price  价格
     * @return id自动生成的药物对象
     */
    public static Medicine insert(String code, String name, String format, double price) {
        // todo id生成
        int id = 0;
        return new Medicine(id, code, name, format, price);
    }

    /**
     * 修改价格
     *
     * @param price 新价格
     */
    public void setPrice(double price) {
        this.price = price;
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
     * 获取姓名
     *
     * @return 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 获取药物规格
     *
     * @return 药物规格
     */
    public String getFormat() {
        return format;
    }

    /**
     * 获取价格
     *
     * @return 价格
     */
    public double getPrice() {
        return price;
    }
}
