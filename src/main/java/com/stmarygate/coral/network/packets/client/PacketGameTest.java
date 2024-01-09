package com.stmarygate.coral.network.packets.client;

import com.stmarygate.coral.network.packets.Packet;
import com.stmarygate.coral.network.packets.PacketBuffer;
import com.stmarygate.coral.network.packets.PacketHandler;
import lombok.Getter;

/** A {@link Packet} sent by the client to the server containing the result of the game test. */
@Getter
public class PacketGameTest extends Packet {

  private int result;

  /**
   * Constructs a new {@code PacketGameTest} with specified version information.
   *
   * @param result The result of the test.
   */
  public PacketGameTest(int result) {
    this.result = result;
  }

  /**
   * Constructs a default {@code PacketGameTest} with initial version information. Defaults to 0.
   */
  public PacketGameTest() {
    this(0);
  }

  /**
   * Decodes the packet data from the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} containing the packet data.
   */
  @Override
  public void decode(PacketBuffer packet) throws Exception {
    this.result = packet.readByte();
  }

  /**
   * Encodes the packet data into the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} to which the packet data will be written.
   */
  @Override
  public void encode(PacketBuffer packet) throws Exception {
    packet.writeByte(result);
    packet.finish();
  }

  /**
   * Handles the packet using the specified {@link PacketHandler}.
   *
   * @param handler The {@link PacketHandler} responsible for handling the packet.
   */
  @Override
  public void handle(PacketHandler handler) throws Exception {
    handler.handlePacket(this);
  }

  /**
   * Returns a string representation of the {@code PacketGameTest}.
   *
   * @return A string representation containing result information.
   */
  @Override
  public String toString() {
    return "{ result: " + result + " }";
  }
}
