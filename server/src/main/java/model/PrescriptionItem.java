package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 处方中的药物清单组成
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
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
