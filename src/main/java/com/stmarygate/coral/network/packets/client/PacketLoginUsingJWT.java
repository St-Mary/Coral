package com.stmarygate.coral.network.packets.client;

import com.stmarygate.coral.network.packets.Packet;
import com.stmarygate.coral.network.packets.PacketBuffer;
import com.stmarygate.coral.network.packets.PacketHandler;
import lombok.Getter;

/** A {@link Packet} sent by the client to the server containing the JWT token for the user. */
@Getter
public class PacketLoginUsingJWT extends Packet {

  private String jwt;

  /**
   * Constructs a new {@code PacketLoginUsingJWT} with the specified JWT token.
   *
   * @param jwt The JWT token for the user.
   */
  public PacketLoginUsingJWT(String jwt) {
    if (jwt.isEmpty()) return;
    this.jwt = jwt;
  }

  /** Constructs a default {@code PacketLoginUsingJWT} with an empty JWT token. */
  public PacketLoginUsingJWT() {
    this("");
  }

  /**
   * Encodes the packet data into the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} to which the packet data will be written.
   * @throws Exception If an error occurs while encoding the packet data.
   */
  @Override
  public void encode(PacketBuffer packet) throws Exception {
    packet.writeBigString(this.jwt);
    packet.finish();
  }

  /**
   * Decodes the packet data from the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} containing the packet data.
   * @throws Exception If an error occurs while decoding the packet data.
   */
  @Override
  public void decode(PacketBuffer packet) throws Exception {
    this.jwt = packet.readBigString();
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
   * Returns a string representation of this packet.
   *
   * @return the string.
   */
  @Override
  public String toString() {
    return "{" + "jwt='" + jwt + "'}";
  }
}
