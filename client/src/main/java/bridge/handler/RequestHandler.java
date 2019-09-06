package bridge.handler;

// todo 改成伪责任链模式

/**
 * 实现该接口的类可以在bridge.Request类发送请求时自动被调用
 */
//todo 设计好数据交换的协议及格式之后再设计
class RequestHandler {
    // todo 一个私有的请求对象

    /**
     * 在请求发送前调用
     */
    void before() {

    }

    /**
     * 请求完成后调用
     */
    void after() {

    }

    /**
     * 发出请求
     */
    void request() {

    }

    public void setRequestContext() {
        // todo 外界通过这个设置请求对象
    }

    public String handler() {
        // todo 返回值应该改成请求对象
        before();
        request();
        after();
        return "";
    }

}
