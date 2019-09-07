package database;


import com.alibaba.fastjson.JSON;
import lib.FileUtils;
import lib.LogUtils;
import model.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * 数据库
 */
public enum Database {
    INSTANCE;

    // 病历
    private HashMap<Long, MedicalRecords> medicalRecords = null;
    // 已经退费/完成的挂号记录，供查询
    private HashMap<Long, Registration> registrations = null;
    // 尚未完成/退费的挂号记录，供操作
    private HashMap<Long, Registration> newRegistrations = null;
    // 诊断
    private HashMap<Long, Diagnosis> diagnoses = null;
    // 检查记录
    private HashMap<Long, InspectionRecord> inspectionRecords = null;
    // 药方
    private HashMap<Long, Prescription> prescriptions = null;
    // 员工
    private HashMap<Integer, Staff> staffs = null;
    // 检查项目
    private HashMap<Integer, InspectionItem> inspectionItems = null;
    // 药物
    private HashMap<Integer, Medicine> medicines = null;
    // 疾病
    private HashMap<Integer, Disease> diseases = null;
    // 挂号等级
    private HashMap<Integer, RegistrationLevel> registrationLevels = null;
    // 科室
    private HashMap<Integer, Department> departments = null;
    // 职称
    private HashMap<Integer, Title> titles = null;


    // 因为极少更新，所以缓存成json字符串
    private String inspectionItemsCache = null;
    private String medicinesCache = null;
    private String diseasesCache = null;
    private String registrationLevelsCache = null;
    private String departmentsCache = null;
    private String titlesCache = null;


    // 存储数据与文件的映射关系
    private HashMap<String, Path> paths = new HashMap<>() {
        {
            put("medicalRecords", Paths.get("medicalRecords.dat"));
            put("registrations", Paths.get("registrations.dat"));
            put("newRegistrations", Paths.get("newRegistrations.dat"));
            put("diagnoses", Paths.get("diagnoses.dat"));
            put("inspectionRecords", Paths.get("inspectionRecords.dat"));
            put("prescriptions", Paths.get("prescriptions.dat"));
            put("staffs", Paths.get("staffs.dat"));
            put("inspectionItems", Paths.get("inspectionItems.dat"));
            put("medicines", Paths.get("medicines.dat"));
            put("diseases", Paths.get("disease.dat"));
            put("registrationLevels", Paths.get("registrationLevels.dat"));
            put("departments", Paths.get("departments.dat"));
            put("titles", Paths.get("titles.dat"));
        }
    };


    // 存储名称与数据的映射关系
    private HashMap<String, HashMap> fileds = new HashMap<>() {
        {
            put("medicalRecords", medicalRecords);
            put("registrations", registrations);
            put("newRegistrations", newRegistrations);
            put("diagnoses", diagnoses);
            put("inspectionRecords", inspectionRecords);
            put("prescriptions", prescriptions);
            put("staffs", staffs);
            put("inspectionItems", inspectionItems);
            put("medicines", medicines);
            put("diseases", diseases);
            put("registrationLevels", registrationLevels);
            put("departments", departments);
            put("titles", titles);
        }
    };

    private HashMap<String, String> caches = new HashMap<>() {
        {
            put("inspectionItems", inspectionItemsCache);
            put("medicines", medicinesCache);
            put("diseases", diseasesCache);
            put("registrationLevels", registrationLevelsCache);
            put("departments", departmentsCache);
            put("titles", titlesCache);
        }
    };

    /**
     * 创建数据对象
     */
    @SuppressWarnings("unchecked")
    public <K extends Number, V> boolean insert(String fieldName, K key, V object) {
        // 如果键值已存在，则不存入
        if (fileds.get(fieldName).containsKey(key))
            return false;
        fileds.get(fieldName).put(key, object);
        return true;
    }


    /**
     * 在数据库中查找实体
     *
     * @param fieldName 待查找的实体所属的field名称
     * @return 查找结果列表
     */
    public SelectSession select(String fieldName) {
        return new SelectSession(fileds.get(fieldName));
    }

    /**
     * 获取某一类数据的所有实体的json字符串
     *
     * @param fieldName 待查找的实体所属的field名称
     * @return 查找结果的json字符串
     */
    public String selectAll(String fieldName) {
        if (caches.containsKey(fieldName))
            return caches.get(fieldName);
        return JSON.toJSONString(fileds.get(fieldName));
    }

