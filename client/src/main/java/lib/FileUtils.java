package lib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

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
                if(!file.getParentFile().mkdirs())
                    return false;
            try {
                file.createNewFile();
            } catch (IOException e) {
                return false;
            }
        } else if (!file.isFile()) {
            return false;
        } else return file.canWrite() && file.canRead();
        return true;
    }

    /**
     * 读取一个文件中的所有内容，若读取失败则返回空字符串
     *
     * @param path           path对象
     * @param defaultContent 若文件内容为空或读取出错，返回的默认值
     * @return path对象所指向的文件中的内容 string
     */
    public static String readStringFromFile(Path path, String defaultContent) {
        if (!ensureFile(path))
            return defaultContent;
        try {
            return Files.readString(path);
        } catch (IOException ignored) {
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
                Files.writeString(path, content);
            else
                Files.writeString(path, content, StandardOpenOption.APPEND);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
