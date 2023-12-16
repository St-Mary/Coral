package com.stmarygate.common.network;

import com.stmarygate.common.network.packets.Packet;
import io.netty.channel.Channel;

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
  public void write(Packet packet) {
    this.channel.writeAndFlush(packet);
  }

  /**
   * Closes the communication channel associated with the client session. This method can be used to
   * terminate the communication with the client.
   */
  public void close() {
    this.channel.close();
  }
}
