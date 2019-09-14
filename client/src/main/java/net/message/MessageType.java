package net.message;

/**
 * 消息类型
 */
public enum MessageType {
    /**
     * 0 - 心跳请求
     */
    HEARTBEAT_REQ((byte) 0),
    /**
     * 1 - 心跳响应
     */
    HEARTBEAT_RES((byte) 1),
    /**
     * 2 - 业务请求
     */
    REQUEST((byte) 2),
    /**
     * 3 - 业务响应
     */
    RESPONSE((byte) 3),

    /**
     * 4 - 建立链接请求(仅在登陆与重新链接时
     */
    CONNECT_REQ((byte) 4),
    /**
     * 5 - 建立链接响应
     */
    CONNECT_RES((byte) 5),
    /**
     * 6 - 服务端推送
     */
    PUSH((byte) 6);

    private byte type;

    MessageType(byte type) {
        this.type = type;
    }

    /**
     * 获取类型对应的byte值
     *
     * @return 类型值
     */
    public byte type() {
        return type;
    }
}
