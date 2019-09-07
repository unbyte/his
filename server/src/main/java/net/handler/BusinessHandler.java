package net.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.message.Message;
import net.message.MessageType;


public class BusinessHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.CONNECT_REQ.type()) {

        }
    }




}
