package com.stmarygate.coral.network.packets;

import com.stmarygate.coral.network.PacketHandler;

/**
 * Represents a generic packet in the network communication.
 */
public class Packet {
  /**
   * The size of the packet header in bytes.
   */
  public static final int HEADER_SIZE = 4;

  /**
   * Decodes the packet data from the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} containing the packet data.
   */
  public void decode(PacketBuffer packet) throws Exception {
    // Implementation specific to each packet type.
  }

  /**
   * Encodes the packet data into the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} to which the packet data will be written.
   * @throws RuntimeException If an error occurs during encoding.
   */
  public void encode(PacketBuffer packet) throws Exception {
    packet.finish();
  }

  /**
   * Handles the packet using the specified {@link PacketHandler}.
   *
   * @param handler The {@link PacketHandler} responsible for handling the packet.
   */
  public void handle(PacketHandler handler) throws Exception {
    // Implementation specific to each packet type.
  }

  /**
   * Enum representing the action type of packet (READ or WRITE).
   */
  public enum PacketAction {
    /**
     * Indicates a packet is being read.
     */
    READ,

    /**
     * Indicates a packet is being written.
     */
    WRITE
  }
}

