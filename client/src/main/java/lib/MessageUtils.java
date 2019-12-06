package lib;


import net.message.Header;
import net.message.Message;


/**
 * 消息构造工具类
 */
public class MessageUtils {
    /**
     * 成功状态码
     */
    public static final byte SUCCESS = (byte) 0;
    /**
     * not found状态码
     */
    public static final byte NOT_FOUND = (byte) 1;
    /**
     * bad request状态码
     */
    public static final byte BAD_REQUEST = (byte) 2;
    /**
     * no permission状态码
     */
    public static final byte NO_PERMISSION = (byte) 3;

    /**
     * 为请求构造Message
     *
     * @param methodName 请求的方法
     * @param request    请求内容
     * @param type       消息类型
     * @return Message对象
     */
    public static Message buildRequest(String methodName, String request, byte type) {
        return new Message().setHeader(
                new Header().setType(type)
        ).setBody(
                request
        );
    }


}
