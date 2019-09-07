package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 挂号等级
 */
@ToString
public class RegistrationLevel {
    @Getter
    private int id;
    @Getter
    private String name;
    @Getter /*每日限制数*/
    private int limit;
    @Getter
    @Setter
    private double fee;

    private RegistrationLevel(int id, String name, int limit, double fee) {
        this.id = id;
        this.name = name;
        this.limit = limit;
        this.fee = fee;
    }

    /**
     * 新建一个挂号等级对象并获取
     *
     * @param name  等级名称
     * @param limit 每日挂号数限制
     * @param fee   费用
     * @return id自动生成的挂号等级对象
     */
    public static RegistrationLevel insert(String name, int limit, double fee) {
        int id = 0;//todo 唯一id生成规则
        return new RegistrationLevel(id, name, limit, fee);
    }

}
