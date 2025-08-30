package org.example.omok.server.servers

import org.example.omok.server.managers.PlayerManager
import org.example.omok.server.runnables.OmokListenRunnable
import org.example.omok.server.managers.RoomManager
import org.example.omok.server.packets.PacketMessage
import org.example.omok.server.runnables.PacketProcessingRunnable
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.net.InetAddress
import java.net.ServerSocket


@Component
class MultiThreadServer {
    private val serverSocket = ServerSocket(
        9090,
        50, // Maximum number of queued connections
        InetAddress.getByName("localhost"),
    )

    val roomManager = RoomManager()
    val playerManager = PlayerManager()
    val sink = Sinks.many().unicast().onBackpressureBuffer<PacketMessage>()
    val messageQueue: Flux<PacketMessage> = sink.asFlux()

    fun start() {
        Thread(
            PacketProcessingRunnable(
                messageQueue = messageQueue,
                roomManager = roomManager,
                playerManager = playerManager,
            )
        ).start()
        while (true) {
            val socket = serverSocket.accept()
            val player = playerManager.addPlayer(socket)
            roomManager.addPlayerToWaitingRoom(player)
            Thread(
                OmokListenRunnable(
                    roomManager = roomManager,
                    player = player,
                    sink = sink,
                )
            ).start()
        }
    }
}