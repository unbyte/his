package model;

import lib.Security;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 员工(医生、药师、技师、前台)
 */
@NoArgsConstructor
@Getter
@Setter
public class Staff {

    private int id;

    private String name;
    private String password;

    private int department;

    private int title;

    public Staff(int id, String name, String password, int department, int title) {
        this.id = id;
        this.name = name;
        this.password = Security.encrypt(password);
        this.department = department;
        this.title = title;
    }

    /**
     * 比较输入的密码是否正确
     *
     * @param password 输入的明文密码
     * @return 密码是否正确
     */
    public boolean comparePassword(String password) {
        return this.password.equals(Security.encrypt(password));
    }

    /**
     * 修改密码
     *
     * @param password 明文新密码
     */
    public void shangePassword(String password) {
        this.password = Security.encrypt(password);
    }

    @Override
    public String toString() {
        return id + ":{\"department\":" + department + ",\"id\":" + id + ",\"name\":\"" + name + "\",\"title\":" + title + "}";
    }
}
