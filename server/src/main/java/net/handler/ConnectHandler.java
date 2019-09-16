package net.handler;

import controller.ControllerCenter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lib.LogUtils;
import lib.MessageUtils;
import lib.Tuple;
import model.Staff;
import net.ChannelPool;
import net.message.Message;
import net.message.MessageType;

public class ConnectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.CONNECT_REQ)
            ctx.writeAndFlush(handleLogin(ctx, message));
        else
            // 经过三次类型筛选后进入该分支的类型应该不属于任何一个，因此抛出not found
            ctx.writeAndFlush(MessageUtils.buildResponse(MessageUtils.NOT_FOUND, "该请求类型不存在"));
    }

    private Message handleLogin(ChannelHandlerContext ctx, Message message) {
        Tuple res = ControllerCenter.INSTANCE.forwardTask("login", message.getBody(), null);
        // 登陆失败
        if (res.size() < 2)
            return res.get(0, Message.class);
        // 登陆成功
        // 加入到链接池
        Staff staff = res.get(1, Staff.class);
        ChannelPool.add(ctx.channel(), staff);
        // 日志输出
        LogUtils.info(staff.getName() + " was connected");

        return res.get(0, Message.class);
    }
}
