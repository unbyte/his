package model;

import lombok.*;

/**
 * 挂号等级
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RegistrationLevel {

    private int id;

    /*每日限制数*/
    private int limit;

    private double fee;
}
