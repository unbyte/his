package net;

import controller.PushHandlerCenter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.handler.BusinessHandler;
import net.handler.HeartBeatHandler;
import net.handler.MessageDecoder;
import net.handler.MessageEncoder;
import net.message.Header;
import net.message.Message;
import net.message.MessageType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public enum ClientNetCenter {
    INSTANCE;

    private EventLoopGroup group;
    private Channel channel;

    private CountDownLatch countDownLatch;
    private Message tempResponse;

    public void start(String address, int port) {
        if (channel != null)
            return;
        group = new NioEventLoopGroup();
        Thread client = new Thread(() -> {
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group).channel(NioSocketChannel.class)
                        .handler(
// 客户端拦截器数据进入顺序 编解码器 -> 心跳处理 -> 业务处理
// 服务端拦截器数据进入顺序 编解码器 -> 心跳处理 -> 业务处理 -> 响应连接处理
                                new ChannelInitializer<SocketChannel>() {
                                    @Override
                                    protected void initChannel(SocketChannel ch) {
                                        ch.pipeline().addLast("decoder", new MessageDecoder(1024 * 1024));
                                        ch.pipeline().addLast("encoder", new MessageEncoder());
                                        ch.pipeline().addLast("readTimeOutHandler", new ReadTimeoutHandler(50));
                                        ch.pipeline().addLast("heartBeatHandler", new HeartBeatHandler());
                                        ch.pipeline().addLast("businessHandler", new BusinessHandler());
                                    }
                                });
                channel = bootstrap.connect(address, port).sync().channel();
                countDownLatch.countDown();
                channel.closeFuture().sync();
            } catch (InterruptedException ignored) {
            } finally {
                group.shutdownGracefully();
                channel = null;
            }
        });
        // 初始化一个倒数器
        countDownLatch = new CountDownLatch(1);

        // 启动线程
        client.start();

        // 阻塞住主线程直到client线程里channel建立成功
        try {
            countDownLatch.await();
        } catch (InterruptedException ignored) {
        }

        // 用完了，赋空
        countDownLatch = null;
    }

    public void stop() {
        if (channel == null)
            return;
        channel.close();
    }


    public Message send(Object msg) {
        // 新实例化一个倒数器
        countDownLatch = new CountDownLatch(1);

        // 请求不能为空
        if (channel == null)
            return null;

        channel.writeAndFlush(msg);


        // 阻塞，等待响应
        try {
            countDownLatch.await(6, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
        }


        // 获取到响应
        Message response = tempResponse;

        // 删除倒数器和临时响应
        countDownLatch = null;
        tempResponse = null;

        // 超时了没有收到消息会返回一个超时提示
        return response == null ? new Message().setHeader(new Header().setStatus((byte) 10)).setBody("连接超时") : response;
    }

    public void receive(Message message) {
        // 若倒数器为空，则没有前序请求，是服务端主动推送，直接转发给push handler
        if (countDownLatch == null || message.getHeader().getType() == MessageType.PUSH) {
            PushHandlerCenter.handle(message);
            return;
        }

        // 将返回的message存成临时响应供返回
        tempResponse = message;

        // 通知已经取到响应
        countDownLatch.countDown();
    }
}
