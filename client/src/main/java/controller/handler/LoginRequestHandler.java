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
        // 若失败则断开连接
        if (message.getHeader().getStatus() != MessageUtils.SUCCESS) {
            ClientNetCenter.INSTANCE.stop();
            return;
        }

        // 若成功则改变窗口大小
        MainScene mainScene = ViewCenter.getScene("MainScene", MainScene.class);

        // 窗口大小1024*768（暂定
        mainScene.changeSize(1280, 800);
        // todo 继续写登陆之后的界面变化
    }
}
