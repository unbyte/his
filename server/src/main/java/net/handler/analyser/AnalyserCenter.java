package net.handler.analyser;

import lib.LogUtils;
import model.Staff;
import net.ChannelPool;

import java.util.HashMap;

public class AnalyserCenter {
    private static HashMap<String, ResultAnalyser> analysers = new HashMap<>();

    static {
        analysers.put("default", ((ctx, result) -> {
        }));
        analysers.put("login",((ctx, result) -> {
            // 登陆失败
            if (result.size() < 2)
                return;
            // 登陆成功, 加入到链接池
            Staff staff = result.get(1, Staff.class);
            ChannelPool.add(ctx.channel(), staff);
            // 日志输出
            LogUtils.info(staff.getName() + " has connected");
        }));
    }

    /**
     * 获取到指定的ResultAnalyser，若不存在则返回默认空操作
     *
     * @param analyserName 应该与methodName同名
     * @return analyser
     */
    public static ResultAnalyser getAnalyser(String analyserName) {
        if (!analysers.containsKey(analyserName))
            return analysers.get("default");
        return analysers.get(analyserName);
    }
}
