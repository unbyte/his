package model;

import lombok.*;

/**
 * 药物
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Medicine {
    private int id;

    /*助记码*/
    private String code;

    private String name;

    private String format;

    private double price;

}
