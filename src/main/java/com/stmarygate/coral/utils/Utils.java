package com.stmarygate.coral.utils;

import io.netty.channel.ChannelHandlerContext;
import java.net.SocketAddress;
import org.slf4j.Logger;

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

  public static void logChannel(Logger LOGGER, String remoteAddress, String message) {
    LOGGER.info("[{}] - {}", remoteAddress, message);
  }

  public static String getRemote(SocketAddress address) {
    return address.toString();
  }

  public static String getRemote(ChannelHandlerContext ctx) {
    return ctx.channel().remoteAddress() == null ? "" : ctx.channel().remoteAddress().toString();
  }
}
