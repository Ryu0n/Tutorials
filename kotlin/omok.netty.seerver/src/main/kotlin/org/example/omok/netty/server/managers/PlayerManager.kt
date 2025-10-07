package org.example.omok.netty.server.managers

import org.example.omok.netty.server.packets.SetPlayerIdPacket
import org.example.omok.netty.server.packets.SetRoomPacket
import org.example.omok.netty.server.packets.data.SetPlayerIdPacketData
import org.example.omok.netty.server.packets.data.SetRoomPacketData
import org.example.omok.netty.server.players.Player
import java.net.Socket
import java.util.concurrent.ConcurrentHashMap

class PlayerManager {
    val players = ConcurrentHashMap<String, Player>()

    fun addPlayer(socket: Socket): Player {
        val player = Player(socket = socket)
        players[player.id] = player
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
        return player
    }

    fun removePlayer(player: Player) {
        players.remove(player.id)
    }
}