    /**
     * 启动时从文件中初始化数据
     */
    public void boot() {
        LogUtils.info("Database is booting");

        medicalRecords = FileUtils.loadHashMapFromFile(paths.get("medicalRecords"), Long.class, MedicalRecords.class);

        registrations = FileUtils.loadHashMapFromFile(paths.get("registrations"), Long.class, Registration.class);

        newRegistrations = FileUtils.loadHashMapFromFile(paths.get("newRegistrations"), Long.class, Registration.class);

        diagnoses = FileUtils.loadHashMapFromFile(paths.get("diagnoses"), Long.class, Diagnosis.class);

        inspectionRecords = FileUtils.loadHashMapFromFile(paths.get("inspectionRecords"), Long.class, InspectionRecord.class);

        prescriptions = FileUtils.loadHashMapFromFile(paths.get("prescriptions"), Long.class, Prescription.class);

        staffs = FileUtils.loadHashMapFromFile(paths.get("staffs"), Integer.class, Staff.class);

        inspectionItems = FileUtils.loadHashMapFromFile(paths.get("inspectionItems"), Integer.class, InspectionItem.class);
        inspectionItemsCache = FileUtils.readStringFromFile(paths.get("inspectionItems"));

        departments = FileUtils.loadHashMapFromFile(paths.get("departments"), Integer.class, Department.class);
        departmentsCache = FileUtils.readStringFromFile(paths.get("departments"));

        medicines = FileUtils.loadHashMapFromFile(paths.get("medicines"), Integer.class, Medicine.class);
        medicinesCache = FileUtils.readStringFromFile(paths.get("medicines"));

        diseases = FileUtils.loadHashMapFromFile(paths.get("diseases"), Integer.class, Disease.class);
        diseasesCache = FileUtils.readStringFromFile(paths.get("diseases"));

        registrationLevels = FileUtils.loadHashMapFromFile(paths.get("registrationLevels"), Integer.class, RegistrationLevel.class);
        registrationLevelsCache = FileUtils.readStringFromFile(paths.get("registrationLevels"));

        titles = FileUtils.loadHashMapFromFile(paths.get("titles"), Integer.class, Title.class);
        titlesCache = FileUtils.readStringFromFile(paths.get("titles"));

        LogUtils.info("Database booted successfully");
    }


    public void shut() {
        LogUtils.info("Database is shutting down");
        if (persist())
            shutImmediately();
        else
            LogUtils.warn("To ensure that data is properly processed, suspend closure");
    }

    public void shutImmediately() {
        // todo 关闭
    }

    /**
     * 持久化，写入数据
     */
    private boolean persist() {
        // todo 以后改成增量写入

        // 是否有写入失败的情况
        boolean flag;

        LogUtils.info("Saving data...");

        // 由HashMap写入到文件
        flag = FileUtils.saveHashMapToFile(paths.get("medicalRecords"), medicalRecords, false);
        flag &= FileUtils.saveHashMapToFile(paths.get("registrations"), registrations, false);
        flag &= FileUtils.saveHashMapToFile(paths.get("newRegistrations"), newRegistrations, false);
        flag &= FileUtils.saveHashMapToFile(paths.get("diagnoses"), diagnoses, false);
        flag &= FileUtils.saveHashMapToFile(paths.get("inspectionRecords"), inspectionRecords, false);
        flag &= FileUtils.saveHashMapToFile(paths.get("prescriptions"), prescriptions, false);
        flag &= FileUtils.saveHashMapToFile(paths.get("staffs"), staffs, false);


        // 由缓存成的字符串直接写成文件
        flag &= FileUtils.writeStringToFile(paths.get("inspectionItems"), inspectionItemsCache, false);
        flag &= FileUtils.writeStringToFile(paths.get("departments"), departmentsCache, false);
        flag &= FileUtils.writeStringToFile(paths.get("medicines"), medicinesCache, false);
        flag &= FileUtils.writeStringToFile(paths.get("diseases"), diseasesCache, false);
        flag &= FileUtils.writeStringToFile(paths.get("registrationLevels"), registrationLevelsCache, false);
        flag &= FileUtils.writeStringToFile(paths.get("titles"), titlesCache, false);
        if (!flag)
            LogUtils.warn("Some of data has been saved");
        else
            LogUtils.info("Data has been saved completely");
        return flag;
    }

}
