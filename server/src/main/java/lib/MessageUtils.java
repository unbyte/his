package lib;

import com.alibaba.fastjson.JSONObject;
import net.message.Header;
import net.message.Message;
import net.message.MessageType;

public class MessageUtils {
    public static Message buildResponse(byte status, JSONObject msg) {
        return buildResponse(status, msg, MessageType.RESPONSE.type());
    }

    public static Message buildResponse(byte status, JSONObject msg, byte type) {
        return new Message().setHeader(
                new Header().setType(type)
        ).setBody(
                new JSONObject().fluentPut("status",status)
                        .fluentPut("msg",msg)
                        .toJSONString()
        );
    }

    public static Message buildHeartBeat() {
        return new Message().setHeader(
                new Header().setType(
                        MessageType.HEARTBEAT_RES.type()
                )
        );
    }


}
