package org.example.omok_server.listeners

import org.example.omok_server.player.Player
import org.example.omok_server.room.Room
import java.io.BufferedReader
import java.io.InputStreamReader

class OmokListenRunnable(
    val room: Room,
    val player: Player,
) : Runnable {
    override fun run() {
        val inputStream = player.socket.inputStream
        val reader = BufferedReader(InputStreamReader(inputStream))

        try {
            while (true) {
                val message = reader.readLine() ?: break
                room.broadcast(message)
            }
        } catch (e: Exception) {
            println("Error reading message from player ${player.id}: ${e.message}")
        } finally {
            reader.close()
            player.socket.close()
            room.removePlayer(player)
        }
    }
}