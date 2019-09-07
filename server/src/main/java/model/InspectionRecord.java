package model;

import lib.IDGenerator;
import lombok.*;

/**
 * 检查/检验记录
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class InspectionRecord {
    @Getter
    private long id;
    @Getter /*挂号记录id*/
    private long registrationID;
    @Getter
    private long time;
    @Getter /*检查项目*/
    private int item;
    @Getter /*检查部位*/
    private String part;
    @Getter /*检查要求*/
    private String request;
    @Getter
    @Setter /*检查结果*/
    private String result;
    @Getter
    @Setter
    private int status;

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
}
