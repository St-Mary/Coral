package com.stmarygate.coral.utils;

import io.netty.channel.ChannelHandlerContext;
import java.net.SocketAddress;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/** A utility class containing various methods for both the client and server. */
public class Utils {
  /**
   * Wait the specified amount of time.
   *
   * @param ms The amount of time to wait in milliseconds.
   */
  public static void wait(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Log a channel message.
   *
   * @param LOGGER The logger to be used.
   * @param remoteAddress The remote address of the channel.
   * @param message The message to be logged.
   */
  public static void logChannel(
      @NotNull Logger LOGGER, @NotNull String remoteAddress, @NotNull String message) {
    LOGGER.info("[{}] - {}", remoteAddress, message);
  }

  /**
   * Get the remote address of the specified channel.
   *
   * @param address The socket address.
   * @return The remote address of the channel as a string.
   */
  public static String getRemote(@NotNull SocketAddress address) {
    return address.toString();
  }

  /**
   * Get the remote address of the specified channel.
   *
   * @param ctx The channel context.
   * @return The remote address of the channel.
   */
  public static String getRemote(@NotNull ChannelHandlerContext ctx) {
    return ctx.channel().remoteAddress() == null ? "" : ctx.channel().remoteAddress().toString();
  }
}
