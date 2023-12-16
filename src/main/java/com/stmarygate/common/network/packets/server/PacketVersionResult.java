package com.stmarygate.common.network.packets.server;

import com.stmarygate.common.network.PacketHandler;
import com.stmarygate.common.network.packets.Packet;
import com.stmarygate.common.network.packets.PacketBuffer;
import lombok.Getter;

@Getter
public class PacketVersionResult extends Packet {
  private boolean accepted;
  private int major;
  private int minor;
  private int patch;
  private String buildVersion;

  public PacketVersionResult(
      boolean accepted, int major, int minor, int patch, String buildVersion) {
    this.accepted = accepted;
    this.major = major;
    this.minor = minor;
    this.patch = patch;
    this.buildVersion = buildVersion;
  }

  public PacketVersionResult() {
    this(false, 0, 0, 0, "");
  }

  @Override
  public void decode(PacketBuffer packet) {
    this.accepted = packet.readUnsignedByte() == 1;
    this.major = packet.readUnsignedByte();
    this.minor = packet.readUnsignedShort();
    this.patch = packet.readUnsignedByte();
    this.buildVersion = packet.readString();
  }

  @Override
  public void encode(PacketBuffer packet) {
    packet.writeByte(accepted ? 1 : 0);
    packet.writeByte(major);
    packet.writeShort(minor);
    packet.writeByte(patch);
    packet.writeString(buildVersion);
    packet.finish();
  }

  @Override
  public void handle(PacketHandler handler) {
    handler.handlePacket(this);
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  public void setMajor(int major) {
    this.major = major;
  }

  public void setMinor(int minor) {
    this.minor = minor;
  }

  public void setPatch(int patch) {
    this.patch = patch;
  }

  public void setBuildVersion(String buildVersion) {
    this.buildVersion = buildVersion;
  }

  @Override
  public String toString() {
    return "{ accepted: "
        + accepted
        + ", major: "
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
