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

  /**
   * Handles the given packet by invoking the appropriate handler method.
   *
   * @param packet The packet to be handled.
   * @throws RuntimeException If the packet is not handled or an error occurs during handling.
   */
  public void handlePacket(Packet packet) {
    try {
      Method method = findHandlerMethod(packet);
      method.invoke(this, packet);
    } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
      throw new RuntimeException("Error handling packet: " + packet.getClass().getSimpleName(), e);
    }
  }

  /**
   * Retrieves the client session associated with this packet handler's channel.
   *
   * @return The client session.
   */
  public ClientSession getSession() {
    return this.channel.getSession();
  }

  /**
   * Finds the handler method for the given packet class.
   *
   * @param packet The packet for which to find the handler method.
   * @return The handler method.
   * @throws NoSuchMethodException If no handler method is found.
   */
  private Method findHandlerMethod(Packet packet) throws NoSuchMethodException {
    Class<?> packetClass = packet.getClass();
    String handlerMethodName = "handle" + packetClass.getSimpleName();
    return this.getClass().getMethod(handlerMethodName, packetClass);
  }
}
