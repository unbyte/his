package model;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;

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
    public static final byte OUTPATIENT = 0;
    public static final byte PHARMACY = 1;
    public static final byte MEDICAL_TECHNIQUE = 2;
    public static final byte FRONT_DESK = 3;
    public static final byte ADMIN = 8;

    private int id;
    private String name;
    /* 分类 0 门诊部门 1 药师部门 2 技师部门 3 前台 8 管理员*/
    private byte clazz;

    private Department parent;
    private ArrayList<Department> children;
}
