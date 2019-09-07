package controller;

import com.alibaba.fastjson.JSONObject;
import model.Staff;
import net.message.Message;

@FunctionalInterface
public interface Controller {
    Message process(JSONObject params, Staff user);
}
