package model;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 检查/检验项目
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class InspectionItem {

    private int id;
    /*助记码*/
    private String code;

    private String name;

    /*执行科室*/
    private int departmentID;

    private double fee;
}
