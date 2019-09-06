package bridge;

import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

import java.util.HashMap;

/**
 * 管理各个Bridge实现类并挂载到js运行时中
 */
public class BridgeMaster {
    private static HashMap<String, Bridge> bridges = new HashMap<>();
    private static WebEngine webEngine = null;
    private static JSObject win;
    private final static String prefix = "bridge.";

    /**
     * 根据类名挂载Bridge的实现对象到js运行时，并将对象注册进Bridge容器中，防止被GC回收<br/>挂载后的引用名称是类名的小写形式。
     *
     * @param className 需要实例化并挂载的Bridge实现类类名
     */
    public static void addBridge(String className) {
        if (win == null)
            win = (JSObject) webEngine.executeScript("window");
        try {
            bridges.put(className, (Bridge) Class.forName(prefix + className).getConstructor().newInstance());
        } catch (Exception ignored) {
            return;
        }
        win.setMember(className.toLowerCase(), bridges.get(className));
    }


    public static void setWebEngine(WebEngine webEngine) {
        BridgeMaster.webEngine = webEngine;
    }
}
