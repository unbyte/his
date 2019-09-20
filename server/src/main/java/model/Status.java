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
     */
    public static byte TEMPORARY = 0;
    public static byte UNPAID = 1;
    public static byte UNCONSUMED = 2;
    public static byte CONSUMED = 3;
    public static byte CANCELED = 4;
    public static byte REFUNDED = 5;
}
