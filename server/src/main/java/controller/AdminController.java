package controller;

import com.alibaba.fastjson.JSONObject;
import database.Database;
import lib.MessageUtils;
import lib.Tuple;
import model.Department;
import model.Disease;
import model.Staff;
import org.apache.logging.log4j.core.appender.ScriptAppenderSelector;

import java.util.ArrayList;
import java.util.Map;

public class AdminController implements Controller {
    @Override
    public Tuple process(String methodName, JSONObject params, Staff user) {
        // 先检查用户权限
        if (Database.INSTANCE.select("departments", Integer.class, Department.class).getRaw().get(user.getDepartment()).getClazz() != Department.ADMIN)
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "越权操作"));

        // 转发到具体的处理方法
        switch (methodName) {
            case "admin-update-disease-tree-struct":
                return updateDiseaseTreeStruct(params);
            case "admin-add-disease":
                return addDisease(params);
            case "admin-edit-disease":
                return editDisease(params);
            case "admin-remove-disease":
                return removeDisease(params);
        }
        return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "目的行为不存在"));
    }

    private Tuple updateDiseaseTreeStruct(JSONObject params) {
        int nodeID, dropNodeID;
        String type;
        try {
            nodeID = params.getInteger("nodeID");
            dropNodeID = params.getInteger("dropNodeID");
            type = params.getString("type");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Map<Integer, Disease> diseases = Database.INSTANCE.select("diseases", Integer.class, Disease.class).getRaw();

        if (!diseases.containsKey(nodeID) || !diseases.containsKey(dropNodeID))
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "病种不存在"));


        Disease node = diseases.get(nodeID);
        Disease dropNode = diseases.get(dropNodeID);

        switch (type) {
            case "before":
            case "after":
                node.getParent().getChildren().remove(node);
                node.setParent(dropNode.getParent());
                dropNode.getParent().getChildren().add(node);
                break;
            case "inside":
                node.getParent().getChildren().remove(node);
                node.setParent(dropNode);
                dropNode.getChildren().add(node);
                break;
        }

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "更新结构成功"));
    }

    private Tuple addDisease(JSONObject params) {
        int parentID;
        String name, code;
        try {
            parentID = params.getInteger("parentID");
            name = params.getString("name");
            code = params.getString("code");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Map<Integer, Disease> diseases = Database.INSTANCE.select("diseases", Integer.class, Disease.class).getRaw();

        if (!diseases.containsKey(parentID))
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该病种不存在"));


        Disease parent = diseases.get(parentID);

        int id = 100 + diseases.size();
        Disease newDisease = new Disease(id, name, code, 1, parent, new ArrayList<>());
        parent.getChildren().add(newDisease);
        diseases.put(id, newDisease);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, id));
    }

    private Tuple removeDisease(JSONObject params) {
        int id;
        try {
            id = params.getInteger("id");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Map<Integer, Disease> diseases = Database.INSTANCE.select("diseases", Integer.class, Disease.class).getRaw();

        if (!diseases.containsKey(id))
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该病种不存在"));
        Disease disease = diseases.get(id);
        disease.getParent().getChildren().remove(disease);
        diseases.remove(id);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "移除病种成功"));
    }

    private Tuple editDisease(JSONObject params) {
        int id;
        String name, code;
        try {
            id = params.getInteger("id");
            name = params.getString("name");
            code = params.getString("code");
        } catch (ClassCastException e) {
            return new Tuple(MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数类型错误"));
        }

        Map<Integer, Disease> diseases = Database.INSTANCE.select("diseases", Integer.class, Disease.class).getRaw();

        if (!diseases.containsKey(id))
            return new Tuple(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该病种不存在"));

        diseases.get(id).setName(name).setCode(code);

        return new Tuple(MessageUtils.buildResponse(MessageUtils.SUCCESS, "修改成功"));
    }

}
