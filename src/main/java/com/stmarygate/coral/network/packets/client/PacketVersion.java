package com.stmarygate.coral.network.packets.client;

import com.stmarygate.coral.network.packets.Packet;
import com.stmarygate.coral.network.packets.PacketBuffer;
import com.stmarygate.coral.network.packets.PacketHandler;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a version packet in the network communication. Extends the base {@link Packet} class.
 */
@Getter
@Setter
public class PacketVersion extends Packet {
  /** The major version number. */
  private int major;

  /** The minor version number. */
  private int minor;

  /** The patch version number. */
  private int patch;

  /** The build version string. */
  private String buildVersion;

  /**
   * Constructs a new {@code PacketVersion} with specified version information.
   *
   * @param major The major version number.
   * @param minor The minor version number.
   * @param patch The patch version number.
   * @param buildVersion The build version string.
   */
  public PacketVersion(int major, int minor, int patch, String buildVersion) {
    this.major = major;
    this.minor = minor;
    this.patch = patch;
    this.buildVersion = buildVersion;
  }

  /**
   * Constructs a default {@code PacketVersion} with initial version information. Defaults to
   * version 0.0.1-SNAPSHOT.
   */
  public PacketVersion() {
    this(0, 0, 1, "SNAPSHOT");
  }

  /**
   * Decodes the packet data from the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} containing the packet data.
   */
  @Override
  public void decode(PacketBuffer packet) throws Exception {
    this.major = packet.readByte();
    this.minor = packet.readByte();
    this.patch = packet.readByte();
    this.buildVersion = packet.readString();
  }

  /**
   * Encodes the packet data into the provided {@link PacketBuffer}.
   *
   * @param packet The {@link PacketBuffer} to which the packet data will be written.
   */
  @Override
  public void encode(PacketBuffer packet) throws Exception {
    packet.writeByte(major);
    packet.writeByte(minor);
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
   * Returns a string representation of the {@code PacketVersion}.
   *
   * @return A string representation containing version information.
   */
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
