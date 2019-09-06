package database;

import com.alibaba.fastjson.JSON;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 数据查询会话
 */
public class SelectSession {
    // todo 为了安全深拷贝所有的结果
    private List result = null;
    private HashMap originData = null;

    /**
     * 筛选操作
     *
     * @param filter 筛选器
     * @return 数据查询会话
     */
    public SelectSession filter(SelectFilter filter) {
        result = filter.filter(originData);
        return this;
    }

    /**
     * 保留指定下标范围内的结果<br/>
     * 范围前闭后开
     *
     * @param from 起始下标
     * @param to   结束下标
     * @return 数据查询会话
     */
    public SelectSession range(Integer from, Integer to) {
        if (from < 0 || to > result.size() || from > to)
            return this;
        result = result.subList(from, to);
        return this;
    }

    /**
     * 获取到结果对象数组
     *
     * @return 对象数组
     */
    public Object[] toArray() {
        return result.toArray();
    }

    /**
     * 获取到结果对象列表
     *
     * @return 对象列表
     */
    public List toList() {
        return result;
    }

    /**
     * 获取到结果Json字符串
     *
     * @return json字符串
     */
    public String toJSONString() {
        if (result.isEmpty())
            return "[]";
        if (result.size() > 1)
            return JSON.toJSONString(result.toArray());
        return JSON.toJSONString(result.get(0));
    }

    /**
     * 获取一个结果
     *
     * @return 对象
     */
    public Object get() {
        if (result.isEmpty())
            return null;
        return result.get(0);
    }

    public SelectSession(HashMap originData) {
        this.originData = originData;
    }

}
