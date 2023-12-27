package com.stmarygate.coral.network;

import com.stmarygate.coral.network.packets.Packet;
import io.netty.channel.Channel;

import java.util.Arrays;

/**
 * Represents a session associated with a client's communication channel. It provides methods to
 * send packets to the client and close the communication {@link Channel} associated with the client.
 */
public class ClientSession {
  /** The communication channel associated with the client session. */
  private final Channel channel;

  /**
   * Constructs a new client session with the specified communication channel.
   *
   * @param channel The communication channel associated with the client session.
   */
  public ClientSession(Channel channel) {
    this.channel = channel;
  }

  /**
   * Writes the given packet to the client by sending it over the communication channel. The packet
   * is flushed immediately after writing.
   *
   * @param packet The packet to be sent to the client.
   */
  public void write(Packet packet) throws Exception {
    try {
      this.channel.writeAndFlush(packet);
    } catch (Exception e) {
      throw new Exception("Error writing packet " + packet.getClass().getSimpleName() + " to client " + this.channel.remoteAddress().toString() + "\nError:\n" + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
    }
  }

  /**
   * Closes the communication channel associated with the client session. This method can be used to
   * terminate the communication with the client.
   */
  public void close() {
    this.channel.close();
  }
}
