package bridge;


import controller.RequestHandlerCenter;

/**
 * 使得js能通过netty发送请求、接收响应
 */
public class Request implements Bridge {
    /**
     * 发送一个请求，并返回响应结果<br/>
     *
     * @param methodName 请求调用服务的方法名
     * @param request 请求全文
     * @return 响应结果
     */
    public String send(String methodName, String request) {
        return RequestHandlerCenter.handle(methodName,request);
    }
}
