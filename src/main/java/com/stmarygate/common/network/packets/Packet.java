package com.stmarygate.common.network.packets;

import com.stmarygate.common.network.PacketHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class Packet {
    public static final int HEADER_SIZE = 4;

    public void decode(PacketBuffer packet) {}
    public void encode(PacketBuffer packet) throws RuntimeException {
        packet.finish();
    }

    public void handle(PacketHandler handler) {}

    public enum PacketAction {
        READ, WRITE
    }
}
