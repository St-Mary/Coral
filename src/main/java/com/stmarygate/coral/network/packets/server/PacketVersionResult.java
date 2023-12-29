package com.stmarygate.coral.network.packets.server;

import com.stmarygate.coral.network.packets.Packet;
import com.stmarygate.coral.network.packets.PacketBuffer;
import com.stmarygate.coral.network.packets.PacketHandler;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a version result packet in the network communication. Extends the base {@link Packet}
 * class.
 */
@Getter
@Setter
public class PacketVersionResult extends Packet {
  /** Indicates whether the version is accepted. */
  private boolean accepted;

  /** The major version number. */
  private int major;

  /** The minor version number. */
  private int minor;

  /** The patch version number. */
  private int patch;

  /** The build version string. */
  private String buildVersion;

  /**
   * Constructs a new {@code PacketVersionResult} with specified version information.
   *
   * @param accepted Whether the version is accepted.
   * @param major The major version number.
   * @param minor The minor version number.
   * @param patch The patch version number.
   * @param buildVersion The build version string.
   */
  public PacketVersionResult(
      boolean accepted, int major, int minor, int patch, String buildVersion) {
    this.accepted = accepted;
    this.major = major;
    this.minor = minor;
    this.patch = patch;
    this.buildVersion = buildVersion;
  }

  /** Constructs a default {@code PacketVersionResult} with all version information set to zero. */
  public PacketVersionResult() {
    this(false, 0, 0, 0, "");
  }

  /**
   * Decodes the packet data from the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} containing the packet data.
   */
  @Override
  public void decode(PacketBuffer packet) throws Exception {
    this.accepted = packet.readUnsignedByte() == 1;
    this.major = packet.readUnsignedByte();
    this.minor = packet.readUnsignedShort();
    this.patch = packet.readUnsignedByte();
    this.buildVersion = packet.readString();
  }

  /**
   * Encodes the packet data into the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} to which the packet data will be written.
   */
  @Override
  public void encode(PacketBuffer packet) throws Exception {
    packet.writeByte(accepted ? 1 : 0);
    packet.writeByte(major);
    packet.writeShort(minor);
    packet.writeByte(patch);
    packet.writeString(buildVersion);
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
   * Returns a string representation of the {@code PacketVersionResult}.
   *
   * @return A string representation containing version information.
   */
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
