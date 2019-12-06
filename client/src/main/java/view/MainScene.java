package view;

import bridge.BridgeCenter;
import bridge.ConfigBridge;
import bridge.LifecycleBridge;
import bridge.RequestBridge;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScene implements Initializable {
    @FXML
    WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WebEngine webEngine = webView.getEngine();

        // 载入前端页面
        webEngine.load(getClass().getResource("../html/index.html").toExternalForm());

        // 在webview中禁用右键
        webView.setContextMenuEnabled(false);

        // 使得webview中的表单能获取到焦点
        webView.requestFocus();

        // 把WebEngine对象注册进BridgeMaster工厂
        BridgeCenter.setWebEngine(webEngine);

        // 指定需要挂载到JS运行时里的对象
        BridgeCenter.addBridge("lifecycle", new LifecycleBridge());
        BridgeCenter.addBridge("request", new RequestBridge());
        BridgeCenter.addBridge("config", new ConfigBridge());
    }

    /**
     * 改变窗口的大小
     *
     * @param newWidth  新的宽度
     * @param newHeight 新的高度
     */
    public void changeSize(double newWidth, double newHeight) {

        // 思路是，更改窗口尺寸的时候关闭窗口，同时消息传到webview中，经过js处理，通过js调用显示窗口，以实现平滑过渡
        Stage stage = ViewCenter.getStage("main");
        stage.hide();
        stage.setWidth(newWidth);
        stage.setHeight(newHeight);
        webView.setPrefWidth(newWidth);
        webView.setPrefHeight(newHeight);
        stage.centerOnScreen();

        // show在webview中载入完后使用js调用
    }
}
