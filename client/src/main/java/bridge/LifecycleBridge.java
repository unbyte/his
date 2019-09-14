package bridge;

import javafx.application.Platform;

/**
 * 方便js调用java主程序的生命周期方法
 */
public class LifecycleBridge implements Bridge {
    /**
     * 将js中的数据打印到java控制台中，方便调试
     *
     * @param message 需要打印的数据
     */
    public <T> void log(T message) {
        System.out.println(message);
    }

    /**
     * 关闭java主程序
     */
    public void exit() {
        Platform.exit();
    }
}
