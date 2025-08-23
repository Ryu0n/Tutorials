package org.example.omok.server.managers

import org.example.omok.server.enums.GameRoomStatusType
import org.example.omok.server.packets.NotifyPacket
import org.example.omok.server.packets.data.NotifyPacketData
import org.example.omok.server.players.Player
import org.example.omok.server.rooms.GameRoom
import org.example.omok.server.rooms.Room
import org.example.omok.server.rooms.WaitingRoom

class RoomManager {
    val waitingRoom = WaitingRoom()
    val gameRooms = mutableListOf<GameRoom>()
    val playerPosition = mutableMapOf<Player, Room>()

    fun addPlayerToWaitingRoom(player: Player) {
        if (playerPosition.containsKey(player)) {
            val currentRoom = playerPosition[player]
            if (currentRoom is WaitingRoom) {
                return player.send(
                    NotifyPacket(
                        NotifyPacketData(
                            listOf(
                                "Failed",
                                "[SYSTEM] ${player.id} is already in the waiting room."
                            )
                        )
                    )
                )
            }
            if (currentRoom is GameRoom) {
                currentRoom.removePlayer(player)
            }
            removeGameRoomIfEmpty(currentRoom)
        }
        waitingRoom.addPlayer(player)
        playerPosition[player] = waitingRoom
    }

    fun addPlayerToGameRoom(player: Player) {
        if (playerPosition.containsKey(player)) {
            val currentRoom = playerPosition[player]
            if (currentRoom is GameRoom) {
                return player.send(
                    NotifyPacket(
                        NotifyPacketData(
                            listOf(
                                "Failed",
                                "[SYSTEM] ${player.id} is already in a game room."
                            )
                        )
                    )
                )
            }
            if (currentRoom is WaitingRoom) {
                currentRoom.removePlayer(player)
            }
        }
        val gameRoom = gameRooms.find { it.status == GameRoomStatusType.WAITING.name }
        if (gameRoom != null) {
            gameRoom.addPlayer(player)
            playerPosition[player] = gameRoom
        } else {
            val newGameRoom = GameRoom()
            newGameRoom.addPlayer(player)
            gameRooms.add(newGameRoom)
            playerPosition[player] = newGameRoom
        }
    }

    fun removePlayerFromApplication(player: Player) {
        player.close()
        val currentRoom = playerPosition[player]
        currentRoom?.removePlayer(player)
        playerPosition.remove(player)
        removeGameRoomIfEmpty(currentRoom)
    }

    fun removeGameRoomIfEmpty(currentRoom: Room?) {
        if (currentRoom is GameRoom && currentRoom.players.isEmpty()) {
            gameRooms.remove(currentRoom)
        }
    }
}