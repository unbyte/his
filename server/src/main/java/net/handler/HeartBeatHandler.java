package net.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.message.Header;
import net.message.Message;
import net.message.MessageType;

// 客户端建立socket链接 -> 发送CONNECT_REQ请求 -> 服务端响应CONNECT_RES -> 客户端处理并开始发送心跳请求
// 客户端拦截器数据进入顺序 编解码器 -> 心跳处理 -> 业务处理
// 服务端拦截器数据进入顺序 编解码器 -> 心跳处理 -> 业务处理 -> 响应连接处理
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message) msg;

        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.type())
            ctx.writeAndFlush(buildHeartBeat());
        else
            ctx.fireChannelRead(msg);
    }


    private Message buildHeartBeat() {
        return new Message().setHeader(
                new Header().setType(
                        MessageType.HEARTBEAT_RES.type()
                )
        );
    }
}
