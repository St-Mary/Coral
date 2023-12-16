package com.stmarygate.common.network.packets;

import com.stmarygate.common.network.PacketHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class Packet {

    public void decode(PacketBuffer packet) {}
    public void encode(PacketBuffer packet) throws RuntimeException {
        packet.finish();
    }

    public void handle(PacketHandler handler) {}

    public enum PacketAction {
        READ, WRITE
    }
}
