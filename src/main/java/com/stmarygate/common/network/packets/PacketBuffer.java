package com.stmarygate.common.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;

public class PacketBuffer {
    private ByteBuf data;
    private int packetId;
    private int size;

    public PacketBuffer(Packet.PacketType packetType,  int id) {
        this(Unpooled.buffer(), id, packetType);
    }

    public PacketBuffer(ByteBuf buffer, int id, Packet.PacketType packetType) {
        this.data = buffer;
        this.packetId = id;

        if(packetType == Packet.PacketType.CLIENT_MSG) {
            this.packetId = data.readShort();
            this.size = data.readShort();
        } else {
            data.writeShort(packetId);
            data.writeShort(0);
        }
    }

    public void finish() {
        data.setShort(2, data.writerIndex());
    }

    public ByteBuf getData() {
        return data;
    }

    public void setData(ByteBuf buffer) {
        this.data = buffer;
    }

    public ByteBuffer toByteBuffer() {
        byte[] bytes = readBytes(readableBytes());
        return ByteBuffer.wrap(bytes);
    }

    public int getPacketId() {
        return packetId;
    }

    public PacketBuffer writeByte(int b) {
        data.writeByte(b);
        return this;
    }

    public byte readByte() {
        return data.readByte();
    }

    public short readUnsignedByte() {
        return data.readUnsignedByte();
    }

    public PacketBuffer writeShort(int s) {
        data.writeShort(s);
        return this;
    }

    public short readShort() {
        return data.readShort();
    }

    public int readUnsignedShort() {
        return data.readUnsignedShort();
    }

    public PacketBuffer writeInt(int i) {
        data.writeInt(i);
        return this;
    }

    public int readInt() {
        return data.readInt();
    }

    public long readUnsignedInt() {
        return data.readUnsignedInt();
    }

    public PacketBuffer writeLong(long l) {
        data.writeLong(l);
        return this;
    }

    public long readLong() {
        return data.readLong();
    }

    public PacketBuffer writeBytes(byte[] b) {
        data.writeBytes(b);
        return this;
    }

    public byte[] readBytes(int length) {
        byte[] bytes = new byte[length];
        data.readBytes(bytes);
        return bytes;
    }

    public PacketBuffer writeBoolean(boolean b) {
        data.writeBoolean(b);
        return this;
    }

    public boolean readBoolean() {
        return data.readBoolean();
    }

    public PacketBuffer writeString(String s) {
        data.writeByte(s.length());
        data.writeBytes(s.getBytes());
        return this;
    }

    public String readString() {
        byte[] str = new byte[data.readUnsignedByte()];
        data.readBytes(str);
        return new String(str);
    }

    public PacketBuffer writeBigString(String s) {
        data.writeShort(s.length());
        data.writeBytes(s.getBytes());
        return this;
    }

    public String readBigString() {
        int len = data.readUnsignedShort();
        byte[] str = new byte[len];
        data.readBytes(str);
        return new String(str);
    }

    public PacketBuffer writeLargeString(String s) {
        data.writeInt(s.length());
        data.writeBytes(s.getBytes());
        return this;
    }

    public String readLargeString() {
        int len = data.readInt();
        byte[] str = new byte[len];
        data.readBytes(str);
        return new String(str);
    }

    public int readableBytes() {
        return data.readableBytes();
    }

    public int writableBytes() {
        return data.writableBytes();
    }
}
