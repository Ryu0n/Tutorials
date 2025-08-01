package org.example.omok.server.room

import org.example.omok.server.packet.NotifyPacket
import org.example.omok.server.packet.Packet
import org.example.omok.server.packet.data.NotifyPacketData
import org.example.omok.server.player.Player

class GameRoom : Room {
    override val players: MutableList<Player> = mutableListOf()

    override fun addPlayer(player: Player) {
        if (players.size < 2) {
            players.add(player)
            broadcast(
                NotifyPacket(
                    NotifyPacketData(
                        listOf(
                            "Success",
                            "${player.id} has joined the game room."
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
                            "Game room is full. ${player.id} cannot join."
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
                        "${player.id} has left the game room."
                    )
                )
            )
        )
    }

    override fun broadcast(packet: Packet) {
        for (player in players) {
            player.send(packet)
        }
    }
}