package controller.handler;

import bridge.BridgeCenter;
import bridge.ConfigBridge;
import lib.MessageUtils;
import net.ClientNetCenter;
import net.message.Message;
import net.message.MessageType;

public class LoginRequestHandler extends RequestHandler {
    @Override
    protected void before(String methodName, String request) {
        // 登陆前获取连接服务器的配置信息（地址，端口），启动NetCenter
        ConfigBridge configBridge = (ConfigBridge) BridgeCenter.getBridge("config");
        String address = configBridge.getConfig("server_address");
        int port = Integer.parseInt(configBridge.getConfig("server_port"));
        ClientNetCenter.INSTANCE.start(address,port);
    }

    @Override
    protected Message post(String methodName, String request) {
        return ClientNetCenter.INSTANCE.send(MessageUtils.buildRequest(methodName, request, MessageType.CONNECT_REQ.type()));
    }

    @Override
    protected void after(Message message) {
        //todo 若成功则挂载信息、改变窗口大小; 若失败则断开连接
    }
}
