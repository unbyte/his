package model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 科室
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class Department {
    private int id;
    private String name;
    /* 分类 0 门诊部门 1 药师部门 2 技师部门 3 前台*/
    private int clazz;


    /**
     * 新建一个科室对象并获取
     *
     * @param name  名称
     * @param clazz 分类
     * @return id自动生成的科室对象
     */
    public static Department insert(String name, int clazz) {
        int id = 0;//todo 唯一id生成规则
        return new Department(id, name, clazz);
    }
}
