package model;

import lombok.*;

/**
 * 疾病
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class Disease {
    private int id;
    private String name;
    /*助记码*/
    private String code;
    /*0-中 1-西*/
    private int clazz;


    /**
     * 新建一个疾病对象并获取
     *
     * @param clazz 分类 0 中/ 1 西
     * @param code  助记码
     * @param name  名称
     * @return id自动生成的疾病对象
     */
    public static Disease insert(String name, String code, int clazz) {
        int id = 0;//todo 唯一id生成规则
        return new Disease(id, name, code, clazz);
    }

}
