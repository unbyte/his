import database.Database;
import net.NetCenter;

public class Main {
    public static void main(String[] args) {
        // 启动数据库服务
        Database.INSTANCE.boot();
        // 启动netty服务
        int port = 30000;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception ignored) {
            }
        }
        NetCenter.INSTANCE.start(port);

        // 注册程序关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(Main::shutdown));

        // 需要通过kill -15关闭程序
    }

    private static void shutdown() {
        // 关闭netty服务
        NetCenter.INSTANCE.stop();

        // 关闭数据库
        Database.INSTANCE.shut();
    }
}
