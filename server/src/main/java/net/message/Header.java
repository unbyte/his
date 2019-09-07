package net.message;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 消息头
 */
@Accessors(chain = true)
public class Header {
    @Getter
    private int crcCode = 0x2817767d;
    @Getter
    @Setter /*消息长度，该长度指由本字段结束位置到消息结束位置的长度，在实例化一个新Message时可以不指定，经由encode时会自动生成实际长度*/
    private int length;
    @Getter
    @Setter /*获取消息类型，需与{@link MessageType}进行比较*/
    private byte type;


    @Override
    public String toString() {
        return "header [ crcCode=" + crcCode + ", length=" + length + ", type=" + type;
    }
}
