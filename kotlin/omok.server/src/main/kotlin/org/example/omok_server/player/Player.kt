package org.example.omok_server.player

import java.net.Socket

class Player (
    val id: String,
    val socket: Socket,
) {
    fun sendMessage(message: String) {
        try {
            socket.getOutputStream().write(("$message\n").toByteArray())
            socket.getOutputStream().flush()
        } catch (e: Exception) {
            println("Error sending message to player $id: ${e.message}")
        }
    }
}