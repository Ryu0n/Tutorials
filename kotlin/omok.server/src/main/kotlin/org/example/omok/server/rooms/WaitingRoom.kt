package org.example.omok.server.rooms

import java.util.Collections
import org.example.omok.server.packets.NotifyPacket
import org.example.omok.server.packets.Packet
import org.example.omok.server.packets.data.NotifyPacketData
import org.example.omok.server.players.Player

class WaitingRoom : Room {
    override val players: MutableList<Player> = Collections.synchronizedList(
        mutableListOf()
    )

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