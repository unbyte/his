package model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lib.JSONUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class DataInitialTest {
    public static void main(String[] args) {
        genDisease();
    }

    static void genDepartments() {
        HashMap<Integer, Department> departments = new HashMap<>();
        Department department0 = new Department(0, "管理员", Department.ADMIN, null,new ArrayList<>());
        Department department10 = new Department(10, "前台", Department.FRONT_DESK, null, new ArrayList<>());
        Department department20 = new Department(20, "中药药房", Department.PHARMACY, null, new ArrayList<>());
        Department department21 = new Department(21, "西药药房", Department.PHARMACY, null, new ArrayList<>());
        Department department30 = new Department(30, "放射科", Department.MEDICAL_TECHNIQUE, null, new ArrayList<>());

        Department department40 = new Department(40, "内科", Department.OUTPATIENT, null, new ArrayList<>());
        Department department41 = new Department(41, "呼吸内科", Department.OUTPATIENT, null, new ArrayList<>());
        Department department42 = new Department(42, "消化内科", Department.OUTPATIENT, null, new ArrayList<>());
        Department department43 = new Department(43, "心内科", Department.OUTPATIENT, null, new ArrayList<>());

        department40.getChildren().add(department41);
        department40.getChildren().add(department42);
        department40.getChildren().add(department43);
        department41.setParent(department40);
        department42.setParent(department40);
        department43.setParent(department40);

        departments.put(0, department0);
        departments.put(10, department10);
        departments.put(20, department20);
        departments.put(21, department21);
        departments.put(30, department30);
        departments.put(40, department40);
        departments.put(41, department41);
        departments.put(42, department42);
        departments.put(43, department43);

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
        titles.put(13, new Title(13, "管理员", 0));
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

    static void genStaff() {
        HashMap<Integer, Staff> staffs = new HashMap<>();
        staffs.put(0, new Staff(0, "白白白", "E=pc", 10, 12)); // 前台
        staffs.put(1, new Staff(1, "黑黑黑", "E=mc^2", 20, 8)); // 中药房
        staffs.put(2, new Staff(2, "灰灰灰", "E=1/2mv^2", 21, 8)); // 西药房
        staffs.put(3, new Staff(3, "蓝蓝蓝", "E=U/d", 30, 4)); // 放射科
        staffs.put(4, new Staff(4, "红红红", "E=BLv", 43, 1)); // 主治医师
        staffs.put(5, new Staff(5, "绿绿绿", "E=BLv", 41, 2)); // 副主任医师
        staffs.put(6, new Staff(6, "黄黄黄", "E=BLv", 42, 3)); // 主任医师
        staffs.put(7, new Staff(7, "紫紫紫", "E=BLv", 0, 1)); // 主治医师
        System.out.println(JSON.toJSONString(staffs));
    }

    static void genInspectionItem() {
        HashMap<Integer, InspectionItem> inspectionItems = new HashMap<>();
        inspectionItems.put(0, new InspectionItem(0, "PTTS", "普通透视", 3, 5));
        inspectionItems.put(1, new InspectionItem(1, "SGBCTS", "食管钡餐透视", 3, 15));
        System.out.println(JSON.toJSONString(inspectionItems));
    }

    static void genDisease() {
        HashMap<Integer, Disease> diseases = new HashMap<>();
        Disease disease0 = new Disease(0, "内科疾病", "nkjb", 1, null, new ArrayList<>());
        Disease disease1 = new Disease(1, "呼吸内科", "hxnk", 1, disease0, new ArrayList<>());
        Disease disease2 = new Disease(2, "消化内科", "xhnk", 1, disease0, new ArrayList<>());
        Disease disease3 = new Disease(3, "心血管内科", "xxgnk", 1, disease0, new ArrayList<>());

        disease0.getChildren().addAll(Arrays.asList(disease1, disease2, disease3));

        Disease disease4 = new Disease(104, "肺病", "xc", 1, disease1, new ArrayList<>());

        disease1.getChildren().add(disease4);

        Disease disease11 = new Disease(108, "咽喉病", "yh", 1, disease1, new ArrayList<>());

        disease1.getChildren().add(disease11);


        Disease disease14 = new Disease(109, "肺炎", "xc", 1, disease4, new ArrayList<>());

        disease4.getChildren().add(disease14);

        Disease disease21 = new Disease(110, "咽炎", "yy", 1, disease11, new ArrayList<>());

        disease11.getChildren().add(disease21);


        Disease disease5 = new Disease(105, "肠炎", "cy", 1, disease2, new ArrayList<>());
        disease2.getChildren().add(disease5);

        Disease disease6 = new Disease(106, "出血性结肠炎", "cxxjcy", 1, disease5, new ArrayList<>());

        disease5.getChildren().add(disease6);

        Disease disease7 = new Disease(107, "心肌炎", "xjy", 1, disease3, new ArrayList<>());

        disease3.getChildren().add(disease7);

        diseases.put(0, disease0);
        diseases.put(1, disease1);
        diseases.put(2, disease2);
        diseases.put(3, disease3);
        diseases.put(104, disease4);
        diseases.put(105, disease5);
        diseases.put(106, disease6);
        diseases.put(107, disease7);
        diseases.put(108, disease11);
        diseases.put(109, disease14);
        diseases.put(110, disease21);

        System.out.println(JSON.toJSONString(diseases));
        System.out.println(JSONUtils.toHashMap(JSON.toJSONString(diseases), Integer.class, Disease.class).get(0).getChildren().get(0).getName());
    }
}
