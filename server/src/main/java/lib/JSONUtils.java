package lib;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.HashMap;

public class JSONUtils {
    /**
     * 将一个Json字符串转换成指定的Hashmap
     *
     * @param string    json字符串
     * @param keyType   key类
     * @param valueType value类
     * @return 指定类型的hashmap
     */
    public static <K, V> HashMap<K, V> toHashMap(String string, Class<K> keyType, Class<V> valueType) {
        return JSON.parseObject(string, new TypeReference<>(keyType, valueType) {
        });
    }
}
