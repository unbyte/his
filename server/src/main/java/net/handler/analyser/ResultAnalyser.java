package net.handler.analyser;

import io.netty.channel.ChannelHandlerContext;
import lib.Tuple;

/**
 * 用于对result进行处理
 */
@FunctionalInterface
public interface ResultAnalyser {
    void process(ChannelHandlerContext ctx, Tuple result);
}
