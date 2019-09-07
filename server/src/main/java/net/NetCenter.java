package net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.handler.HeartBeatHandler;
import net.handler.MessageDecoder;
import net.handler.MessageEncoder;

public enum NetCenter {
    INSTANCE;

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    public void start() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast("decoder", new MessageDecoder(1024 * 1024));
                            ch.pipeline().addLast("encoder", new MessageEncoder());
                            ch.pipeline().addLast("readTimeOutHandler", new ReadTimeoutHandler(50));
                            ch.pipeline().addLast("heartBeatHandler", new HeartBeatHandler());

                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(12345).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException ignored) {
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
