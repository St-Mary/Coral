package com.stmarygate.common.network.packets.client;

import com.stmarygate.common.network.PacketHandler;
import com.stmarygate.common.network.packets.Packet;
import com.stmarygate.common.network.packets.PacketBuffer;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PacketVersion extends Packet {
  @Setter private int major;
  @Setter private int minor;
  @Setter private int patch;
  @Setter private String buildVersion;

  public PacketVersion(int major, int minor, int patch, String buildVersion) {
    this.major = major;
    this.minor = minor;
    this.patch = patch;
    this.buildVersion = buildVersion;
  }

  public PacketVersion() {
    this(0, 0, 1, "SNAPSHOT");
  }

  @Override
  public void decode(PacketBuffer packet) {
    this.major = packet.readByte();
    this.minor = packet.readByte();
    this.patch = packet.readByte();
    this.buildVersion = packet.readString();
  }

  @Override
  public void encode(PacketBuffer packet) {
    packet.writeByte(major);
    packet.writeByte(minor);
    packet.writeByte(patch);
    packet.writeString(buildVersion);
    packet.finish();
  }

  @Override
  public void handle(PacketHandler handler) {
    handler.handlePacket(this);
  }

  @Override
  public String toString() {
    return "{ major: "
        + major
        + ", minor: "
        + minor
        + ", patch: "
        + patch
        + ", build: "
        + buildVersion
        + " }";
  }
}
