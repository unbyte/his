package model;

import lib.Security;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 员工(医生、药师、技师、前台)
 */
@ToString(exclude = "password")
public class Staff {
    @Getter
    private int id;
    @Getter
    private String name;
    private String password;
    @Getter
    @Setter
    private int department;
    @Getter
    @Setter
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
        int id = 0;//todo 唯一id生成规则
        return new Staff(id, name, Security.encrypt(password), department, title);
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
     * 修改密码
     *
     * @param password 明文新密码
     */
    public void setPassword(String password) {
        this.password = Security.encrypt(password);
    }


}
