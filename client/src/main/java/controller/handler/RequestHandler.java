package controller.handler;

import lib.MessageUtils;
import net.ClientNetCenter;
import net.message.Message;
import net.message.MessageType;

/**
 * RequestHandler
 */
public class RequestHandler {
    /**
     * 请求发送前被调用
     *
     * @param methodName 传入的请求调用服务的方法名
     * @param request    传入的request内容
     */
    protected void before(String methodName, String request) {

    }

    /**
     * 发送请求
     *
     * @param methodName 传入的请求调用服务的方法名
     * @param request    传入的request内容
     * @return 响应
     */
    protected Message post(String methodName, String request) {
        return ClientNetCenter.INSTANCE.send(MessageUtils.buildRequest(methodName, request, MessageType.REQUEST));
    }

    /**
     * 响应返回后被调用
     *
     * @param message 响应
     */
    protected void after(Message message) {

    }

    /**
     * 处理一次请求
     *
     * @param methodName 请求调用服务的方法名
     * @param request    传入的request内容
     * @return 最终得到的Message对象
     */
    public Message process(String methodName, String request) {
        before(methodName, request);
        Message response = post(methodName, request);
        after(response);
        return response;
    }


}
