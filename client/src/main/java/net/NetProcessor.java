package net;


/**
 * 发送与接收信息的处理类
 */
public class NetProcessor {
    /**
     * 发送一个请求，返回响应结果.
     *
     * @param method 请求调用服务的方法名
     * @param params 请求参数
     * @return 响应结果
     */
    public static String request(String method, String params) {
        return decode(send(encode(method, params)));
    }



    //todo 这一块还需要重新设计封装
    private static String encode(String method, String params) {
        //todo 包装为数据包
        return "";
    }

    private static String send(String request) {
        //todo 发送包装好的数据包
        return "";
    }

    private static String decode(String result) {
        //todo 解码收到的响应
        return "";
    }

}
