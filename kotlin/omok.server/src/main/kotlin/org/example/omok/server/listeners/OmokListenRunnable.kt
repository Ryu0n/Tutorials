package org.example.omok.server.listeners

import org.example.omok.server.managers.RoomManager
import org.example.omok.server.packets.AttendancePacket
import org.example.omok.server.packets.ExitPacket
import org.example.omok.server.packets.Packet
import org.example.omok.server.packets.SetPlayerIdPacket
import org.example.omok.server.packets.data.SetPlayerIdPacketData
import org.example.omok.server.players.Player


class OmokListenRunnable(
    val roomManager: RoomManager,
    val player: Player,
) : Runnable {
    override fun run() {
        player.send(
            SetPlayerIdPacket(
                SetPlayerIdPacketData(
                    listOf(player.id)
                )
            )
        )
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