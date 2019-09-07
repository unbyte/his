package net;

import io.netty.channel.Channel;
import lib.BiMap;
import model.Staff;

public class ChannelPool {
    private static BiMap<Channel, Staff> channelPool = new BiMap<>();

    public static void add(Channel channel,Staff staff){
        channelPool.put(channel,staff);
    }

    public static Staff getStaff(Channel channel){
        return channelPool.getValue(channel);
    }

    public static Channel getChannel(Staff staff){
        return channelPool.getKey(staff);
    }

    public static void removeStaff(Staff staff){
        channelPool.removeValue(staff);
    }

    public static void removeChannel(Channel channel){
        channelPool.removeKey(channel);
    }
}
