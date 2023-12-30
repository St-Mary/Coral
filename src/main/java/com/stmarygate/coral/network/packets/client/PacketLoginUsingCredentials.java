package com.stmarygate.coral.network.packets.client;

import com.stmarygate.coral.network.packets.Packet;
import com.stmarygate.coral.network.packets.PacketBuffer;
import com.stmarygate.coral.network.packets.PacketHandler;
import lombok.Getter;

@Getter
public class PacketLoginUsingCredentials extends Packet {
  private String username;
  private String password;

  public PacketLoginUsingCredentials(String username, String password) {
    if (username.isEmpty() && password.isEmpty()) return;
    this.username = username;
    this.password = password;
  }

  public PacketLoginUsingCredentials() {
    this("", "");
  }

  @Override
  public void encode(PacketBuffer packet) throws Exception {
    packet.writeBigString(this.username);
    packet.writeBigString(this.password);
    packet.finish();
  }

  @Override
  public void decode(PacketBuffer packet) throws Exception {
    this.username = packet.readBigString();
    this.password = packet.readBigString();
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
    return "{" + "username='" + username + '\'' + ", password='" + password + '\'' + '}';
  }
}
