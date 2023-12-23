package com.stmarygate.coral.network.codec;

import com.stmarygate.coral.network.packets.Packet;
import com.stmarygate.coral.network.packets.PacketBuffer;
import com.stmarygate.coral.network.packets.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

/**
 * <p>The {@code PacketDecoder} class is responsible for decoding raw bytes received over the network
 * into instances of the {@link Packet} class. It extends {@link MessageToMessageDecoder} to integrate
 * with Netty's decoding pipeline.
 *
 * <p>This decoder works by maintaining an internal {@link ByteBuf} buffer to accumulate incoming bytes
 * until there is enough data to construct a complete packet. The decoding process involves reading
 * the packet ID and size from the buffer, then verifying if there is sufficient data to construct
 * the entire packet. If enough data is available, the packet is created using the {@link Protocol}
 * and {@link PacketBuffer}, and the decoded packet is added to the output list for further processing.
 *
 * <p>The decoding process is performed in a loop to handle multiple packets in the input buffer, and the
 * remaining bytes are retained in the buffer for subsequent decoding attempts.
 *
 * <p><strong>Example usage:</strong>
 * ```java
 * // Create an instance of PacketDecoder
 * PacketDecoder packetDecoder = new PacketDecoder();
 *
 * <p>// Assume 'inputBytes' is a ByteBuf containing received network data
 * List<Object> outputPackets = new ArrayList<>();
 * packetDecoder.decode(ctx, inputBytes, outputPackets);
 *
 * <p>// Process the decoded packets in 'outputPackets'
 * ```
 *
 * <p>Note: It's crucial to properly release resources in Netty, especially ByteBuf instances, to prevent memory leaks.
 * Ensure that the {@code decode} method is called with sufficient frequency to handle incoming data and release
 * the retained ByteBuf instances appropriately.
 */
public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {
  private ByteBuf buffer;

  /**
   * Constructs a new {@code PacketDecoder}.
   */
  public PacketDecoder() {}

  /**
   * Decodes the incoming raw bytes into instances of the {@link Packet} class.
   *
   * @param ctx the {@link ChannelHandlerContext} for the channel
   * @param msg the incoming ByteBuf containing raw network data
   * @param out the list to which decoded packets will be added
   * @throws Exception if an error occurs during the decoding process
   */
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
    // Duplicate and retain the incoming buffer for manipulation
    if (this.buffer == null || this.buffer.readableBytes() == 0) {
      this.buffer = msg.duplicate().retain();
    } else {
      this.buffer = Unpooled.copiedBuffer(this.buffer, msg).retain();
    }

    // Continue decoding while there is enough data in the buffer
    while (this.buffer.readableBytes() > Packet.HEADER_SIZE) {
      int id = this.buffer.readShort();
      int size = this.buffer.readShort();

      // Check if there is enough data to read the entire packet
      if (size > this.buffer.readableBytes()) {
        // Not enough data to read the packet, wait for more data
        return;
      }

      // Reset the reader index to the beginning of the packet
      this.buffer.resetReaderIndex();

      // Read a slice of the buffer containing the entire packet. The size add the header size because size dosn't include the header size
      ByteBuf slice = this.buffer.readSlice(size + Packet.HEADER_SIZE);

      // Create a new packet instance using the Protocol and PacketBuffer
      Packet packet = Protocol.getInstance().getPacket(id);
      packet.decode(new PacketBuffer(slice, id, Packet.PacketAction.READ));

      // Add the decoded packet to the output list
      out.add(packet);
    }
  }
}

