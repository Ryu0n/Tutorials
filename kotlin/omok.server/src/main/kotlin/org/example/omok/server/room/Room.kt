package org.example.omok.server.room

import org.example.omok.server.packet.Packet
import org.example.omok.server.player.Player

interface Room {
    val players: MutableList<Player>

    fun addPlayer(player: Player)
    fun removePlayer(player: Player)
    fun broadcast(packet: Packet)
}