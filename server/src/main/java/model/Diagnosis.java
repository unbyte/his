package model;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 诊断信息
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Diagnosis {
    /*初诊*/
    public static byte PRESUMPTIVE = (byte) 0;

    /*确诊*/
    public static byte FINAL = (byte) 1;

    private long id;

    /*主诉*/
    private String complaint;

    /*0-中 1-西*/
    private byte clazz;

    /*疾病*/
    private List<Integer> disease;

    /*建议*/
    private String suggestion;

    /*注意事项*/
    private String note;

    /*最终诊断信息*/
    private String judgement;

    private byte status;

}
