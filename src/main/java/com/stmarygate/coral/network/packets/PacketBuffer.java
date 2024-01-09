package com.stmarygate.coral.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a buffer for handling packet data using Netty's {@link ByteBuf}. It provides methods
 * for reading and writing various data types, as well as converting the buffer to a {@link
 * ByteBuffer}.
 */
@Getter
public class PacketBuffer {
  /** The underlying {@link ByteBuf} used for reading and writing data. */
  @Setter private ByteBuf data;

  /** The packet ID associated with the buffer. */
  private int packetId;

  /** The size of the packet data. */
  private int size;

  /**
   * Constructs a new {@link PacketBuffer} for a given packet type and ID.
   *
   * @param packetType The type of the packet (READ or WRITE).
   * @param id The packet ID.
   */
  public PacketBuffer(Packet.PacketAction packetType, int id) {
    this(Unpooled.buffer(), id, packetType);
  }

  /**
   * Constructs a new {@link PacketBuffer} with a specified {@link ByteBuf}, packet ID, and packet
   * type.
   *
   * @param buffer The underlying {@link ByteBuf}.
   * @param id The packet ID.
   * @param packetType The type of the packet (READ or WRITE).
   */
  public PacketBuffer(ByteBuf buffer, int id, Packet.PacketAction packetType) {
    this.data = buffer;
    this.packetId = id;

    if (packetType == Packet.PacketAction.READ) {
      this.packetId = data.readShort();
      this.size = data.readShort();
    } else {
      data.writeShort(packetId);
      data.writeShort(0);
    }
  }

  /** Finishes the packet by updating the size field in the buffer. */
  public void finish() {
    data.setShort(2, data.writerIndex() - Packet.HEADER_SIZE);
  }

  /**
   * Converts the {@link PacketBuffer} to a {@link ByteBuffer}.
   *
   * @return A {@link ByteBuffer} containing the buffer data.
   */
  public ByteBuffer toByteBuffer() {
    byte[] bytes = readBytes(readableBytes());
    return ByteBuffer.wrap(bytes);
  }

  /**
   * Writes a byte to the underlying {@link ByteBuf}.
   *
   * @param b The byte to write.
   * @return This {@link PacketBuffer} instance for method chaining.
   */
  public PacketBuffer writeByte(int b) {
    data.writeByte(b);
    return this;
  }

  /**
   * Reads a byte from the underlying {@link ByteBuf}.
   *
   * @return The read byte.
   */
  public byte readByte() {
    return data.readByte();
  }

  /**
   * Reads an unsigned byte from the underlying {@link ByteBuf}.
   *
   * @return The read unsigned byte as a short.
   */
  public short readUnsignedByte() {
    return data.readUnsignedByte();
  }

  /**
   * Writes a short to the underlying {@link ByteBuf}.
   *
   * @param s The short to write.
   * @return This {@link PacketBuffer} instance for method chaining.
   */
  public PacketBuffer writeShort(int s) {
    data.writeShort(s);
    return this;
  }

  /**
   * Reads a short from the underlying {@link ByteBuf}.
   *
   * @return The read short.
   */
  public short readShort() {
    return data.readShort();
  }

  /**
   * Reads an unsigned short from the underlying {@link ByteBuf}.
   *
   * @return The read unsigned short as an int.
   */
  public int readUnsignedShort() {
    return data.readUnsignedShort();
  }

  /**
   * Writes an int to the underlying {@link ByteBuf}.
   *
   * @param i The int to write.
   * @return This {@link PacketBuffer} instance for method chaining.
   */
  public PacketBuffer writeInt(int i) {
    data.writeInt(i);
    return this;
  }

  /**
   * Reads an int from the underlying {@link ByteBuf}.
   *
   * @return The read int.
   */
  public int readInt() {
    return data.readInt();
  }

  /**
   * Reads an unsigned int from the underlying {@link ByteBuf}.
   *
   * @return The read unsigned int as a long.
   */
  public long readUnsignedInt() {
    return data.readUnsignedInt();
  }

  /**
   * Writes a long to the underlying {@link ByteBuf}.
   *
   * @param l The long to write.
   * @return This {@link PacketBuffer} instance for method chaining.
   */
  public PacketBuffer writeLong(long l) {
    data.writeLong(l);
    return this;
  }

