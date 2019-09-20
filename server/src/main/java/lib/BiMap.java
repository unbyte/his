package lib;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 双向映射表
 *
 * @param <KEY>   key类型
 * @param <VALUE> value类型
 */
public class BiMap<KEY, VALUE> {
    private final ConcurrentMap<KEY, VALUE> mapKeyToValue = new ConcurrentHashMap<>();
    private final ConcurrentMap<VALUE, KEY> mapValueToKey = new ConcurrentHashMap<>();


    /**
     * 清除表中数据
     */
    public void clear() {
        mapKeyToValue.clear();
        mapValueToKey.clear();
    }

    /**
     * 向映射表中添加一对映射关系，需保证键值都具有唯一性
     *
     * @param key   key
     * @param value value
     */
    public void put(KEY key, VALUE value) {
        if (mapKeyToValue.containsKey(key) || mapValueToKey.containsKey(value)) return;
        mapKeyToValue.put(key, value);
        mapValueToKey.put(value, key);
    }

    /**
     * 获取value
     *
     * @param key key
     * @return value
     */
    public VALUE getValue(KEY key) {
        return mapKeyToValue.get(key);
    }

    /**
     * 获取key.
     *
     * @param value value
     * @return key
     */
    public KEY getKey(VALUE value) {
        return mapValueToKey.get(value);
    }

    /**
     * 查看是否包含key的映射关系
     *
     * @param key key
     * @return 是否包含key的映射
     */
    public boolean containsKey(KEY key) {
        return mapKeyToValue.containsKey(key);
    }

    /**
     * 查看是否包含value的映射关系
     *
     * @param value value
     * @return 是否包含value的映射
     */
    public boolean containsValue(VALUE value) {
        return mapKeyToValue.containsValue(value);
    }

    /**
     * 查看映射表大小
     *
     * @return 大小
     */
    public int size() {
        return mapKeyToValue.size();
    }

    /**
     * 查看映射表是否为空
     *
     * @return 映射表是否为空
     */
    public boolean isEmpty() {
        return mapKeyToValue.isEmpty();
    }

    /**
     * 通过指定key移除映射关系
     *
     * @param key key
     * @throws UnsupportedOperationException the unsupported operation exception
     */
    public void removeKey(KEY key) throws UnsupportedOperationException {
        mapValueToKey.remove(mapKeyToValue.get(key));
        mapKeyToValue.remove(key);
    }

    /**
     * 通过指定value移除映射关系
     *
     * @param value value
     * @throws UnsupportedOperationException the unsupported operation exception
     */
    public void removeValue(VALUE value) throws UnsupportedOperationException {
        mapKeyToValue.remove(mapValueToKey.get(value));
        mapValueToKey.remove(value);
    }

}
