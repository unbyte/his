package model;

import lib.IDGenerator;

/**
 * 检查/检验记录
 */
public class InspectionRecord {
    private long id;
    private long registrationID;
    private long time;
    private int item;
    private String part;
    private String request;
    private String result;
    private int status;

    private InspectionRecord(long id, long registrationID, long time, int item, String part, String request, String result, int status) {
        this.id = id;
        this.registrationID = registrationID;
        this.time = time;
        this.item = item;
        this.part = part;
        this.request = request;
        this.result = result;
        this.status = status;
    }

    /**
     * 新建一个检查记录对象并获取
     *
     * @param registrationID 检查记录所属的挂号记录id
     * @param time           检查时间
     * @param item           检查项目
     * @param part           检查部位
     * @param request        检查要求
     * @param result         检查结果
     * @param status         状态id
     * @return 一个id自动生成的检查记录对象
     */
    public static InspectionRecord insert(long registrationID, long time, int item, String part, String request, String result, int status) {
        return new InspectionRecord(IDGenerator.generate(), registrationID, time, item, part, request, result, status);
    }

    /**
     * 获取id
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * 获取所属的挂号记录id
     *
     * @return 挂号记录id
     */
    public long getRegistrationID() {
        return registrationID;
    }

    /**
     * 获取检查时间
     *
     * @return 检查时间
     */
    public long getTime() {
        return time;
    }

    /**
     * 获取检查项目id
     *
     * @return 检查项目id
     */
    public int getItem() {
        return item;
    }

    /**
     * 获取检查部位
     *
     * @return 检查部位
     */
    public String getPart() {
        return part;
    }

    /**
     * 获取检查要求
     *
     * @return 检查要求
     */
    public String getRequest() {
        return request;
    }

    /**
     * 获取检查结构
     *
     * @return 检查结果
     */
    public String getResult() {
        return result;
    }

    /**
     * 获取状态id
     *
     * @return 状态id
     */
    public int getStatus() {
        return status;
    }

    /**
     * 更新状态id
     *
     * @param status 新状态
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
