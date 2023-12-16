package com.stmarygate.common.network.codec;

import com.stmarygate.common.network.packets.Packet;
import com.stmarygate.common.network.packets.PacketBuffer;
import com.stmarygate.common.network.packets.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * The {@code PacketEncoder} class is responsible for encoding instances of the {@link Packet} class
 * into raw bytes to be sent over the network. It extends {@link MessageToByteEncoder} to facilitate
 * integration with Netty's encoding pipeline.
 *
 * <p>This encoder works by calling the {@link Packet#encode(PacketBuffer)} method on the provided
 * {@link Packet} instance, creating a {@link PacketBuffer} to write the encoded data into the specified
 * {@link ByteBuf} (output buffer). The encoded packet is then flushed to the network channel for transmission.
 *
 * <p>The encoding process involves creating a {@link PacketBuffer} initialized with the output buffer,
 * the packet's ID, and the action type (in this case, {@link Packet.PacketAction#WRITE}). The packet's
 * encode method writes the packet's data into the buffer. The resulting byte stream is then flushed
 * to the network channel.
 *
 * <p><strong>Usage:</strong>
 * To use this encoder, add an instance of {@code PacketEncoder} to the Netty pipeline in your Netty
 * server or client bootstrap configuration.
 *
 * <pre>{@code
 * ChannelPipeline pipeline = ch.pipeline();
 * pipeline.addLast("encoder", new PacketEncoder());
 * // ... add other handlers to the pipeline
 * }</pre>
 *
 * <p><strong>Example:</strong>
 * Suppose you have a custom packet class named {@code MyPacket} that extends {@link Packet}. When you send
 * an instance of {@code MyPacket} over the network, the {@code PacketEncoder} will handle the encoding
 * process automatically.
 *
 * <pre>{@code
 * MyPacket myPacket = new MyPacket(); // create an instance of your custom packet
 * channel.writeAndFlush(myPacket);   // send the packet over the network
 * }</pre>
 *
 * @see Packet
 * @see PacketBuffer
 * @see Protocol
 * @see MessageToByteEncoder
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

  /**
   * Constructs a new {@code PacketEncoder} instance. This constructor sets the target class
   * for encoding to {@link Packet}.
   */
  public PacketEncoder() {
    super(Packet.class);
  }

  /**
   * Encodes the specified {@link Packet} into raw bytes and writes them into the output buffer.
   *
   * @param ctx The channel handler context.
   * @param msg The {@link Packet} to be encoded.
   * @param out The output buffer to write the encoded bytes into.
   * @throws RuntimeException If an exception occurs during the encoding process.
   * @see Packet#encode(PacketBuffer)
   */
  @Override
  protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) {
    try {
      msg.encode(new PacketBuffer(out, Protocol.getInstance().getPacketId(msg), Packet.PacketAction.WRITE));
      ctx.channel().flush();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}

