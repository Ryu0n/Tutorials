package org.example.omok.netty.server.rooms

import org.example.omok.netty.server.packets.NotifyPacket
import org.example.omok.netty.server.packets.Packet
import org.example.omok.netty.server.packets.SetRoomPacket
import org.example.omok.netty.server.packets.data.NotifyPacketData
import org.example.omok.netty.server.packets.data.SetRoomPacketData
import org.example.omok.netty.server.players.Player
import java.util.concurrent.CopyOnWriteArrayList

class WaitingRoom : Room {
    override val players: MutableList<Player> = CopyOnWriteArrayList()

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