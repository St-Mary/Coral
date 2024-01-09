package com.stmarygate.coral.network.packets.server;

import com.stmarygate.coral.network.packets.Packet;
import com.stmarygate.coral.network.packets.PacketBuffer;
import com.stmarygate.coral.network.packets.PacketHandler;
import lombok.Getter;

/** A {@link Packet} sent by the server to the client containing the login result. */
@Getter
public class PacketLoginResult extends Packet {

  private boolean accepted;

  private int code;

  private String token;

  /**
   * Constructs a new {@code PacketVersionResult} with specified login informations.
   *
   * @param accepted Whether the login is accepted.
   * @param token The JWT token for the user if the login is accepted.
   */
  public PacketLoginResult(boolean accepted, int code, String token) {
    this.accepted = accepted;
    this.code = code;
    this.token = token;
  }

  /** Constructs a default {@code PacketLoginResult} with all version information set to zero. */
  public PacketLoginResult() {
    this(false, 2, "");
  }

  /**
   * Decodes the packet data from the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} containing the packet data.
   */
  @Override
  public void decode(PacketBuffer packet) throws Exception {
    this.accepted = packet.readUnsignedByte() == 1;
    this.code = packet.readUnsignedByte();
    this.token = this.accepted ? packet.readBigString() : "";
  }

  /**
   * Encodes the packet data into the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} to which the packet data will be written.
   */
  @Override
  public void encode(PacketBuffer packet) throws Exception {
    packet.writeByte(accepted ? 1 : 0);
    packet.writeByte(code);
    if (accepted) packet.writeBigString(token);
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
   * Returns a string representation of the {@code PacketLoginResult}.
   *
   * @return A string representation containing version information.
   */
  @Override
  public String toString() {
    return "{ accepted: " + accepted + ", code: " + code + ", token: " + token + " }";
  }
}
