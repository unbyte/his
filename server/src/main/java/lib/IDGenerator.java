package lib;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * ID发号器
 */
public class IDGenerator {

    /**
     * 生成一个唯一id<br/>
     * 按课设标准，简单起见，取精确到毫秒的时间戳作为id
     *
     * @return id
     */
    public static long generate() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

}
