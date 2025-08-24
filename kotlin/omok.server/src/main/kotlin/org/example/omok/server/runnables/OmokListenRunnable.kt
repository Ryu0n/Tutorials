package org.example.omok.server.runnables

import org.example.omok.server.managers.RoomManager
import org.example.omok.server.packets.AttendancePacket
import org.example.omok.server.packets.ExitPacket
import org.example.omok.server.packets.Packet
import org.example.omok.server.packets.SetPlayerIdPacket
import org.example.omok.server.packets.SetRoomPacket
import org.example.omok.server.packets.data.SetPlayerIdPacketData
import org.example.omok.server.packets.data.SetRoomPacketData
import org.example.omok.server.players.Player
import reactor.core.publisher.Sinks


class OmokListenRunnable(
    val roomManager: RoomManager,
    val player: Player,
//    val sink: Sinks.Many<String>,
) : Runnable {
    override fun run() {
        player.send(
            SetPlayerIdPacket(
                SetPlayerIdPacketData(
                    listOf(player.id)
                )
            )
        )
        player.send(
            SetRoomPacket(
                SetRoomPacketData(
                    listOf("Waiting Room")
                )
            )
        )
//        sink.tryEmitNext("Player ${player.id} connected.")
        try {
            while (true) {
                val packet = player.receive()
                if (packet !is Packet) {
                    println("Received invalid packet from player ${player.id}: $packet")
                    roomManager.removePlayerFromApplication(player)
                    break
                }
                if (packet is AttendancePacket) {
                    roomManager.addPlayerToGameRoom(player)
                    continue
                } else if (packet is ExitPacket) {
                    roomManager.addPlayerToWaitingRoom(player)
                    continue
                }
                val currentRoom = roomManager.playerPosition[player]
                currentRoom?.broadcast(packet)
            }
        } catch (e: Exception) {
            println("Error reading message from player ${player.id}: ${e.message}\n${e.stackTraceToString()}")
        } finally {
            roomManager.removePlayerFromApplication(player)
        }
    }
}