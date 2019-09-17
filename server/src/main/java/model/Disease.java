package model;

import lombok.*;

/**
 * 疾病
 */
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

}
