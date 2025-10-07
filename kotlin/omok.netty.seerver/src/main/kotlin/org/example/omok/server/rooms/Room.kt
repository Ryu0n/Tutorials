package org.example.omok.netty.server.rooms

import org.example.omok.netty.server.packets.Packet
import org.example.omok.netty.server.players.Player

interface Room {
    val players: MutableList<Player>

    fun addPlayer(player: Player)
    fun removePlayer(player: Player)
    fun broadcast(packet: Packet)
}