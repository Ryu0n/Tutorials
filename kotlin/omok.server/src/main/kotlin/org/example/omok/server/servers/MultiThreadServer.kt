package org.example.omok.server.servers

import org.example.omok.server.runnables.OmokListenRunnable
import org.example.omok.server.managers.RoomManager
import org.example.omok.server.players.Player
import org.example.omok.server.runnables.PacketProcessingRunnable
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.net.InetAddress
import java.net.ServerSocket
import java.util.UUID


@Component
class MultiThreadServer {
    private val serverSocket = ServerSocket(
        9090,
        50, // Maximum number of queued connections
        InetAddress.getByName("localhost"),
    )

    val roomManager = RoomManager()
//    val sink = Sinks.many().unicast().onBackpressureBuffer<String>()
//    val messageQueue: Flux<String> = sink.asFlux()

    fun start() {
//        Thread(
//            PacketProcessingRunnable(
//                messageQueue = messageQueue,
//            )
//        ).start()
        while (true) {
            val socket = serverSocket.accept()
            val player = Player(
                socket = socket,
            )
            roomManager.addPlayerToWaitingRoom(player)
            Thread(
                OmokListenRunnable(
                    roomManager = roomManager,
                    player = player,
//                    sink = sink,
                )
            ).start()
        }
    }
}