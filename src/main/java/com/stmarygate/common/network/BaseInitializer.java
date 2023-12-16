package com.stmarygate.common.network;

import com.stmarygate.common.network.codec.PacketDecoder;
import com.stmarygate.common.network.codec.PacketEncoder;
import com.stmarygate.common.network.packets.Packet;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class BaseInitializer extends ChannelInitializer<SocketChannel> {
    private final BaseChannel channel;

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pl = ch.pipeline();

        pl.addLast("decoder", new PacketDecoder());
        pl.addLast("encoder", new PacketEncoder());
        pl.addLast("handler", this.channel);
    }
}
