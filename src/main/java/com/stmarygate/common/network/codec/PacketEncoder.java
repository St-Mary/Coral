package com.stmarygate.common.network.codec;

import com.stmarygate.common.network.packets.Packet;
import com.stmarygate.common.network.packets.PacketBuffer;
import com.stmarygate.common.network.packets.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
  public PacketEncoder() {
    super(Packet.class);
  }

  @Override
  protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) {
    try {
      msg.encode(
          new PacketBuffer(
              out, Protocol.getInstance().getPacketId(msg), Packet.PacketAction.WRITE));
      ctx.channel().flush();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
