package org.example.omok.server.runnables

import org.example.omok.server.managers.PlayerManager
import org.example.omok.server.managers.RoomManager
import org.example.omok.server.packets.AttendancePacket
import org.example.omok.server.packets.ExitPacket
import org.example.omok.server.packets.PacketMessage
import reactor.core.publisher.Flux

class PacketProcessingRunnable(
    val messageQueue: Flux<PacketMessage>,
    val roomManager: RoomManager,
    val playerManager: PlayerManager,
) : Runnable {
    override fun run() {
        messageQueue
            .doOnNext(
                { message ->
                    val playerId = message.playerId
                    val player = playerManager.players[playerId]
                    if (player == null) {
                        println("Player with ID $playerId not found.")
                        return@doOnNext
                    }
                    val packet = message.packet
                    if (packet is AttendancePacket) {
                        roomManager.addPlayerToGameRoom(player)
                        return@doOnNext
                    } else if (packet is ExitPacket) {
                        roomManager.addPlayerToWaitingRoom(player)
                        return@doOnNext
                    }
                    val currentRoom = roomManager.playerPosition[player]
                    currentRoom?.broadcast(packet)
                }
            )
            .subscribe()
    }
}