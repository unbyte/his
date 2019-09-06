package net.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;

public class MessageDecoder extends LengthFieldBasedFrameDecoder {
    public MessageDecoder(int maxFrameLength) {
        super(maxFrameLength, 4, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) return null;

        Message message = new Message()
                .setHeader(new Header().setCrcCode(frame.readInt())
                        .setLength(frame.readInt())
                        .setType(frame.readByte()));


        if (frame.readableBytes() > 4)
            message.setBody(frame.toString(frame.readerIndex(), frame.readableBytes(), CharsetUtil.UTF_8));
        // 备用 message.setBody(frame.readCharSequence(frame.readInt(), CharsetUtil.UTF_8).toString());

        return message;
    }
}


