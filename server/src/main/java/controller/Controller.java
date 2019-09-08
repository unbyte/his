package controller;

import com.alibaba.fastjson.JSONObject;
import lib.Tuple;
import model.Staff;

@FunctionalInterface
public interface Controller {
    /**
     * 返回的结果Tuple规约如下<br/>
     * 第0个对象必须是Message对象<br/>
     * 若需要返回额外对象，需要按对象类型首字母由小到大顺序填入
     *
     * */
    Tuple process(JSONObject params, Staff user);
}
