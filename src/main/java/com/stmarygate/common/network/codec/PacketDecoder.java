package com.stmarygate.common.network.codec;

import com.stmarygate.common.network.packets.Packet;
import com.stmarygate.common.network.packets.PacketBuffer;
import com.stmarygate.common.network.packets.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {
    private static final int HEADER_SIZE = 4;
    private ByteBuf buffer;

    public PacketDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        if(this.buffer == null || this.buffer.readableBytes() == 0) {
            this.buffer = msg.duplicate().retain();
        } else {
            this.buffer = Unpooled.copiedBuffer(this.buffer, msg).retain();
        }

        while(this.buffer.readableBytes() > HEADER_SIZE) {
            int id = this.buffer.readShort();
            int size = this.buffer.readShort();

            if(size > this.buffer.readableBytes()) {
                // Not enough data to read the packet, wait for more data
                return;
            }

            ByteBuf slice = this.buffer.readSlice(size);

            Packet packet = Protocol.getInstance().getPacket(id);
            packet.decode(new PacketBuffer(slice, id, Packet.PacketAction.READ));
            out.add(packet);
        }
    }
}
