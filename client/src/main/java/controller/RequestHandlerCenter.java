package controller;

import controller.handler.DefaultRequestHandler;
import controller.handler.LoginRequestHandler;
import controller.handler.RequestHandler;
import net.message.Message;

import java.util.HashMap;

public class RequestHandlerCenter {
    private static HashMap<String, RequestHandler> handlers = new HashMap<>();

    static {
        handlers.put("login", new LoginRequestHandler());
        handlers.put("default", new DefaultRequestHandler());
    }

    public static String handle(String methodName, String request) {
        // 获取Handler
        RequestHandler handler = handlers.containsKey(methodName) ? handlers.get(methodName) : handlers.get("default");
        // 返回Handler处理过后的响应，所以命名为result
        Message result = handler.process(methodName, request);
        // 返回响应体内容
        return result.getBody();
    }

}
