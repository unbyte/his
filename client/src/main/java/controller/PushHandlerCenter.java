package controller;

import bridge.BridgeCenter;
import javafx.application.Platform;
import net.message.Message;
import net.message.MessageType;
import netscape.javascript.JSObject;

public class PushHandlerCenter {
    public static void handle(Message message) {
        // 判断消息类型
        if (message.getHeader().getType() != MessageType.PUSH)
            return;

        // 执行vue暴露出来的接收方法
        Platform.runLater(() -> {
            JSObject window = (JSObject) BridgeCenter.getWebEngine().executeScript("window");
            window.eval("window.inform('" + message.getBody() + "')");
        });
    }
}
