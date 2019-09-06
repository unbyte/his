package bridge.handler;


import java.util.HashMap;

/**
 * 管理所有RequestHook的实现类对象
 */
public class RequestHandlerMaster {
    private static HashMap<String, RequestHandler> hooks = new HashMap<>();
    // 反射取得的类名前面会有包前缀
    private final static String prefix = "bridge.handler.";

    /**
     * 根据请求的方法名，取得小写化后同名的RequestHook的实现类对象，以便于调用
     *
     * @param methodName 请求的方法命，如{@code login}, {@code logout}
     * @return 返回RequestHook实现类对象，若无则为null
     */
    public static RequestHandler getHook(String methodName) {
        RequestHandler hook = hooks.get(methodName.toLowerCase());
        if (hook == null) {
            try {
                hooks.put(methodName.toLowerCase(), (RequestHandler) Class.forName(prefix + methodName).getConstructor().newInstance());
                hook = hooks.get(methodName.toLowerCase());
            } catch (Exception ignored) {
                return null;
            }
        }
        return hook;
    }
}
