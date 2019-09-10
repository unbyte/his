package bridge;

import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * 管理各个Bridge实现类并挂载到js运行时中
 */
public class BridgeCenter {
    private static HashMap<String, Bridge> bridges = new HashMap<>();
    private static WebEngine webEngine;
    private static JSObject win;

    /**
     * 挂载Bridge的实现对象到js运行时，并将对象注册进Bridge容器中，防止被GC回收
     */
    public static void addBridge(String name, Bridge bridge) {
        if (webEngine == null || bridge == null)
            // todo 得有一个显示报错信息的机制，弄一个Stage专门显示吧
            return;
        if (win == null)
            win = (JSObject) webEngine.executeScript("window");
        try {
            bridges.put(name, bridge);
            win.setMember(name, bridges.get(name));
        } catch (Exception ignored) {
        }
    }


    public static void setWebEngine(WebEngine webEngine) {
        BridgeCenter.webEngine = webEngine;
    }
}
