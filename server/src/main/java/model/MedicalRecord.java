package model;

import lib.IDGenerator;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 病历
 */
@Accessors(chain = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
@Getter
@Setter
public class MedicalRecord {
    /*病历号*/
    private long id;

    private String name;

    private long birthday;
    /*0 男 1 女*/
    private byte gender;

    private String IDNumber;

    private String address;

    /*既往史*/
    private String pastHistory;

    /*现病史*/
    private String presentIllnessHistory;

    /*过敏史*/
    private String allergyHistory;

    /*现病治疗情况*/
    private String currentIllnessTreatment;

    /**
     * 新建一个病历对象并获取
     *
     * @param name                    患者姓名
     * @param birthday                出生日期
     * @param gender                  性别 0为女，1为男
     * @param IDNumber                身份证号码
     * @param address                 家庭住址
     * @param pastHistory             既往史
     * @param presentIllnessHistory   现病史
     * @param allergyHistory          过敏史
     * @param currentIllnessTreatment 现病治疗情况
     * @return 一个病历号自动生成的病历对象
     */
    public static MedicalRecord insert(String name, long birthday, byte gender, String IDNumber, String address, String pastHistory, String presentIllnessHistory, String allergyHistory, String currentIllnessTreatment) {
        return new MedicalRecord(IDGenerator.generate(), name, birthday, gender, IDNumber, address, pastHistory, presentIllnessHistory, allergyHistory, currentIllnessTreatment);
    }
}
