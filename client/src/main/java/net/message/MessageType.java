package net.message;

/**
 * 消息类型
 */
public class MessageType {
    /**
     * 0 - 心跳请求
     */
    public static byte HEARTBEAT_REQ = (byte) 0;
    /**
     * 1 - 心跳响应
     */
    public static byte HEARTBEAT_RES = (byte) 1;
    /**
     * 2 - 业务请求
     */
    public static byte REQUEST = (byte) 2;
    /**
     * 3 - 业务响应
     */
    public static byte RESPONSE = (byte) 3;

    /**
     * 4 - 建立链接请求(仅在登陆与重新链接时
     */
    public static byte CONNECT_REQ = (byte) 4;
    /**
     * 5 - 建立链接响应
     */
    public static byte CONNECT_RES = (byte) 5;
    /**
     * 6 - 服务端推送
     */
    public static byte PUSH = (byte) 6;
    
}
