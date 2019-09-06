package lib;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.HashMap;

public class JSONUtils {
    public static <K, V> HashMap<K, V> toHashMap(String string, Class<K> keyType, Class<V> valueType) {
        return JSON.parseObject(string, new TypeReference<HashMap<K, V>>(keyType,valueType) {
        });
    }
}
