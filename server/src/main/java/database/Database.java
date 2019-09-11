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
    private HashMap<Long, MedicalRecords> medicalRecords;
    // 已经退费/完成的挂号记录，供查询
    private HashMap<Long, Registration> registrations;
    // 尚未完成/退费的挂号记录，供操作
    private HashMap<Long, Registration> newRegistrations;
    // 诊断
    private HashMap<Long, Diagnosis> diagnoses;
    // 检查记录
    private HashMap<Long, InspectionRecord> inspectionRecords;
    // 药方
    private HashMap<Long, Prescription> prescriptions;
    // 员工
    private HashMap<Integer, Staff> staffs;
    // 检查项目
    private HashMap<Integer, InspectionItem> inspectionItems;
    // 药物
    private HashMap<Integer, Medicine> medicines;
    // 疾病
    private HashMap<Integer, Disease> diseases;
    // 挂号等级
    private HashMap<Integer, RegistrationLevel> registrationLevels;
    // 科室
    private HashMap<Integer, Department> departments;
    // 职称
    private HashMap<Integer, Title> titles;


    // 因为极少更新，所以缓存成json字符串
    private String inspectionItemsCache;
    private String medicinesCache;
    private String diseasesCache;
    private String registrationLevelsCache;
    private String departmentsCache;
    private String titlesCache;


    // 存储数据与文件的映射关系
    private HashMap<String, Path> paths = new HashMap<>() {
        {
            String pathPrefix = "./server/data";
            put("medicalRecords", Paths.get(pathPrefix, "medicalRecords.dat"));
            put("registrations", Paths.get(pathPrefix, "registrations.dat"));
            put("newRegistrations", Paths.get(pathPrefix, "newRegistrations.dat"));
            put("diagnoses", Paths.get(pathPrefix, "diagnoses.dat"));
            put("inspectionRecords", Paths.get(pathPrefix, "inspectionRecords.dat"));
            put("prescriptions", Paths.get(pathPrefix, "prescriptions.dat"));
            put("staffs", Paths.get(pathPrefix, "staffs.dat"));
            put("inspectionItems", Paths.get(pathPrefix, "inspectionItems.dat"));
            put("medicines", Paths.get(pathPrefix, "medicines.dat"));
            put("diseases", Paths.get(pathPrefix, "disease.dat"));
            put("registrationLevels", Paths.get(pathPrefix, "registrationLevels.dat"));
            put("departments", Paths.get(pathPrefix, "departments.dat"));
            put("titles", Paths.get(pathPrefix, "titles.dat"));
        }
    };


    // 存储名称与数据的映射关系
    private HashMap<String, HashMap> fields = new HashMap<>() {
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
        if (fields.get(fieldName).containsKey(key))
            return false;
        fields.get(fieldName).put(key, object);
        return true;
    }


    /**
     * 在数据库中查找实体
     *
     * @param fieldName 待查找的实体所属的field名称
     * @param type      实体的类型
     * @return 查找结果列表
     */
    @SuppressWarnings("unchecked")
    public <K extends Number, T> SelectSession<K, T> select(String fieldName, Class<K> key, Class<T> type) {
        return new SelectSession<K, T>(fields.get(fieldName));
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
        return JSON.toJSONString(fields.get(fieldName));
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
        inspectionItemsCache = FileUtils.readJSONStringFromFile(paths.get("inspectionItems"), "{}");

        departments = FileUtils.loadHashMapFromFile(paths.get("departments"), Integer.class, Department.class);
        departmentsCache = FileUtils.readJSONStringFromFile(paths.get("departments"), "{}");

        medicines = FileUtils.loadHashMapFromFile(paths.get("medicines"), Integer.class, Medicine.class);
        medicinesCache = FileUtils.readJSONStringFromFile(paths.get("medicines"), "{}");

        diseases = FileUtils.loadHashMapFromFile(paths.get("diseases"), Integer.class, Disease.class);
        diseasesCache = FileUtils.readJSONStringFromFile(paths.get("diseases"), "{}");

        registrationLevels = FileUtils.loadHashMapFromFile(paths.get("registrationLevels"), Integer.class, RegistrationLevel.class);
        registrationLevelsCache = FileUtils.readJSONStringFromFile(paths.get("registrationLevels"), "{}");

        titles = FileUtils.loadHashMapFromFile(paths.get("titles"), Integer.class, Title.class);
        titlesCache = FileUtils.readJSONStringFromFile(paths.get("titles"), "{}");

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
        // todo 定时或监视增量数量时自动触发
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
