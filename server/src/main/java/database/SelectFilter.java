package database;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface SelectFilter<K, T> {
    /**
     * 接收到数据库中的原始hash map与存放结果的List，
     *
     * @param data   数据库中的原始表单
     * @param result 存放结果的集合
     */
    void filter(Map<K, T> data, List<T> result);
}
