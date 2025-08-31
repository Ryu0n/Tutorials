package org.example.omok.server.rooms

import java.util.Collections
import org.example.omok.server.packets.NotifyPacket
import org.example.omok.server.packets.Packet
import org.example.omok.server.packets.SetRoomPacket
import org.example.omok.server.packets.data.NotifyPacketData
import org.example.omok.server.packets.data.SetRoomPacketData
import org.example.omok.server.players.Player

class WaitingRoom : Room {
    override val players: MutableList<Player> = Collections.synchronizedList(
        mutableListOf()
    )

    override fun addPlayer(player: Player) {
        player.send(
            SetRoomPacket(
                SetRoomPacketData(
                    listOf("Waiting Room")
                )
            )
        )
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
        val playersCopy: List<Player>
        synchronized(players) {
            playersCopy = ArrayList(players)
        }
        for (player in playersCopy) {
            player.send(packet)
        }
    }
}