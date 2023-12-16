package com.stmarygate.common.network.packets.client;

import com.stmarygate.common.network.PacketHandler;
import com.stmarygate.common.network.packets.Packet;
import com.stmarygate.common.network.packets.PacketBuffer;
import lombok.Getter;
import lombok.Setter;

public class PacketVersion extends Packet {
    @Getter
    @Setter
    private int major;
    @Getter
    @Setter
    private int minor;
    @Getter
    @Setter
    private int patch;
    @Getter
    @Setter
    private String buildVersion;

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
        System.out.println("Decoding packet version");
        this.major = packet.readByte();
        System.out.println("Decoding packet version2");
        System.out.println("Print current packet buffer : ");
        System.out.println(packet.printBuffer(true));
        this.minor = packet.readByte();
        System.out.println("Decoding packet version3");
        System.out.println("Print current packet buffer : ");
        System.out.println(packet.printBuffer(true));
        this.patch = packet.readByte();
        System.out.println("Decoding packet version4");
        System.out.println("Print current packet buffer : ");
        System.out.println(packet.printBuffer(true));
        this.buildVersion = packet.readString();
        System.out.println("Decoding packet version5");
        System.out.println("Print current packet buffer : ");
        System.out.println(packet.printBuffer(true));
    }

    @Override
    public void encode(PacketBuffer packet) {
        System.out.println("Encoding packet version");
        System.out.println("Print current packet buffer : ");
        System.out.println(packet.printBuffer(true));
        packet.writeByte(major);
        System.out.println("Encoding packet version2");
        System.out.println("Print current packet buffer : ");
        System.out.println(packet.printBuffer(true));
        packet.writeByte(minor);
        System.out.println("Encoding packet version3");
        System.out.println("Print current packet buffer : ");
        System.out.println(packet.printBuffer(true));
        packet.writeByte(patch);
        System.out.println("Encoding packet version4");
        System.out.println("Print current packet buffer : ");
        System.out.println(packet.printBuffer(true));
        packet.writeString(buildVersion);
        System.out.println("Encoding packet version5");
        System.out.println("Print current packet buffer : ");
        System.out.println(packet.printBuffer(true));

        packet.finish();
    }

    @Override
    public void handle(PacketHandler handler) {
        handler.handlePacket(this);
    }

    @Override
    public String toString() {
        return "{ major: " + major + ", minor: " + minor + ", patch: " + patch + ", build: " + buildVersion + " }";
    }
}
