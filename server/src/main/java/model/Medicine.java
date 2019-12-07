package model;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 药物
 */
@Accessors(chain = true)
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

    private int stock;

}
