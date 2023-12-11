package com.stmarygate.common.network.packets;

import com.stmarygate.common.network.PacketHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Packet {
    @Getter
    private PacketType packetType;

    public void decode(PacketBuffer packet) {}
    public void encode(PacketBuffer packet) {
        packet.finish();
    }

    public void handle(PacketHandler handler) {}

    public enum PacketType {
        CLIENT_MSG, SERVER_MSG
    }
}
