package net.handler;

import controller.PushHandlerCenter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import javafx.application.Platform;
import net.ClientNetCenter;
import net.message.Header;
import net.message.Message;
import net.message.MessageType;


public class BusinessHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ClientNetCenter.INSTANCE.receive((Message) msg);
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //断线相当于接收到一个消息提示已断线的消息包
        ClientNetCenter.INSTANCE.receive(new Message().setHeader(new Header().setType(MessageType.PUSH)).setBody("{\"type\":\"disconnected\",\"msg\":\"\"}"));
    }
}
