package org.example.omok.netty.server.managers

import io.netty.channel.Channel
import org.example.omok.netty.server.packets.SetPlayerIdPacket
import org.example.omok.netty.server.packets.SetRoomPacket
import org.example.omok.netty.server.packets.data.SetPlayerIdPacketData
import org.example.omok.netty.server.packets.data.SetRoomPacketData
import org.example.omok.netty.server.players.Player
import java.util.concurrent.ConcurrentHashMap

class PlayerManager {
    val playersById = ConcurrentHashMap<String, Player>()
    val playersByChannel = ConcurrentHashMap<Channel, Player>()

    fun addPlayer(channel: Channel): Player {
        val player = Player(channel = channel)
        playersById[player.id] = player
        playersByChannel[channel] = player

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
        println("Player ${player.id} added.")
        return player
    }

    fun removePlayer(player: Player) {
        playersById.remove(player.id)
        playersByChannel.remove(player.channel)
        println("Player ${player.id} removed.")
    }

    fun getPlayer(playerId: String): Player? {
        return playersById[playerId]
    }

    fun getPlayer(channel: Channel): Player? {
        return playersByChannel[channel]
    }
}