package controller.handler;

import lib.MessageUtils;
import net.NetCenter;
import net.message.Message;
import net.message.MessageType;

public class LoginRequestHandler extends RequestHandler {
    @Override
    protected void before(String methodName, String request) {
        //todo 登陆前获取连接服务器的配置信息（地址，端口），启动NetCenter
    }

    @Override
    protected Message post(String methodName, String request) {
        return NetCenter.INSTANCE.send(MessageUtils.buildRequest(methodName, request, MessageType.CONNECT_REQ.type()));
    }

    @Override
    protected void after(Message message) {
        //todo 若成功则挂载信息、改变窗口大小; 若失败则断开连接
    }
}
