package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

/**
 * 疾病
 */
@Accessors(chain = true)
@AllArgsConstructor
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

    // 冗余一下方便找父节点
    private Disease parent;

    private ArrayList<Disease> children;
}
