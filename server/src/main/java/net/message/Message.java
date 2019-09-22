package net.message;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 消息
 */
@Accessors(chain = true)
@Getter
@Setter
public class Message {
    private Header header;
    private String body;

    @Override
    public String toString() {
        return "message [" + header + "]";
    }
}
