package org.example.omok.server.room


import org.example.omok.server.packet.NotifyPacket
import org.example.omok.server.packet.Packet
import org.example.omok.server.packet.data.NotifyPacketData
import org.example.omok.server.player.Player

class WaitingRoom : Room {
    override val players: MutableList<Player> = mutableListOf()

    override fun addPlayer(player: Player) {
        players.add(player)
        broadcast(
            NotifyPacket(
                NotifyPacketData(
                    listOf(
                        "Success",
                        "[SYSTEM] ${player.id} has joined the waiting room."
                    )
                )
            )
        )
    }

    override fun removePlayer(player: Player) {
        players.remove(player)
        broadcast(
            NotifyPacket(
                NotifyPacketData(
                    listOf(
                        "Success",
                        "[SYSTEM] ${player.id} has left the waiting room."
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