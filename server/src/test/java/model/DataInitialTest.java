package model;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

public class DataInitialTest {
    public static void main(String[] args) {
        genDepartments();
        genStaff();
    }

    static void genDepartments() {
        HashMap<Integer, Department> departments = new HashMap<>();
        departments.put(0, new Department(0, "前台", Department.FRONT_DESK));
        departments.put(1, new Department(1, "中药药房", Department.PHARMACY));
        departments.put(2, new Department(2, "西药药房", Department.PHARMACY));
        departments.put(3, new Department(3, "放射科", Department.MEDICAL_TECHNIQUE));
        departments.put(4, new Department(4, "外科", Department.OUTPATIENT));
        departments.put(5, new Department(5, "消化科", Department.OUTPATIENT));
        System.out.println(JSON.toJSONString(departments));
    }

    static void genTitles() {
        HashMap<Integer, Title> titles = new HashMap<>();
        titles.put(0, new Title(0, "住院医师", 0));
        titles.put(1, new Title(1, "主治医师", 1));
        titles.put(2, new Title(2, "副主任医师", 2));
        titles.put(3, new Title(3, "主任医师", 3));
        titles.put(4, new Title(4, "初级技师", 0));
        titles.put(5, new Title(5, "主管技师", 0));
        titles.put(6, new Title(6, "副主任技师", 0));
        titles.put(7, new Title(7, "主任技师", 0));
        titles.put(8, new Title(8, "初级药师", 0));
        titles.put(9, new Title(9, "主管药师", 0));
        titles.put(10, new Title(10, "副主任药师", 0));
        titles.put(11, new Title(11, "主任药师", 0));
        titles.put(12, new Title(12, "前台接诊", 0));
        System.out.println(JSON.toJSONString(titles));
    }

    static void genRegistrationLevels() {
        HashMap<Integer, RegistrationLevel> registrationLevels = new HashMap<>();
        registrationLevels.put(0, new RegistrationLevel(0, 0, 0));
        registrationLevels.put(1, new RegistrationLevel(1, 120, 20));
        registrationLevels.put(2, new RegistrationLevel(2, 80, 60));
        registrationLevels.put(3, new RegistrationLevel(3, 40, 100));
        System.out.println(JSON.toJSONString(registrationLevels));
    }

    static void genStaff(){
        HashMap<Integer,Staff> staffs = new HashMap<>();
        staffs.put(0,new Staff(0,"白白白","E=pc",0,12)); // 前台
        staffs.put(1,new Staff(1,"黑黑黑","E=mc^2",1,8)); // 中药房
        staffs.put(2,new Staff(2,"灰灰灰","E=1/2mv^2",2,8)); // 西药房
        staffs.put(3,new Staff(3,"蓝蓝蓝","E=U/d",3,4)); // 放射科
        staffs.put(4,new Staff(4,"红红红","E=BLv",4,1)); // 主治医师
        staffs.put(5,new Staff(5,"绿绿绿","E=BLv",4,2)); // 副主任医师
        staffs.put(6,new Staff(6,"黄黄黄","E=BLv",4,3)); // 主任医师
        staffs.put(7,new Staff(7,"青青青","E=BLv",5,1)); // 主治医师
        staffs.put(8,new Staff(8,"紫紫紫","E=BLv",5,1)); // 主治医师
        System.out.println(JSON.toJSONString(staffs));
    }

}
