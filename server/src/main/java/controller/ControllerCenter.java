package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lib.MessageUtils;
import lib.Tuple;
import model.Staff;

import java.util.HashMap;

public enum ControllerCenter {
    INSTANCE;

    private HashMap<String, Controller> controllers = new HashMap<>();

    public Tuple forwardTask(String methodName, JSONObject params, Staff user) {
        if (!controllers.containsKey(methodName))
            // 若没有对应的controller
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "目的行为不存在"));
        return controllers.get(methodName).process(params, user);
    }

    public Tuple forwardTask(String methodName, String params, Staff user) {
        return forwardTask(methodName, JSON.parseObject(params), user);
    }


    {
        // 初始化controller映射表
        controllers.put("login", new LoginController());

    }
}