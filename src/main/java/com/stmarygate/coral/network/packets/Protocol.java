package com.stmarygate.coral.network.packets;

import com.stmarygate.coral.network.packets.client.*;
import com.stmarygate.coral.network.packets.server.PacketGetPlayerInformationsResult;
import com.stmarygate.coral.network.packets.server.PacketLoginResult;
import com.stmarygate.coral.network.packets.server.PacketVersionResult;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the protocol for packet communication by registering and retrieving packet classes based
 * on their IDs.
 */
public class Protocol {

  /** Mapping of packet IDs to their corresponding packet classes. */
  private static final HashMap<Integer, Class<? extends Packet>> PACKETS_MAP = new HashMap<>();
  private static final Logger LOGGER = LoggerFactory.getLogger(Protocol.class);

  /** Singleton instance of the Protocol. */
  private static final Protocol INSTANCE = new Protocol();

  /** Private constructor to enforce the singleton pattern and register initial packets. */
  private Protocol() {
    try {
      register(1, PacketVersion.class);
      register(2, PacketVersionResult.class);
      register(3, PacketLoginUsingCredentials.class);
      register(4, PacketLoginUsingJWT.class);
      register(5, PacketLoginResult.class);
      register(6, PacketGameTest.class);
      register(7, PacketGetPlayerInformations.class);
      register(8, PacketGetPlayerInformationsResult.class);
    } catch (InstantiationException e) {
      LOGGER.error("Error while registering packet: " + e.getMessage());
    }
  }

  /**
   * Retrieves the singleton instance of the Protocol.
   *
   * @return The singleton instance of the Protocol.
   */
  public static Protocol getInstance() {
    return INSTANCE;
  }

  /**
   * Registers a packet class with a specific ID.
   *
   * @param id The ID associated with the packet.
   * @param packet The class of the packet to be registered.
   * @throws RuntimeException If the packet class does not contain a default constructor.
   */
  private void register(int id, @NotNull Class<? extends Packet> packet)
      throws InstantiationException {
    try {
      packet.getDeclaredConstructor().newInstance();
      PACKETS_MAP.put(id, packet);
    } catch (InvocationTargetException
        | InstantiationException
        | IllegalAccessException
        | NoSuchMethodException e) {
      throw new InstantiationException(
          "Class " + packet.getSimpleName() + " does not contain a default Constructor!");
    }
  }

  /**
   * Retrieves the ID associated with a given packet.
   *
   * @param packet The packet for which to retrieve the ID.
   * @return The ID associated with the packet.
   * @throws RuntimeException If the packet is not registered.
   */
  public int getPacketId(@NotNull Packet packet) {
    Optional<Integer> id =
        PACKETS_MAP.entrySet().stream()
            .filter(entry -> entry.getValue().isInstance(packet))
            .map(Map.Entry::getKey)
            .findFirst();
    if (id.isPresent()) return id.get();
    throw new MissingResourceException("Packet is not registered.", packet.getClass().getName(),
            packet.toString());
  }

  /**
   * Retrieves an instance of a packet class based on its ID.
   *
   * @param id The ID associated with the packet class.
   * @return An instance of the specified packet class.
   * @throws InstantiationException If the packet class cannot be instantiated.
   * @throws IllegalAccessException If the packet class or its nullary constructor is not
   *     accessible.
   * @throws NoSuchMethodException If the nullary constructor for the packet class is not found.
   * @throws InvocationTargetException If an error occurs during instantiation.
   * @throws RuntimeException If the packet with the specified ID is not registered.
   */
  public Packet getPacket(int id)
      throws InstantiationException,
          IllegalAccessException,
          NoSuchMethodException,
          InvocationTargetException {
    if (!PACKETS_MAP.containsKey(id)) return null;
    return PACKETS_MAP.get(id).getDeclaredConstructor().newInstance();
  }

  /**
   * Checks if a packet with a given ID is registered.
   *
   * @param id The ID associated with the packet.
   * @return True if the packet is registered, false otherwise.
   */
  public boolean hasPacket(int id) {
    return PACKETS_MAP.containsKey(id);
  }
}
