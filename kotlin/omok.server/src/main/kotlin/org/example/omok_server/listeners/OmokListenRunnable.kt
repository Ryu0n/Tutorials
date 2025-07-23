package org.example.omok_server.listeners

import org.example.omok_server.player.Player
import org.example.omok_server.room.GameRoom
import org.example.omok_server.room.Room
import java.io.BufferedReader
import java.io.InputStreamReader

class OmokListenRunnable(
    val waitingRoom: Room,
    val gameRooms: MutableList<Room>,
    val player: Player,
) : Runnable {
    override fun run() {
        val inputStream = player.socket.inputStream
        val reader = BufferedReader(InputStreamReader(inputStream))

        try {
            while (true) {
                val message = reader.readLine() ?: break
                if (message == "ready") {
                    if (waitingRoom.players.contains(player)) {
                        waitingRoom.removePlayer(player)
                    }
                    val gameRoom = gameRooms.find { it.players.size < 2 } ?: GameRoom()
                    gameRoom.addPlayer(player)
                    if (!gameRooms.contains(gameRoom)) {
                        gameRooms.add(gameRoom)
                    }
                } else {
                    // Handle other messages, e.g., game moves
                    gameRooms.forEach { room ->
                        if (room.players.contains(player)) {
                            room.broadcast("${player.id} : $message")
                        }
                    }
                    if (waitingRoom.players.contains(player)) {
                        waitingRoom.broadcast("${player.id} : $message")
                    }
                }
            }
        } catch (e: Exception) {
            println("Error reading message from player ${player.id}: ${e.message}")
        } finally {
            reader.close()
            player.socket.close()
            waitingRoom.removePlayer(player)
        }
    }
}