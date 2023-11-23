package com.stmarygate.common.network;

import com.stmarygate.common.network.packets.Packet;
import io.netty.channel.Channel;

public class ClientSession {
    private final Channel channel;

    public ClientSession(Channel channel) {
        this.channel = channel;
    }

    public void write(Packet packet) {
        this.channel.writeAndFlush(packet);
    }

    public void close() {
        this.channel.close();
    }
}
