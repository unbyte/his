package model;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 科室
 */
@Accessors(chain = true)
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Department {
    public static byte OUTPATIENT = 0;
    public static byte PHARMACY = 1;
    public static byte MEDICAL_TECHNIQUE = 2;
    public static byte FRONT_DESK= 3;

    private int id;
    private String name;
    /* 分类 0 门诊部门 1 药师部门 2 技师部门 3 前台*/
    private byte clazz;
}
