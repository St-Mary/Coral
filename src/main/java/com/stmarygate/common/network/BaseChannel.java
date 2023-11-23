package com.stmarygate.common.network;

import com.stmarygate.common.network.packets.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;

@RequiredArgsConstructor
public class BaseChannel extends SimpleChannelInboundHandler<Packet> {
    @Getter
    @Setter
    private final PacketHandler handler;

    @Getter
    protected ClientSession session;

    public BaseChannel(Class<? extends PacketHandler> clazz) {
        try {
            this.handler = clazz.getDeclaredConstructor(BaseChannel.class).newInstance(this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("PacketHandler " + clazz.getSimpleName() + " does not contain the required constructor.");
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) {
        this.handler.handlePacket(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.session = new ClientSession(ctx.channel());
    }
}
