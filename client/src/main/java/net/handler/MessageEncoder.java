package net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import net.message.Message;

public class MessageEncoder extends MessageToByteEncoder<Message> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        if (msg == null || msg.getHeader() == null)
            throw new Exception("The encode message is null");
        out.writeInt(msg.getHeader().getCrcCode());
        out.writeInt(msg.getHeader().getLength());
        out.writeByte(msg.getHeader().getType());
        out.writeByte(msg.getHeader().getStatus());
        if (msg.getBody() != null)
            out.writeBytes(msg.getBody().getBytes(CharsetUtil.UTF_8)); // 直接取得字符串的bytes形式
            // 备用      .writeInt(msg.getBody().getBytes().length);
            //           .writeCharSequence(msg.getBody(), CharsetUtil.UTF_8);
        out.setInt(4, out.readableBytes() - 8); // set与get都是通过绝对下标修改ByteBuf内容，这里用来重写最终的帧长度
    }
}
