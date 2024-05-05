package com.stmarygate.coral.network.packets.server;

import com.stmarygate.coral.entities.Account;
import com.stmarygate.coral.entities.Player;
import com.stmarygate.coral.network.packets.Packet;
import com.stmarygate.coral.network.packets.PacketBuffer;
import com.stmarygate.coral.network.packets.PacketHandler;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.UUID;

@Getter @Setter
public class PacketGetPlayerInformationsResult extends Packet {

    private Player player;

    public PacketGetPlayerInformationsResult(Player player) {
      if (player == null) return;
      this.player = player;
    }

    public PacketGetPlayerInformationsResult() {
      this(null);
    }

    @Override
    public void encode(PacketBuffer packet) throws Exception {
      if (player == null) return;
      packet.writeBigString(this.player.getUsername());
      packet.writeBigString(this.player.getId().toString());
      packet.writeLong(this.player.getExp());
      packet.writeInt(this.player.getLevel());
      packet.writeLong(this.player.getMana());
      packet.writeInt(this.player.getAura());
      packet.writeInt(this.player.getStrength());
      packet.writeInt(this.player.getDefense());
      packet.writeInt(this.player.getSpeed());
      packet.writeInt(this.player.getHealth());
      packet.writeInt(this.player.getMaxHealth());
      packet.writeLong(this.player.getStamina());
      packet.finish();
    }

    @Override
    public void decode(PacketBuffer packet) throws Exception {
      this.player = new Player();
      this.player.setUsername(packet.readBigString());
      this.player.setId(UUID.fromString(packet.readBigString()));
      this.player.setExp(packet.readLong());
      this.player.setLevel(packet.readInt());
      this.player.setMana(packet.readLong());
      this.player.setAura(packet.readInt());
      this.player.setStrength(packet.readInt());
      this.player.setDefense(packet.readInt());
      this.player.setSpeed(packet.readInt());
      this.player.setHealth(packet.readInt());
      this.player.setMaxHealth(packet.readInt());
      this.player.setStamina(packet.readLong());
    }

    @Override
    public void handle(PacketHandler handler) throws Exception {
      handler.handlePacket(this);
    }

    @Override
    public String toString() {
      return "{ player: " + player + " }";
    }
}
