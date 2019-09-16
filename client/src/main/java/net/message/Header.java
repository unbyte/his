package net.message;


/**
 * 消息头
 */
public class Header {
    private int crcCode = 0x2817767d;
    private int length;
    private byte type;
    private byte status;

    /**
     * 获取校验码
     *
     * @return 校验码
     */
    public int getCrcCode() {
        return crcCode;
    }

    /**
     * 设置校验码
     *
     * @param crcCode 校验码
     * @return 消息头对象
     */
    public Header setCrcCode(int crcCode) {
        this.crcCode = crcCode;
        return this;
    }

    /**
     * 获取消息长度，该长度指由本字段结束位置到消息结束位置的长度
     *
     * @return 消息长度
     */
    public int getLength() {
        return length;
    }

    /**
     * 设置消息长度
     *
     * @param length 消息长度
     * @return 消息头对象
     */
    public Header setLength(int length) {
        this.length = length;
        return this;
    }

    /**
     * 获取消息类型，需与{@link MessageType}进行比较
     *
     * @return 消息类型
     */
    public byte getType() {
        return type;
    }

    /**
     * 设置消息类型
     *
     * @param type 消息类型
     * @return 消息头对象
     */
    public Header setType(byte type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "header [ crcCode=" + crcCode + ", length=" + length + ", type=" + type + ", status=" + status;
    }


    public byte getStatus() {
        return status;
    }

    public Header setStatus(byte status) {
        this.status = status;
        return this;
    }
}
