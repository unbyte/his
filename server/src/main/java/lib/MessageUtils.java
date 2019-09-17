package lib;

import com.alibaba.fastjson.JSONObject;
import net.message.Header;
import net.message.Message;
import net.message.MessageType;

/**
 * 消息构造工具类
 */
public class MessageUtils {
    /**
     * 成功状态码
     */
    public static byte SUCCESS = (byte) 0;
    /**
     * not found状态码
     */
    public static byte NOT_FOUND = (byte) 1;
    /**
     * bad request状态码
     */
    public static byte BAD_REQUEST = (byte) 2;
    /**
     * no permission状态码
     */
    public static byte NO_PERMISSION = (byte) 3;


    public static byte FAIL = (byte)4;

    /**
     * 为业务响应构造Message
     *
     * @param status 状态码
     * @param msg    msg载荷
     * @return Message对象
     */
    public static Message buildResponse(byte status, Object msg) {
        return buildResponse(status, msg, MessageType.RESPONSE);
    }

    /**
     * 为各种响应构造Message
     *
     * @param status 状态码
     * @param msg    msg载荷
     * @param type   消息类型
     * @return Message对象
     */
    public static Message buildResponse(byte status, Object msg, byte type) {
        return new Message().setHeader(
                new Header().setType(type)
                .setStatus(status)
        ).setBody(
                new JSONObject().fluentPut("status", status)
                        .fluentPut("msg", msg)
                        .toJSONString()
        );
    }

    /**
     * 构造心跳包响应
     *
     * @return 心跳包响应
     */
    public static Message buildHeartBeat() {
        return new Message().setHeader(
                new Header().setType(
                        MessageType.HEARTBEAT_RES
                )
        );
    }


}
