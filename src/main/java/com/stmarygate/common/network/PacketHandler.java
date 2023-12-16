package com.stmarygate.common.network;

import com.stmarygate.common.network.packets.Packet;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PacketHandler {
  private final BaseChannel channel;

  public void handlePacket(Packet packet) {
    try {
      Method method =
          this.getClass()
              .getMethod("handle" + packet.getClass().getSimpleName(), packet.getClass());
      method.invoke(this, packet);
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
      throw new RuntimeException(
          "Packet " + packet.getClass().getSimpleName() + " is not handled.");
    }
  }

  public ClientSession getSession() {
    return this.channel.getSession();
  }
}
