package org.example.omok_server.room

import org.example.omok_server.player.Player

interface Room {
    val players: MutableList<Player>

    fun addPlayer(player: Player)
    fun removePlayer(player: Player)
    fun broadcast(message: String)
}