package net.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.ClientNetCenter;
import net.message.Message;


public class BusinessHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ClientNetCenter.INSTANCE.receive((Message) msg);
        ctx.fireChannelRead(msg);
    }
}
