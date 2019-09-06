package model;

/**
 * 科室
 */
public class Department {
    private int id;
    private String name;
    private int clazz;

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
     * 获取分类
     *
     * @return 0 门诊部门 1 药师部门 2 技师部门 3 前台
     */
    public int getClazz() {
        return clazz;
    }

    private Department(int id, String name, int clazz) {
        this.id = id;
        this.name = name;
        this.clazz = clazz;
    }

    /**
     * 新建一个科室对象并获取
     *
     * @param name  名称
     * @param clazz 分类
     * @return id自动生成的科室对象
     */
    public static Department insert(String name, int clazz) {
        // todo Id

        int id = 0;
        return new Department(id, name, clazz);
    }
}
