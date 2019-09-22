package database;


import com.alibaba.fastjson.JSON;
import lib.FileUtils;
import lib.LogUtils;
import model.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库
 */
public enum Database {
    INSTANCE;

    // 病历
    private Map<Long, MedicalRecord> medicalRecords = new ConcurrentHashMap<>();
    // 已经退费/完成的挂号记录，供查询
    private Map<Long, Registration> registrations = new ConcurrentHashMap<>();
    // 尚未完成/退费的挂号记录，供操作
    private Map<Long, Registration> newRegistrations = new ConcurrentHashMap<>();
    // 诊断
    private Map<Long, Diagnosis> diagnoses = new ConcurrentHashMap<>();
    // 检查记录
    private Map<Long, InspectionRecord> inspectionRecords = new ConcurrentHashMap<>();
    // 药方
    private Map<Long, Prescription> prescriptions = new ConcurrentHashMap<>();
    // 员工
    private Map<Integer, Staff> staffs = new ConcurrentHashMap<>();
    // 检查项目
    private Map<Integer, InspectionItem> inspectionItems = new ConcurrentHashMap<>();
    // 药物
    private Map<Integer, Medicine> medicines = new ConcurrentHashMap<>();
    // 疾病
    private Map<Integer, Disease> diseases = new ConcurrentHashMap<>();
    // 挂号等级
    private Map<Integer, RegistrationLevel> registrationLevels = new ConcurrentHashMap<>();
    // 科室
    private Map<Integer, Department> departments = new ConcurrentHashMap<>();
    // 职称
    private Map<Integer, Title> titles = new ConcurrentHashMap<>();


    // 因为极少更新，所以缓存成json字符串
    private String inspectionItemsCache;
    private String medicinesCache;
    private String diseasesCache;
    private String registrationLevelsCache;
    private String departmentsCache;
    private String titlesCache;


    // 存储数据与文件的映射关系
    private final HashMap<String, Path> paths = new HashMap<>() {
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
    private final HashMap<String, Map> fields = new HashMap<>() {
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

    private HashMap<String, String> caches = new HashMap<>();

    // 用于持久化的定时任务
    private final Timer persistTimer = new Timer();

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
     * 获取某一类数据的所有实体的json对象
     *
     * @param fieldName 待查找的实体所属的field名称
     * @return 查找结果的json
     */
    public Object selectAll(String fieldName) {
        if (caches.containsKey(fieldName))
            return JSON.parseObject(caches.get(fieldName));
        return JSON.toJSON(fields.get(fieldName));
    }


    /**
     * 启动时从文件中初始化数据
     */
    public void boot() {
        LogUtils.info("Database is booting");

        medicalRecords.putAll(FileUtils.loadMapFromFile(paths.get("medicalRecords"), Long.class, MedicalRecord.class));

        registrations.putAll(FileUtils.loadMapFromFile(paths.get("registrations"), Long.class, Registration.class));

        newRegistrations.putAll(FileUtils.loadMapFromFile(paths.get("newRegistrations"), Long.class, Registration.class));

        diagnoses.putAll(FileUtils.loadMapFromFile(paths.get("diagnoses"), Long.class, Diagnosis.class));

        inspectionRecords.putAll(FileUtils.loadMapFromFile(paths.get("inspectionRecords"), Long.class, InspectionRecord.class));

        prescriptions.putAll(FileUtils.loadMapFromFile(paths.get("prescriptions"), Long.class, Prescription.class));

        staffs.putAll(FileUtils.loadMapFromFile(paths.get("staffs"), Integer.class, Staff.class));

        inspectionItems.putAll(FileUtils.loadMapFromFile(paths.get("inspectionItems"), Integer.class, InspectionItem.class));
        inspectionItemsCache = FileUtils.readJSONStringFromFile(paths.get("inspectionItems"), "{}");

        departments.putAll(FileUtils.loadMapFromFile(paths.get("departments"), Integer.class, Department.class));
        departmentsCache = FileUtils.readJSONStringFromFile(paths.get("departments"), "{}");

        medicines.putAll(FileUtils.loadMapFromFile(paths.get("medicines"), Integer.class, Medicine.class));
        medicinesCache = FileUtils.readJSONStringFromFile(paths.get("medicines"), "{}");

        diseases.putAll(FileUtils.loadMapFromFile(paths.get("diseases"), Integer.class, Disease.class));
        diseasesCache = FileUtils.readJSONStringFromFile(paths.get("diseases"), "{}");

        registrationLevels.putAll(FileUtils.loadMapFromFile(paths.get("registrationLevels"), Integer.class, RegistrationLevel.class));
        registrationLevelsCache = FileUtils.readJSONStringFromFile(paths.get("registrationLevels"), "{}");

        titles.putAll(FileUtils.loadMapFromFile(paths.get("titles"), Integer.class, Title.class));
        titlesCache = FileUtils.readJSONStringFromFile(paths.get("titles"), "{}");

        // 存储缓存引用
        caches.put("inspectionItems", inspectionItemsCache);
        caches.put("medicines", medicinesCache);
        caches.put("diseases", diseasesCache);
        caches.put("registrationLevels", registrationLevelsCache);
        caches.put("departments", departmentsCache);
        caches.put("titles", titlesCache);

        // 开始定时持久化数据
        // todo 有时间再加一个到达一定增量调用持久化
        persistTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                persist();
            }
        }, 30_000, 30_000);

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

        // 由Map写入到文件
        flag = FileUtils.saveMapToFile(paths.get("medicalRecords"), medicalRecords, false);
        flag &= FileUtils.saveMapToFile(paths.get("registrations"), registrations, false);
        flag &= FileUtils.saveMapToFile(paths.get("newRegistrations"), newRegistrations, false);
        flag &= FileUtils.saveMapToFile(paths.get("diagnoses"), diagnoses, false);
        flag &= FileUtils.saveMapToFile(paths.get("inspectionRecords"), inspectionRecords, false);
        flag &= FileUtils.saveMapToFile(paths.get("prescriptions"), prescriptions, false);
        flag &= FileUtils.saveMapToFile(paths.get("staffs"), staffs, false);


        // 由缓存成的字符串直接写成文件
        flag &= FileUtils.writeStringToFile(paths.get("inspectionItems"), inspectionItemsCache, false);
        flag &= FileUtils.writeStringToFile(paths.get("departments"), departmentsCache, false);
        flag &= FileUtils.writeStringToFile(paths.get("medicines"), medicinesCache, false);
        flag &= FileUtils.writeStringToFile(paths.get("diseases"), diseasesCache, false);
        flag &= FileUtils.writeStringToFile(paths.get("registrationLevels"), registrationLevelsCache, false);
        flag &= FileUtils.writeStringToFile(paths.get("titles"), titlesCache, false);
        if (!flag)
            LogUtils.warn("Failed to save some data, please check");
        else
            LogUtils.info("Data has been saved completely");
        return flag;
    }

}
