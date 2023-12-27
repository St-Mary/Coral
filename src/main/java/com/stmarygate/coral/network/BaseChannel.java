package com.stmarygate.coral.network;

import com.stmarygate.coral.network.packets.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.lang.reflect.InvocationTargetException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a channel handler for handling incoming packets of type {@link Packet}. It uses a
 * {@link PacketHandler} to process and handle the business logic associated with received packets.
 */
@Getter
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class BaseChannel extends SimpleChannelInboundHandler<Packet> {
  /** The packet handler responsible for processing and handling business logic. */
  private final PacketHandler handler;

  /** The client session associated with this channel. */
  protected ClientSession session;

  /**
   * Constructs a new {@link BaseChannel} with the specified packet handler class.
   *
   * @param clazz The class of the packet handler to be used.
   * @throws RuntimeException If the packet handler class does not contain the required constructor.
   */
  public BaseChannel(Class<? extends PacketHandler> clazz) {
    try {
      this.handler = clazz.getDeclaredConstructor(BaseChannel.class).newInstance(this);
    } catch (InstantiationException
        | IllegalAccessException
        | InvocationTargetException
        | NoSuchMethodException e) {
      throw new RuntimeException(
          "PacketHandler " + clazz.getSimpleName() + " does not contain the required constructor.",
          e);
    }
  }

  /**
   * Handles an incoming packet by delegating the processing to the associated packet handler.
   *
   * @param ctx The channel handler context.
   * @param msg The incoming packet to be handled.
   */
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
    this.handler.handlePacket(msg);
  }

  /**
   * Invoked when a channel becomes active. Initializes the client session associated with this
   * channel.
   *
   * @param ctx The channel handler context.
   */
  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    this.session = new ClientSession(ctx.channel());
  }
}
