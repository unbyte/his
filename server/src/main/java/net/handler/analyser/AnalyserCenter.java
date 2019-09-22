package net.handler.analyser;

import lib.LogUtils;
import model.Staff;
import net.ChannelPool;
import net.message.Message;

import java.util.HashMap;

public class AnalyserCenter {
    private static HashMap<String, ResultAnalyser> analysers = new HashMap<>();

    static {
        // todo 在这里注册analyser
        analysers.put("default", ((ctx, result) -> {
        }));
        analysers.put("login", ((ctx, result) -> {
            // 登陆失败
            if (result.size() < 2)
                return;
            // 登陆成功, 加入到链接池
            Staff staff = result.get(1, Staff.class);
            ChannelPool.add(ctx.channel(), staff);
            // 日志输出
            LogUtils.info(staff.getName() + " has connected");
        }));

        analysers.put("front-desk-register-new", ((ctx, result) -> {
            if (result.size() < 2)
                return;
            Staff staff = result.get(2, Staff.class);
            Message message = result.get(1, Message.class);
            // 推送新挂号信息
            if (ChannelPool.containStaff(staff))
                ChannelPool.getChannel(staff).writeAndFlush(message);
        }));

        analysers.put("front-desk-register-exist", ((ctx, result) -> {
            if (result.size() < 2)
                return;
            Staff staff = result.get(2, Staff.class);
            Message message = result.get(1, Message.class);
            LogUtils.info(message.getBody());
            LogUtils.info(staff);

            // 推送新挂号信息
            if (ChannelPool.containStaff(staff))
                ChannelPool.getChannel(staff).writeAndFlush(message);
        }));

        analysers.put("front-desk-cancel", ((ctx, result) -> {
            if (result.size() < 2)
                return;
            Staff staff = result.get(2, Staff.class);
            Message message = result.get(1, Message.class);
            LogUtils.info(message.getBody());
            LogUtils.info(staff);

            if (ChannelPool.containStaff(staff))
                ChannelPool.getChannel(staff).writeAndFlush(message);
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