  /**
   * Reads a long from the underlying {@link ByteBuf}.
   *
   * @return The read long.
   */
  public long readLong() {
    return data.readLong();
  }

  /**
   * Writes a byte array to the underlying {@link ByteBuf}.
   *
   * @param b The byte array to write.
   * @return This {@link PacketBuffer} instance for method chaining.
   */
  public PacketBuffer writeBytes(byte[] b) {
    data.writeBytes(b);
    return this;
  }

  /**
   * Reads a byte array from the underlying {@link ByteBuf}.
   *
   * @param length The length of the byte array to read.
   * @return The read byte array.
   */
  public byte[] readBytes(int length) {
    byte[] bytes = new byte[length];
    data.readBytes(bytes);
    return bytes;
  }

  /**
   * Writes a boolean to the underlying {@link ByteBuf}.
   *
   * @param b The boolean to write.
   * @return This {@link PacketBuffer} instance for method chaining.
   */
  public PacketBuffer writeBoolean(boolean b) {
    data.writeBoolean(b);
    return this;
  }

  /**
   * Reads a boolean from the underlying {@link ByteBuf}.
   *
   * @return The read boolean.
   */
  public boolean readBoolean() {
    return data.readBoolean();
  }

  /**
   * Generates a string representation of the buffer content.
   *
   * @param hex If true, displays bytes in hexadecimal format.
   * @return The string representation of the buffer content.
   */
  public String printBuffer(boolean hex) {
    byte[] buf = new byte[data.writerIndex()];
    StringBuilder sb = new StringBuilder();
    data.getBytes(0, buf);

    sb.append("[");
    for (int i = 0; i < buf.length; i++)
      sb.append(
              hex
                  ? "0x" + Integer.toHexString(buf[i] & 0xFF).toUpperCase()
                  : String.valueOf(buf[i]))
          .append(i == buf.length - 1 ? "" : ", ");
    sb.append("]");
    return sb.toString();
  }

  /**
   * Writes a string to the underlying {@link ByteBuf}.
   *
   * @param s The string to write.
   * @return This {@link PacketBuffer} instance for method chaining.
   */
  public PacketBuffer writeString(String s) {
    data.writeByte(s.length());
    data.writeBytes(s.getBytes(StandardCharsets.UTF_8));
    return this;
  }

  /**
   * Reads a string from the underlying {@link ByteBuf}.
   *
   * @return The read string.
   */
  public String readString() {
    byte[] str = new byte[data.readByte()];
    data.readBytes(str);
    return new String(str, StandardCharsets.UTF_8);
  }

  /**
   * Writes a string to the underlying {@link ByteBuf} with a short length prefix.
   *
   * @param s The string to write.
   * @return This {@link PacketBuffer} instance for method chaining.
   */
  public PacketBuffer writeBigString(String s) {
    data.writeShort(s.length());
    data.writeBytes(s.getBytes(StandardCharsets.UTF_8));
    return this;
  }

  /**
   * Reads a string from the underlying {@link ByteBuf} with a short length prefix.
   *
   * @return The read string.
   */
  public String readBigString() {
    int len = data.readUnsignedShort();
    byte[] str = new byte[len];
    data.readBytes(str);
    return new String(str, StandardCharsets.UTF_8);
  }

  /**
   * Writes a string to the underlying {@link ByteBuf} with an int length prefix.
   *
   * @param s The string to write.
   * @return This {@link PacketBuffer} instance for method chaining.
   */
  public PacketBuffer writeLargeString(String s) {
    data.writeInt(s.length());
    data.writeBytes(s.getBytes(StandardCharsets.UTF_8));
    return this;
  }

  /**
   * Reads a string from the underlying {@link ByteBuf} with an int length prefix.
   *
   * @return The read string.
   */
  public String readLargeString() {
    int len = data.readInt();
    byte[] str = new byte[len];
    data.readBytes(str);
    return new String(str, StandardCharsets.UTF_8);
  }

  /**
   * Gets the number of readable bytes in the buffer.
   *
   * @return The number of readable bytes.
   */
  public int readableBytes() {
    return data.readableBytes();
  }

  /**
   * Gets the number of writable bytes in the buffer.
   *
   * @return The number of writable bytes.
   */
  public int writableBytes() {
    return data.writableBytes();
  }
}
