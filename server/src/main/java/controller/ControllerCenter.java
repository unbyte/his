package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lib.MessageUtils;
import lib.Tuple;
import model.Staff;

import java.util.HashMap;
import java.util.Map;

public enum ControllerCenter {
    INSTANCE;

    private HashMap<String, Controller> controllers = new HashMap<>();

    public Tuple forwardTask(String methodName, JSONObject params, Staff user) {
        Controller controller = controllers.entrySet().stream()
                .filter(i -> methodName.startsWith(i.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
        // 若没有对应的controller
        if (controller == null)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "目的行为不存在"));
        return controller.process(methodName, params, user);
    }

    public Tuple forwardTask(String methodName, String params, Staff user) {
        return forwardTask(methodName, JSON.parseObject(params), user);
    }


    {
        // todo 在这里注册controller
        // 初始化controller映射表
        // 只要请求的方法名以以下某字符串开头，则匹配到对应的controller
        controllers.put("login", new LoginController());
        controllers.put("query", new QueryController());
        controllers.put("front-desk", new FrontDeskController());
    }
}