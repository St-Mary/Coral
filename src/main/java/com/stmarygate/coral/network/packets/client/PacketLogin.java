package com.stmarygate.coral.network.packets.client;

import com.stmarygate.coral.network.packets.Packet;
import com.stmarygate.coral.network.packets.PacketBuffer;
import lombok.Getter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Getter
public class PacketLogin extends Packet {
  private String username;
  private String password;
  private String encodedPassword;

  public PacketLogin(String username, String password) {
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

  public PacketLogin() {
    this("", "");
  }

  @Override
  public void encode(PacketBuffer packet) {
    packet.writeBigString(this.username);
    packet.writeBigString(this.password);
    packet.finish();
  }

  @Override
  public void decode(PacketBuffer packet) {
    this.username = packet.readBigString();
    this.password = packet.readBigString();
  }

  @Override
  public String toString() {
    return "{" + "username='" + username + '\'' + ", password='" + password + '\'' + '}';
  }
}
