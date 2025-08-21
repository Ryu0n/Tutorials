package org.example.omok.server.rooms

import org.example.omok.server.packets.Packet
import org.example.omok.server.players.Player

interface Room {
    val players: MutableList<Player>

    fun addPlayer(player: Player)
    fun removePlayer(player: Player)
    fun broadcast(packet: Packet)
}