package net.message;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 消息
 */
@Accessors(chain = true)
public class Message {
    @Getter
    @Setter
    private Header header;
    @Getter
    @Setter
    private String body;


    @Override
    public String toString() {
        return "message [" + header + "]";
    }
}
