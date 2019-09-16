package model;

import com.alibaba.fastjson.JSON;
import lib.Security;


public class ReadFromFileTest {
    public static void main(String[] args) {
//        HashMap<Integer,Staff> staffs = FileUtils.loadHashMapFromFile(Paths.get("./server/data","staffs"),Integer.class,Staff.class);
        Staff staff = JSON.parseObject("{\"department\":0,\"id\":0,\"name\":\"白\",\"password\":\"1ebed8a0de33f0fbfc54bf9c4d698703\",\"title\":0}",Staff.class);
        System.out.println(staff);
        System.out.println(staff.comparePassword("E=1/2mv^2"));
        System.out.println(Security.encrypt("E=1/2mv^2"));
        System.out.println(staff.getPassword());

    }
}
