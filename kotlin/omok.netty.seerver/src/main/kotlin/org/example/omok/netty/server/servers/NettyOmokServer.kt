package org.example.omok.netty.server.servers

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.LineBasedFrameDecoder
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.util.CharsetUtil
import org.example.omok.netty.server.handlers.OmokServerHandler
import org.example.omok.netty.server.managers.PlayerManager
import org.example.omok.netty.server.managers.RoomManager
import org.example.omok.netty.server.packets.PacketMessage
import org.example.omok.netty.server.runnables.PacketProcessingRunnable
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks

@Component
class NettyOmokServer(
    val PORT: Int = 8009,
) {
    fun start() {
        println("Netty Omok Server started on port $PORT")

        val bossGroup = NioEventLoopGroup()
        val workerGroup = NioEventLoopGroup()

        val roomManager = RoomManager()
        val playerManager = PlayerManager()
        val sink = Sinks.many().unicast().onBackpressureBuffer<PacketMessage>()
        val messageQueue: Flux<PacketMessage> = sink.asFlux()

        try {
            Thread(
                PacketProcessingRunnable(
                    messageQueue = messageQueue,
                    roomManager = roomManager,
                    playerManager = playerManager,
                )
            ).start()

            val b = ServerBootstrap()
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(
                    object : ChannelInitializer<SocketChannel>() {
                        override fun initChannel(ch: SocketChannel) {
                            ch.pipeline().addLast(LineBasedFrameDecoder(8192))
                            ch.pipeline().addLast(StringDecoder(CharsetUtil.UTF_8))
                            ch.pipeline().addLast(StringEncoder(CharsetUtil.UTF_8))
                            ch.pipeline().addLast(
                                OmokServerHandler(
                                    roomManager = roomManager,
                                    playerManager = playerManager,
                                    sink = sink,
                                )
                            )
                        }
                    }
                )
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)

            // Bind and start to accept incoming connections.
            val f = b.bind(PORT).sync()

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            workerGroup.shutdownGracefully()
            bossGroup.shutdownGracefully()
        }
    }
}