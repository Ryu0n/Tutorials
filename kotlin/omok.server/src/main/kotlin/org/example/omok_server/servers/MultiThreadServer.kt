package org.example.omok_server.servers

import org.example.omok_server.listeners.ListenRunnable
import org.example.omok_server.listeners.OmokListenRunnable
import org.example.omok_server.player.Player
import org.example.omok_server.room.WaitingRoom
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

    val room = WaitingRoom()

    fun start() {
        while (true) {
            val randomUUIDString = UUID.randomUUID().toString()
            val socket = serverSocket.accept()
            val player = Player(
                id = randomUUIDString,
                socket = socket,
            )
            room.addPlayer(player)
            Thread(
                OmokListenRunnable(
                    room = room,
                    player = player,
                )
            ).start()
        }
    }
}