package controller;

import com.alibaba.fastjson.JSONObject;
import database.Database;
import lib.MessageUtils;
import lib.Tuple;
import model.Department;
import model.Staff;

import java.util.ArrayList;

public class AdminController implements Controller {
    @Override
    public Tuple process(String methodName, JSONObject params, Staff user) {
        // 先检查用户权限
        if (Database.INSTANCE.select("departments", Integer.class, Department.class).getRaw().get(user.getDepartment()).getClazz() != Department.ADMIN)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "越权操作"));

        // 转发到具体的处理方法
        switch (methodName) {

        }
        return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "目的行为不存在"));
    }
}
