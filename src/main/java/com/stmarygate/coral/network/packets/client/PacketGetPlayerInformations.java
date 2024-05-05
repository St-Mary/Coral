package com.stmarygate.coral.network.packets.client;

import com.stmarygate.coral.network.packets.Packet;
import com.stmarygate.coral.network.packets.PacketBuffer;
import com.stmarygate.coral.network.packets.PacketHandler;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacketGetPlayerInformations extends Packet {

  /** Constructs a new {@code PacketGetPlayerInformations}. */
  private Long id;

  public PacketGetPlayerInformations(Long id) {
    this.id = id;
  }

  public PacketGetPlayerInformations() {
    this(0L);
  }

  /**
   * Encodes the packet data into the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} to which the packet data will be written.
   */
  @Override
  public void encode(PacketBuffer packet) throws Exception {
    packet.writeLong(this.id);
    packet.finish();
  }

  /**
   * Decodes the packet data from the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} containing the packet data.
   */
  @Override
  public void decode(PacketBuffer packet) throws Exception {
    this.id = packet.readLong();
  }

  /**
   * Handles the packet using the specified {@link PacketHandler}.
   *
   * @param handler The {@link PacketHandler} responsible for handling the packet.
   */
  @Override
  public void handle(PacketHandler handler) throws Exception {
    try {
      handler.handlePacket(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns a string representation of the {@code PacketVersion}.
   *
   * @return A string representation containing version information.
   */
  @Override
  public String toString() {
    return "{ id: " + id + " }";
  }
}
