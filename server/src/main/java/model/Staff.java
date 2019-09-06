package model;

import lib.Security;

/**
 * 员工(医生、药师、技师、前台)
 */
public class Staff {
    private int id;
    private String name;
    private String password;
    private int department;
    private int title;

    private Staff(int id, String name, String password, int department, int title) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.department = department;
        this.title = title;
    }

    /**
     * 新建一个员工对象并获取
     *
     * @param name       姓名
     * @param password   密码，会自动进行hash处理
     * @param department 科室id
     * @param title      职称id
     * @return id自动生成的员工对象
     */
    public static Staff insert(String name, String password, int department, int title) {
        // todo 生成id
        int id = 0;
        return new Staff(id, name, Security.encrypt(password), department, title);
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
     * 获取姓名
     *
     * @return 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 比较输入的密码是否正确
     *
     * @param password 输入的明文密码
     * @return 密码是否正确
     */
    public boolean comparePassword(String password) {
        return password.equals(Security.encrypt(password));
    }

    /**
     * 获取科室id
     *
     * @return 科室的id
     */
    public int getDepartment() {
        return department;
    }

    /**
     * 获取职称id
     *
     * @return 职称id
     */
    public int getTitle() {
        return title;
    }

    /**
     * 修改密码
     *
     * @param password 明文新密码
     */
    public void setPassword(String password) {
        this.password = Security.encrypt(password);
    }

    /**
     * 修改职称id
     *
     * @param title 新的职称id
     */
    public void setTitle(int title) {
        this.title = title;
    }
}
