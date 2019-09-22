package model;


import lombok.*;
import lombok.experimental.Accessors;

/**
 * 职称
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Title {
    private int id;

    private String name;

    /*返回的是registrationLevel的id*/
    private int registrationLevel;
}
