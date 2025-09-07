package org.example.omok.server.runnables

import org.example.omok.server.managers.PlayerManager
import org.example.omok.server.managers.RoomManager
import org.example.omok.server.packets.Packet
import org.example.omok.server.packets.PacketMessage
import org.example.omok.server.players.Player
import reactor.core.publisher.Sinks


class OmokListenRunnable(
    val roomManager: RoomManager,
    val playerManager: PlayerManager,
    val player: Player,
    val sink: Sinks.Many<PacketMessage>,
) : Runnable {
    override fun run() {
        try {
            while (true) {
                val packet = player.receive()
                if (packet !is Packet) {
                    println("Received invalid packet from player ${player.id}: $packet")
                    break
                }
                sink.tryEmitNext(
                    PacketMessage(
                        playerId = player.id,
                        packet = packet,
                    )
                )
            }
        } catch (e: Exception) {
            println("Error reading message from player ${player.id}: ${e.message}")
        } finally {
            roomManager.removePlayerFromApplication(player)
            playerManager.removePlayer(player)
        }
    }
}