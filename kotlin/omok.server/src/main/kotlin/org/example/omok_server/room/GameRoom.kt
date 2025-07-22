package org.example.omok_server.room

import org.example.omok_server.player.Player

class GameRoom : Room {
    override val players: MutableList<Player> = mutableListOf()

    override fun addPlayer(player: Player) {
        if (players.size < 2) {
            players.add(player)
            broadcast("${player.id} has joined the game room.")
        } else {
            broadcast("Game room is full. ${player.id} cannot join.")
        }
    }

    override fun removePlayer(player: Player) {
        players.remove(player)
        broadcast("${player.id} has left the waiting room.")
    }

    override fun broadcast(message: String) {
    }
}