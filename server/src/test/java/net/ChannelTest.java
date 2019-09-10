package net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import net.handler.MessageDecoder;
import org.junit.Assert;
import org.junit.Test;

public class ChannelTest {
    @Test
    public void testDecode() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt((byte) 0x2817767d);
        byteBuf.writeInt(0);
        byteBuf.writeByte((byte) 1);
        byteBuf.writeBytes("{test}".getBytes());
        byteBuf.setInt(4, byteBuf.readableBytes() - 8);

        ByteBuf send = byteBuf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new MessageDecoder(1024 * 1024));

        // 测试读取帧是否正常
        Assert.assertFalse(channel.writeInbound(send.readBytes(6)));
        Assert.assertFalse(channel.writeInbound(send.readBytes(6)));
        Assert.assertTrue(channel.writeInbound(send.readBytes(send.readableBytes())));

        Assert.assertTrue(channel.finish());

        send.release();
    }
}
