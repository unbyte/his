package lib;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

/**
 * 文件操作工具类
 */
public class FileUtils {
    /**
     * 验证path对象所指向的文件
     *
     * @param path path对象
     * @return 是否可用于读写 boolean
     */
    private static boolean ensureFile(Path path) {
        if (path == null)
            return false;
        File file = path.toFile();
        if (!file.exists()) {
            if (file.getParentFile() != null && !file.getParentFile().exists())
                if (!file.getParentFile().mkdirs())
                    return false;
            try {
                if (file.createNewFile()) {
                    LogUtils.info("File `" + file.getAbsolutePath() + "` was created successfully.");
                } else {
                    LogUtils.warn("File `" + file.getAbsolutePath() + "` already exists!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                LogUtils.error("Failed to create file `" + file.getAbsolutePath() + "`");
                return false;
            }
        } else if (!file.isFile()) {
            LogUtils.warn("File `" + file.getAbsolutePath() + "` is not a file!");
            return false;
        } else if (!file.canWrite() || !file.canRead()) {
            LogUtils.warn("No writing or reading access to file `" + file.getAbsolutePath() + "`!");
            return false;
        }
        return true;
    }

    /**
     * 读取一个文件中的所有内容，若读取失败则返回空字符串
     *
     * @param path           path对象
     * @param defaultContent 若文件内容为空或读取出错，返回的默认值
     * @return path对象所指向的文件中的内容 string
     */
    public static String readJSONStringFromFile(Path path, String defaultContent) {
        if (!ensureFile(path))
            return defaultContent;
        try {
            String content = Files.readString(path);
            return JSON.isValid(content) ? content : defaultContent;
        } catch (IOException e) {
            LogUtils.warn("IOException happened when read file `" + path.getFileName() + "`");
        }
        return defaultContent;
    }

    /**
     * 将字符串写入到文件中
     *
     * @param path       path对象
     * @param content    待写入的字符串
     * @param appendMode 写入的字符串是否追加在文件末尾
     * @return 写入是否成功 boolean
     */
    public static boolean writeStringToFile(Path path, String content, boolean appendMode) {
        if (!ensureFile(path))
            return false;
        try {
            if (appendMode)
                Files.writeString(path, content, StandardOpenOption.APPEND);
            else
                Files.writeString(path, content);
        } catch (IOException e) {
            LogUtils.warn("IOException happened when write data to file `" + path.getFileName() + "`");
            return false;
        }
        return true;
    }


    /**
     * 从文件中读入一个HashMap类型的对象，若读取失败则返回空HashMap
     *
     * @param <K>       HashMap键的类型
     * @param <V>       HashMap值的类型
     * @param path      Path对象
     * @param keyType   HashMap键的类型
     * @param valueType HashMap值的类型
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> loadHashMapFromFile(Path path, Class<K> keyType, Class<V> valueType) {
        if (!ensureFile(path))
            return new HashMap<>();
        String jsonString = FileUtils.readJSONStringFromFile(path, "{}");
        return JSON.parseObject(jsonString, new TypeReference<>(keyType, valueType) {
        });
    }

    /**
     * 将一个HashMap以json的形式存储到文件中
     *
     * @param path       path对象
     * @param hashMap    将要保存的hashmap对象
     * @param appendMode 是否以追加在文件末尾的形式写入
     * @return 是否写入成功
     */
    public static boolean saveHashMapToFile(Path path, HashMap hashMap, boolean appendMode) {
        if (!ensureFile(path))
            return false;
        return FileUtils.writeStringToFile(path, JSON.toJSONString(hashMap), appendMode);
    }
}
