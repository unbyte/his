package model;

import lombok.*;

/**
 * 处方中的药物清单组成
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class PrescriptionItem {
    /*药物id*/
    private int medicineID;
    /*用量*/
    private String dosage;
    /*用法*/
    private String usage;
    /*频次*/
    private String frequency;
    /*数量*/
    private int amount;
}
