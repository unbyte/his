package bridge;


import controller.RequestHandlerCenter;
import net.NetCenter;

/**
 * 使得js能通过netty发送请求、接收响应
 */
public class Request implements Bridge {
    /**
     * 发送一个请求，并返回响应结果
     *
     * @param methodName 请求调用服务的方法名
     * @param params 请求参数
     * @return 响应结果
     */
    public String send(String methodName, String params) {

        return RequestHandlerCenter.handle(methodName,params);
    }
}
