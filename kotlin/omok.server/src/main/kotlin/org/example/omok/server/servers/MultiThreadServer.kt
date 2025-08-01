package org.example.omok.server.servers

import org.example.omok.server.listeners.OmokListenRunnable
import org.example.omok.server.player.Player
import org.example.omok.server.room.Room
import org.example.omok.server.room.WaitingRoom
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

    val waitingRoom = WaitingRoom()
    val gameRooms = mutableListOf<Room>()

    fun start() {
        while (true) {
            val randomUUIDString = UUID.randomUUID().toString()
            val socket = serverSocket.accept()
            val player = Player(
                id = randomUUIDString,
                socket = socket,
            )
            waitingRoom.addPlayer(player)
            Thread(
                OmokListenRunnable(
                    waitingRoom = waitingRoom,
                    gameRooms = gameRooms,
                    player = player,
                )
            ).start()
        }
    }
}