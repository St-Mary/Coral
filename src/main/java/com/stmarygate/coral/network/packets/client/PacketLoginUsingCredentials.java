package com.stmarygate.coral.network.packets.client;

import com.stmarygate.coral.network.PacketHandler;
import com.stmarygate.coral.network.packets.Packet;
import com.stmarygate.coral.network.packets.PacketBuffer;
import lombok.Getter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Getter
public class PacketLoginUsingCredentials extends Packet {
  private String username;
  private String password;
  private String encodedPassword;

  public PacketLoginUsingCredentials(String username, String password) {
    if (username.isEmpty() && password.isEmpty()) return;
    this.username = username;

    Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(
            16,     // saltLength
            64,     // hashLength
            8,      // parallelism (e.g., number of CPU cores)
            65536,  // memory in kilobytes
            4       // iterations
    );

    this.password = password;
    this.encodedPassword = argon2PasswordEncoder.encode(password);
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
