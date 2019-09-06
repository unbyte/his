package model;

/**
 * 疾病
 */
public class Disease {
    private int id;
    private int clazz;
    private String code;
    private String name;

    private Disease(int id, int clazz, String code, String name) {
        this.id = id;
        this.clazz = clazz;
        this.code = code;
        this.name = name;
    }

    /**
     * 新建一个疾病对象并获取
     *
     * @param clazz 分类 0 中/ 1 西
     * @param code  助记码
     * @param name  名称
     * @return id自动生成的疾病对象
     */
    public static Disease insert(int clazz, String code, String name) {
        // todo id
        int id = 0;
        return new Disease(id, clazz, code, name);
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
     * 获取分类
     *
     * @return 分类 0 中/ 1 西
     */
    public int getClazz() {
        return clazz;
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
}
