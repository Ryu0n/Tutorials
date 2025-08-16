package org.example.omok.server.room

import org.example.omok.server.packet.CoordinatePacket
import org.example.omok.server.packet.NotifyPacket
import org.example.omok.server.packet.Packet
import org.example.omok.server.packet.data.NotifyPacketData
import org.example.omok.server.player.Player

class GameRoom : Room {
    var playerTurn: Int = 1 // Placeholder for player turn logic, can be expanded later
    override val players: MutableList<Player> = mutableListOf()

    override fun addPlayer(player: Player) {
        if (players.size < 2) {
            players.add(player)
            broadcast(
                NotifyPacket(
                    NotifyPacketData(
                        listOf(
                            "Success",
                            "[SYSTEM] ${player.id} has joined the game room."
                        )
                    )
                )
            )
        } else {
            broadcast(
                NotifyPacket(
                    NotifyPacketData(
                        listOf(
                            "Failed",
                            "[SYSTEM] Game room is full. ${player.id} cannot join."
                        )
                    )
                )
            )
        }
    }

    override fun removePlayer(player: Player) {
        players.remove(player)
        broadcast(
            NotifyPacket(
                NotifyPacketData(
                    listOf(
                        "Success",
                        "[SYSTEM] ${player.id} has left the game room."
                    )
                )
            )
        )
    }

    override fun broadcast(packet: Packet) {
        if (packet is CoordinatePacket) {
            if (packet.packetData.playerColor.toInt() != playerTurn)  {
                return
            } else {
                val playerColor = packet.packetData.playerColor.toInt()
                playerTurn = 3 - playerColor // Toggle between 1 and 2
            }
        }
        for (player in players) {
            player.send(packet)
        }
    }
}