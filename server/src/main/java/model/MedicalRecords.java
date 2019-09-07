package model;

import lib.IDGenerator;
import lombok.*;

/**
 * 病历
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class MedicalRecords {
    @Getter /*病历号*/
    private long id;
    @Getter
    private String name;
    @Getter
    private long birthDate;
    @Getter
    private int gender;
    @Getter
    private String idCard;
    @Getter
    private String address;
    @Getter
    @Setter /*既往史*/
    private String pastHistory;
    @Getter
    @Setter /*现病史*/
    private String presentIllnessHistory;
    @Getter
    @Setter /*过敏史*/
    private String allergyHistory;
    @Getter
    @Setter /*现病治疗情况*/
    private String currentIllnessTreatment;

    /**
     * 新建一个病历对象并获取
     *
     * @param name                    患者姓名
     * @param birthDate               出生日期
     * @param gender                  性别 0为女，1为男
     * @param idCard                  身份证号码
     * @param address                 家庭住址
     * @param pastHistory             既往史
     * @param presentIllnessHistory   现病史
     * @param allergyHistory          过敏史
     * @param currentIllnessTreatment 现病治疗情况
     * @return 一个病历号自动生成的病历对象
     */
    public static MedicalRecords insert(String name, long birthDate, int gender, String idCard, String address, String pastHistory, String presentIllnessHistory, String allergyHistory, String currentIllnessTreatment) {
        return new MedicalRecords(IDGenerator.generate(), name, birthDate, gender, idCard, address, pastHistory, presentIllnessHistory, allergyHistory, currentIllnessTreatment);
    }
}
