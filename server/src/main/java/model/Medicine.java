package model;

import lombok.*;

/**
 * 药物
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
@Getter
public class Medicine {
    private int id;

    /*助记码*/
    private String code;

    private String name;

    private String format;

    @Setter
    private double price;

    /**
     * 新建一个药物对象并获取
     *
     * @param code   助记码
     * @param name   名称
     * @param format 规格
     * @param price  价格
     * @return id自动生成的药物对象
     */
    public static Medicine insert(String code, String name, String format, double price) {
        int id = 0; //todo 唯一id生成规则
        return new Medicine(id, code, name, format, price);
    }

}
