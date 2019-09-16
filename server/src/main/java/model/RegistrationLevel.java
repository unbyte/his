package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 挂号等级
 */
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RegistrationLevel {

    private int id;

    private String name;
    /*每日限制数*/
    private int limit;

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
