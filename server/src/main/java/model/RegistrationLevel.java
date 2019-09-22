package model;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 挂号等级
 */
@Accessors(chain = true)
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
