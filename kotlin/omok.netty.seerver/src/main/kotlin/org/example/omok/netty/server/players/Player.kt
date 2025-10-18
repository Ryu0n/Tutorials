package org.example.omok.netty.server.players

import io.netty.channel.Channel
import org.example.omok.netty.server.packets.Packet
import java.util.UUID

class Player(
    val channel: Channel,
) {
    val id: String = "Player-" + UUID.randomUUID().toString().substring(0, 5)
    var playerColor: Int = 0 // Placeholder for player color, can be set later

    fun send(packet: Packet) {
        try {
            val bytes = packet.serialize()
            val data = String(bytes)
            // The StringEncoder and LineBasedFrameDecoder handle the newline.
            channel.writeAndFlush(data + "\n")
        } catch (e: Exception) {
            println("Error sending message to player $id: ${e.message}")
        }
    }

    fun close() {
        try {
            if (channel.isOpen) {
                channel.close()
            }
            println("Closed connection for player $id")
        } catch (e: Exception) {
            println("Error closing connection for player $id: ${e.message}")
        }
    }
}
