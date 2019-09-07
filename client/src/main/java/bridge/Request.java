package bridge;


import net.NetCenter;

/**
 * 使得js能通过netty发送请求、接收响应
 */
class Request implements Bridge {
    /**
     * 发送一个请求，并返回响应结果
     *
     * @param method 请求调用服务的方法名
     * @param params 请求参数
     * @return 响应结果
     */
    public String query(String method, String params) {
        //todo 根据method获得hooks里对应的静态处理类
        //todo 调用静态处理类的before方法
        //todo 根据静态处理类的after方法
        return "";
    }
}
