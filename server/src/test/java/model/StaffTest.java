package model;

import com.alibaba.fastjson.JSON;

public class StaffTest {
    public static void main(String[] args) {
        Department department = Department.insert("测试", 3);
        Title title = new Title(0, "测试头衔", 0);
        RegistrationLevel registrationLevel = RegistrationLevel.insert("测试等级", 999, 999);
        Staff staff = Staff.insert("白", "E=1/2mv^2", 0, 0);
        System.out.println(JSON.toJSONString(department));
        System.out.println(JSON.toJSONString(title));
        System.out.println(JSON.toJSONString(registrationLevel));
        System.out.println(JSON.toJSONString(staff));
    }
}
