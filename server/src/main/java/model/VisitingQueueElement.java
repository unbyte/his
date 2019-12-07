package model;

import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class VisitingQueueElement {
    public static final byte TYPE_A = 0;
    public static final byte TYPE_B = 1;
    public static final byte TYPE_URGENT = 2;

    Registration registration;
    int power;
    byte type;
}
