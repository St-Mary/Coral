package com.stmarygate.coral.network.packets.client;

import com.stmarygate.coral.network.packets.Packet;
import com.stmarygate.coral.network.packets.PacketBuffer;
import com.stmarygate.coral.network.packets.PacketHandler;
import lombok.Getter;

/**
 * A {@link Packet} sent by the client to the server containing the username and password for the
 * user.
 */
@Getter
public class PacketLoginUsingCredentials extends Packet {
  private String username;
  private String password;

  /**
   * Constructs a new {@code PacketLoginUsingCredentials} with the specified username and password.
   *
   * @param username The username for the user.
   * @param password The password for the user.
   */
  public PacketLoginUsingCredentials(String username, String password) {
    if (username.isEmpty() && password.isEmpty()) return;
    this.username = username;
    this.password = password;
  }

  /**
   * Constructs a default {@code PacketLoginUsingCredentials} with an empty username and password.
   */
  public PacketLoginUsingCredentials() {
    this("", "");
  }

  /**
   * Encodes the packet data into the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} to which the packet data will be written.
   * @throws Exception If an error occurs while encoding the packet data.
   */
  @Override
  public void encode(PacketBuffer packet) throws Exception {
    packet.writeBigString(this.username);
    packet.writeBigString(this.password);
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

  /**
   * Returns a string representation of this packet.
   *
   * @return the string.
   */
  @Override
  public String toString() {
    return "{" + "username='" + username + '\'' + ", password='" + password + '\'' + '}';
  }
}
