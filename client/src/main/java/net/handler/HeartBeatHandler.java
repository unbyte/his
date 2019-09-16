package net.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lib.FileUtils;
import net.message.Header;
import net.message.Message;
import net.message.MessageType;

import java.nio.file.Paths;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

// 客户端建立socket链接 -> 发送CONNECT_REQ请求 -> 服务端响应CONNECT_RES -> 客户端处理并开始发送心跳请求
// 客户端拦截器数据进入顺序 编解码器 -> 心跳处理 -> 业务处理
// 服务端拦截器数据进入顺序 编解码器 -> 心跳处理 -> 业务处理 -> 响应连接处理
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message) msg;

        if (message.getHeader() != null && message.getHeader().getType() == MessageType.CONNECT_RES.type())

            heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatHandler.Task(ctx), 0, 5, TimeUnit.SECONDS);
            // todo 加上对CONNECT_RES的初步判断是否成功，若成功则进入下一个拦截器，若失败则断开连接
        else
            ctx.fireChannelRead(msg);
    }

    private class Task implements Runnable {
        private final ChannelHandlerContext ctx;

        private Task(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            ctx.writeAndFlush(buildHeartBeat());
        }

        private Message buildHeartBeat() {
            return new Message().setHeader(new Header().setType(MessageType.HEARTBEAT_REQ.type()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        cause.printStackTrace();
        if (heartBeat != null) {
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }
}
