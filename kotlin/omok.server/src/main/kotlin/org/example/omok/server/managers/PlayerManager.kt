package org.example.omok.server.managers

import org.example.omok.server.packets.SetPlayerIdPacket
import org.example.omok.server.packets.SetRoomPacket
import org.example.omok.server.packets.data.SetPlayerIdPacketData
import org.example.omok.server.packets.data.SetRoomPacketData
import org.example.omok.server.players.Player
import java.net.Socket

class PlayerManager {
    val players = mutableMapOf<String, Player>()

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
}