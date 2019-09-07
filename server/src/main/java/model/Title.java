package model;


import lombok.*;

/**
 * 职称
 */
@ToString
@AllArgsConstructor
public class Title {
    @Getter
    private int id;
    @Getter
    private String name;
    @Getter /*返回的是registrationLevel的id*/
    private int registrationLevel;
}
