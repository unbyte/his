package net.message;

/**
 * 消息
 */
public class Message {
    private Header header;
    private String body;

    /**
     * 获取消息头
     *
     * @return 消息头
     */
    public Header getHeader() {
        return header;
    }

    /**
     * 设置消息头
     *
     * @param header 消息头
     * @return 消息
     */
    public Message setHeader(Header header) {
        this.header = header;
        return this;
    }

    /**
     * 获取消息体
     *
     * @return 消息体
     */
    public String getBody() {
        return body;
    }

    /**
     * 设置消息体
     *
     * @param body 消息体
     * @return 消息
     */
    public Message setBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        return "message [" + header + "]";
    }
}
