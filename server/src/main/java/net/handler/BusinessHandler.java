package net.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import controller.ControllerCenter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lib.MessageUtils;
import lib.Tuple;
import model.Staff;
import net.ChannelPool;
import net.handler.analyser.AnalyserCenter;
import net.handler.analyser.ResultAnalyser;
import net.message.Message;
import net.message.MessageType;


public class BusinessHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.REQUEST.type())
            ctx.writeAndFlush(handleRequest(ctx, message));
        else
            ctx.fireChannelRead(msg);

    }

    private Message handleRequest(ChannelHandlerContext ctx, Message message) {
        JSONObject messageBody = JSON.parseObject(message.getBody());

        // 检查请求格式
        if (!messageBody.containsKey("method") || !messageBody.containsKey("params"))
            return MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求格式错误");


        // 检查请求参数类型
        String methodName;
        JSONObject params;
        try {
            methodName = messageBody.getString("method");
            params = messageBody.getJSONObject("params");
        } catch (ClassCastException e) {
            return MessageUtils.buildResponse(MessageUtils.BAD_REQUEST, "请求参数非法");
        }

        // 防止尚未登陆时发送请求
        Staff staff = ChannelPool.getStaff(ctx.channel());
        if (staff == null)
            return MessageUtils.buildResponse(MessageUtils.NO_PERMISSION, "尚未登陆，权限不足");

        // 校验成功，进入逻辑处理
        Tuple result = ControllerCenter.INSTANCE.forwardTask(methodName, params, staff);

        // todo 通过methodName调用相应的解释器解释result，默认解释器为取出直接取出message返回

        ResultAnalyser analyser = AnalyserCenter.getAnalyser(methodName);
        analyser.process(ctx, result);

        // 获取result中的message对象
        return result.get(0, Message.class);
    }

}
