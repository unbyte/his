package lib;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 日志工具类
 */
public class LogUtils {
    private static Logger logger = LogManager.getRootLogger();

    /**
     * 输出Info日志。日志中的类标识为指定类的名称。
     *
     * @param message 日志消息
     * @param clazz   指定的类
     */
    public static <T> void info(T message, Class clazz) {
        logger.info(message + " [" + clazz.getSimpleName() + "]");
    }

    /**
     * 输出Info日志。使用当前类的名称作为日志中的类标识。
     *
     * @param message 日志消息
     */
    public static <T> void info(T message) {
        logger.info(message + " [" + (new Throwable()).getStackTrace()[1].getClassName() + "]");
    }

    /**
     * 输出Warn日志。日志中的类标识为指定类的名称。
     *
     * @param message 日志消息
     * @param clazz   指定的类
     */
    public static <T> void warn(T message, Class clazz) {
        logger.warn(message + " [" + clazz.getSimpleName() + "]");
    }

    /**
     * 输出Warn日志。使用当前类的名称作为日志中的类标识。
     *
     * @param message 日志消息
     */
    public static <T> void warn(T message) {
        logger.warn(message + " [" + (new Throwable()).getStackTrace()[1].getClassName() + "]");
    }

    /**
     * 输出Error日志。日志中的类标识为指定类的名称。
     *
     * @param message 日志消息
     * @param clazz   指定的类
     */
    public static <T> void error(T message, Class clazz) {
        logger.error(message + " [" + clazz.getSimpleName() + "]");
    }

    /**
     * 输出Error日志。使用当前类的名称作为日志中的类标识。
     *
     * @param message 日志消息
     */
    public static <T> void error(T message) {
        logger.error(message + " [" + (new Throwable()).getStackTrace()[1].getClassName() + "]");
    }

    /**
     * 输出Fatal日志。日志中的类标识为指定类的名称。
     *
     * @param message 日志消息
     * @param clazz   指定的类
     */
    public static <T> void fatal(T message, Class clazz) {
        logger.fatal(message + " [" + clazz.getSimpleName() + "]");
    }

    /**
     * 输出Fatal日志。使用当前类的名称作为日志中的类标识。
     *
     * @param message 日志消息
     */
    public static <T> void fatal(T message) {
        logger.fatal(message + " [" + (new Throwable()).getStackTrace()[1].getClassName() + "]");
    }

}
