package org.example.omok.server.servers

import org.example.omok.server.listeners.OmokListenRunnable
import org.example.omok.server.managers.RoomManager
import org.example.omok.server.players.Player
import org.springframework.stereotype.Component
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

    fun start() {
        while (true) {
            val randomUUIDString = UUID.randomUUID().toString()
            val socket = serverSocket.accept()
            val player = Player(
                id = randomUUIDString,
                socket = socket,
            )
            roomManager.addPlayerToWaitingRoom(player)
            Thread(
                OmokListenRunnable(
                    roomManager = roomManager,
                    player = player,
                )
            ).start()
        }
    }
}