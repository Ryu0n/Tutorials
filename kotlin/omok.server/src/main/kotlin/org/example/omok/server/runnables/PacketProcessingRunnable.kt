package org.example.omok.server.runnables

import org.example.omok.server.managers.PlayerManager
import org.example.omok.server.managers.RoomManager
import org.example.omok.server.packets.AttendancePacket
import org.example.omok.server.packets.ExitPacket
import org.example.omok.server.packets.PacketMessage
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class PacketProcessingRunnable(
    val messageQueue: Flux<PacketMessage>,
    val roomManager: RoomManager,
    val playerManager: PlayerManager,
) : Runnable {
    override fun run() {
        messageQueue
            .flatMap { message ->
                Mono.fromRunnable<Void> {
                    println("Processing message from player ${message.playerId}: ${message.packet} ")
                    val playerId = message.playerId
                    val player = playerManager.players[playerId]
                    if (player == null) {
                        println("Player with ID $playerId not found.")
                        return@fromRunnable
                    }
                    val packet = message.packet
                    when (packet) {
                        is AttendancePacket -> {
                            roomManager.addPlayerToGameRoom(player)
                        }

                        is ExitPacket -> {
                            roomManager.addPlayerToWaitingRoom(player)
                        }

                        else -> {
                            val currentRoom = roomManager.playerPosition[player]
                            currentRoom?.broadcast(packet)
                        }
                    }
                }.onErrorResume { e ->
                    println("Error processing packet for player ${message.playerId}. Packet: ${message.packet}, Error: ${e.message}")
                    Mono.empty()
                }
            }
            .subscribe(
                null,
                { error -> println("Packet processing stream terminated with error: ${error.message}") }
            )
    }
}