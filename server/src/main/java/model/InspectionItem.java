package model;

import lombok.*;

/**
 * 检查/检验项目
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class InspectionItem {
    @Getter
    private int id;
    @Getter /*助记码*/
    private String code;
    @Getter
    private String name;
    @Getter
    @Setter
    private double fee;

    /**
     * 新建一个检查检验项目并获取
     *
     * @param code 助记码
     * @param name 名称
     * @param fee  费用
     * @return id自动生成的检查检验项目
     */
    public static InspectionItem insert(String code, String name, double fee) {
        int id = 0;//todo 唯一id生成规则
        return new InspectionItem(id, code, name, fee);
    }

}
