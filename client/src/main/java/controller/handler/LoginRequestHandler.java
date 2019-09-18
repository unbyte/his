package controller.handler;

import bridge.BridgeCenter;
import bridge.ConfigBridge;
import lib.MessageUtils;
import net.ClientNetCenter;
import net.message.Message;
import net.message.MessageType;
import view.MainScene;
import view.ViewCenter;

public class LoginRequestHandler extends RequestHandler {
    @Override
    protected void before(String methodName, String request) {
        // 登陆前获取连接服务器的配置信息（地址，端口），启动NetCenter
        ConfigBridge configBridge = (ConfigBridge) BridgeCenter.getBridge("config");
        String address = configBridge.getConfig("server_address");
        int port = Integer.parseInt(configBridge.getConfig("server_port"));
        ClientNetCenter.INSTANCE.start(address, port);
    }

    @Override
    protected Message post(String methodName, String request) {
        return ClientNetCenter.INSTANCE.send(MessageUtils.buildRequest(methodName, request, MessageType.CONNECT_REQ));
    }

    @Override
    protected void after(Message message) {
        // 若失败则不改变
        if (message.getHeader().getStatus() != MessageUtils.SUCCESS)
            return;

        // 若成功则改变窗口大小
        MainScene mainScene = ViewCenter.getScene("MainScene", MainScene.class);

        // 窗口大小1280x800（暂定
        mainScene.changeSize(1280, 800);
    }
}
