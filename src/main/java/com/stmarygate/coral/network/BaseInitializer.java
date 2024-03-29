package com.stmarygate.coral.network;

import com.stmarygate.coral.network.codec.PacketDecoder;
import com.stmarygate.coral.network.codec.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Initializes a {@link SocketChannel} by configuring its {@link ChannelPipeline} with necessary
 * handlers for decoding and encoding packets, as well as the specified {@link BaseChannel} for
 * handling business logic.
 */
@AllArgsConstructor
public class BaseInitializer extends ChannelInitializer<SocketChannel> {

  /** The {@link BaseChannel} responsible for handling business logic. */
  private final BaseChannel channel;

  /**
   * Initializes the given {@link SocketChannel} by configuring its {@link ChannelPipeline}. Adds a
   * {@link PacketDecoder}, {@link PacketEncoder}, and the specified {@link BaseChannel} handler.
   *
   * @param ch The {@link SocketChannel} to be initialized.
   */
  @Override
  protected void initChannel(@NotNull SocketChannel ch) {
    ChannelPipeline pipeline = ch.pipeline();

    // Add packet decoding and encoding handlers
    pipeline.addLast("decoder", new PacketDecoder());
    pipeline.addLast("encoder", new PacketEncoder());

    // Add the business logic handler
    pipeline.addLast("handler", this.channel);
  }
}
