package org.example.omok.netty.server.runnables

import org.example.omok.netty.server.managers.PlayerManager
import org.example.omok.netty.server.managers.RoomManager
import org.example.omok.netty.server.packets.AttendancePacket
import org.example.omok.netty.server.packets.ExitPacket
import org.example.omok.netty.server.packets.PacketMessage
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

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
                    val player = playerManager.getPlayer(playerId)
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
                }
//                    .subscribeOn(Schedulers.parallel())
                    .subscribeOn(Schedulers.boundedElastic())
                    .onErrorResume { e ->
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