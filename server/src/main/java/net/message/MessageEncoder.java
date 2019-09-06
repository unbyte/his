package net.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<Message> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {

        if (msg == null || msg.getHeader() == null)
            throw new Exception("The encode message is null");
        ByteBuf sendBuf = Unpooled.buffer();
        sendBuf.writeInt(msg.getHeader().getCrcCode());
        sendBuf.writeInt(msg.getHeader().getLength());
        sendBuf.writeByte(msg.getHeader().getType());
        if (msg.getBody() != null)
            sendBuf.writeBytes(msg.getBody().getBytes()); // 直接取得字符串的bytes形式
            // 备用      .writeInt(msg.getBody().getBytes().length);
            //           .writeCharSequence(msg.getBody(), CharsetUtil.UTF_8);
        sendBuf.setInt(4, sendBuf.readableBytes() - 8); // set与get都是通过绝对下标修改ByteBuf内容，这里用来重写最终的帧长度

    }
}
