package model;

/**
 * 项目状态
 */
public class Status {
    /*
     * 0 暂存
     * 1 未付费
     * 2 未消费
     * 3 已消费
     * 4 已作废 （检查项目与处方专用，必须已作废才能退费
     * 5 已退费
     * 6 已作废（直接由UNPAID作废得到这个状态
     */
    public static final byte TEMPORARY = 0;
    public static final byte UNPAID = 1;
    public static final byte UNCONSUMED = 2;
    public static final byte CONSUMED = 3;
    public static final byte CANCELED = 4;
    public static final byte REFUNDED = 5;
    public static final byte CANCELED_WITHOUT_PAID = 6;
}
