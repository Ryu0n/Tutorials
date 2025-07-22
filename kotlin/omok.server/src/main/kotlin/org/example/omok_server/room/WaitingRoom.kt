package org.example.omok_server.room

import org.example.omok_server.player.Player

class WaitingRoom : Room {
    override val players: MutableList<Player> = mutableListOf()

    override fun addPlayer(player: Player) {
        players.add(player)
        broadcast("${player.id} has joined the waiting room.")
    }

    override fun removePlayer(player: Player) {
        players.remove(player)
        broadcast("${player.id} has left the waiting room.")
    }

    override fun broadcast(message: String) {
    }
}