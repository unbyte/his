package net.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lib.FileUtils;
import lib.MessageUtils;
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

        if (message.getHeader() == null)
            return;

        if (message.getHeader().getType() == MessageType.RESPONSE)
            ctx.fireChannelRead(msg);

        System.out.println(message.getHeader().getType());
        if (message.getHeader().getType() == MessageType.CONNECT_RES) {
            // 对CONNECT_RES初步判断是否成功，若成功则开启心跳包处理
            if (message.getHeader().getStatus() == MessageUtils.SUCCESS)
                heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatHandler.Task(ctx), 0, 5, TimeUnit.SECONDS);
            ctx.fireChannelRead(msg);
        }

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
            return new Message().setHeader(new Header().setType(MessageType.HEARTBEAT_REQ));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (heartBeat != null) {
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }
}
