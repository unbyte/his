package model;


import lombok.*;

/**
 * 职称
 */
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
