package com.stmarygate.coral.network.packets;

import com.stmarygate.coral.network.BaseChannel;
import com.stmarygate.coral.network.ClientSession;
import com.stmarygate.coral.network.packets.client.PacketLoginUsingCredentials;
import com.stmarygate.coral.network.packets.client.PacketLoginUsingJWT;
import com.stmarygate.coral.network.packets.client.PacketVersion;
import com.stmarygate.coral.network.packets.server.PacketLoginResult;
import com.stmarygate.coral.network.packets.server.PacketVersionResult;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet handler for handling incoming packets of type {@link Packet}. It uses a
 * {@link PacketHandler} to process and handle the business logic associated with received packets.
 */
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
  public void handlePacket(@NotNull Packet packet) throws Exception {
    try {
      Method method = findHandlerMethod(packet);
      method.invoke(this, packet);
    } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
      System.out.println(e.getClass().getSimpleName());
      throw new Exception(
          "Error handling incoming packet "
              + packet.getClass().getSimpleName()
              + "\nError:\n"
              + e.getMessage()
              + "\n"
              + Arrays.toString(e.getStackTrace()));
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
   * Handles the version packet.
   *
   * @param packet The version packet.
   */
  public void handlePacketVersion(@NotNull PacketVersion packet) {}

  /**
   * Handles the version result packet.
   *
   * @param packet The version result packet.
   */
  public void handlePacketVersionResult(@NotNull PacketVersionResult packet) {}

  /**
   * Handles the login packet using credentials.
   *
   * @param packet The login packet using credentials instance.
   */
  public void handlePacketLoginUsingCredentials(@NotNull PacketLoginUsingCredentials packet) {}

  /**
   * Handles the login result packet.
   *
   * @param packet The login result packet.
   */
  public void handlePacketLoginResult(@NotNull PacketLoginResult packet) {}

  /**
   * Handles the login packet using JWT.
   *
   * @param packet The login packet using JWT instance.
   */
  public void handlePacketLoginUsingJWT(@NotNull PacketLoginUsingJWT packet) {}

  /**
   * Finds the handler method for the given packet class.
   *
   * @param packet The packet for which to find the handler method.
   * @return The handler method.
   * @throws NoSuchMethodException If no handler method is found.
   */
  private Method findHandlerMethod(@NotNull Packet packet) throws NoSuchMethodException {
    Class<?> packetClass = packet.getClass();
    String handlerMethodName = "handle" + packetClass.getSimpleName();
    return this.getClass().getMethod(handlerMethodName, packetClass);
  }
}
