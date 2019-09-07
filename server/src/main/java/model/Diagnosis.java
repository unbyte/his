package model;

import lib.IDGenerator;
import lombok.*;

/**
 * 诊断信息
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Diagnosis {
    @Getter
    private long id;
    @Getter /*挂号id*/
    private long registrationID;
    @Getter /*主诉*/
    private String complaint;
    @Getter /*0-中 1-西*/
    private int clazz;
    @Getter
    @Setter /*疾病*/
    private String disease;
    @Getter /*建议*/
    private String suggestion;
    @Getter
    @Setter /*注意事项*/
    private String note;
    @Getter
    @Setter /*最终诊断信息*/
    private String judgement;
    @Getter
    @Setter
    private int status;


    /**
     * 新建一个诊断信息对象并获取
     *
     * @param registrationID 挂号记录id
     * @param complaint      主诉
     * @param clazz          分类 0中医 1西医
     * @param disease        疾病诊断
     * @param suggestion     检查建议
     * @param note           注意事项
     * @param judgement      最终诊断结果
     * @param status         诊断状态
     * @return 一个id自动生成的诊断信息对象
     */
    public static Diagnosis insert(long registrationID, String complaint, int clazz, String disease, String suggestion, String note, String judgement, int status) {
        return new Diagnosis(IDGenerator.generate(), registrationID, complaint, clazz, disease, suggestion, note, judgement, status);
    }

}
