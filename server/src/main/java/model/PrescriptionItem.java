package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 处方中的药物清单组成
 */
@AllArgsConstructor
@ToString
public class PrescriptionItem {
    @Getter /*药物id*/
    private int medicineID;
    @Getter /*用量*/
    private String dosage;
    @Getter /*用法*/
    private String usage;
    @Getter /*频次*/
    private String frequency;
    @Getter /*数量*/
    private int amount;
}
