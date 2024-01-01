package com.stmarygate.coral.network.packets.client;

import com.stmarygate.coral.network.packets.Packet;
import com.stmarygate.coral.network.packets.PacketBuffer;
import com.stmarygate.coral.network.packets.PacketHandler;
import lombok.Getter;

@Getter
public class PacketLoginUsingJWT extends Packet {
  private String jwt;

  public PacketLoginUsingJWT(String jwt) {
    if (jwt.isEmpty()) return;
    this.jwt = jwt;
  }

  public PacketLoginUsingJWT() {
    this("");
  }

  @Override
  public void encode(PacketBuffer packet) throws Exception {
    packet.writeBigString(this.jwt);
    packet.finish();
  }

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

  @Override
  public String toString() {
    return "{" + "jwt='" + jwt + "'}";
  }
}
