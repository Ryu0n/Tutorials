package org.example.omok_server.listeners

import org.example.omok_server.packet.AttendancePacket
import org.example.omok_server.packet.ExitPacket
import org.example.omok_server.packet.Packet
import org.example.omok_server.packet.SetColorPacket
import org.example.omok_server.packet.data.SetColorPacketData
import org.example.omok_server.player.Player
import org.example.omok_server.room.GameRoom
import org.example.omok_server.room.Room


class OmokListenRunnable(
    val waitingRoom: Room,
    val gameRooms: MutableList<Room>,
    val player: Player,
) : Runnable {
    fun removePlayerFromApplication() {
        player.close()
        val gameRoom = gameRooms.find { it.players.contains(player) }
        if (gameRoom != null) {
            gameRoom.removePlayer(player)
            if (gameRoom.players.isEmpty()) {
                gameRooms.remove(gameRoom)
            }
        } else if (waitingRoom.players.contains(player)) {
            waitingRoom.removePlayer(player)
        }
    }

    fun removePlayerFromGameRoom() {
        val gameRoom = gameRooms.find { it.players.contains(player) }
        if (gameRoom != null) {
            println("Removing player ${player.id} from game room")
            gameRoom.removePlayer(player)
            if (gameRoom.players.isEmpty()) {
                gameRooms.remove(gameRoom)
            }
            waitingRoom.addPlayer(player)
        }
    }

    fun addPlayerToGameRoom(packet: AttendancePacket) {
        val roomId = packet.packetData.roomId
        if (roomId.isNotEmpty()) {
            if (waitingRoom.players.contains(player)) {
                waitingRoom.removePlayer(player)
            }
            val gameRoom = gameRooms.find { it.players.size < 2 } ?: GameRoom()
            gameRoom.addPlayer(player)
            if (!gameRooms.contains(gameRoom)) {
                gameRooms.add(gameRoom)
            }
            val color = gameRoom.players.size.toString()
            player.send(
                SetColorPacket(
                    SetColorPacketData(
                        listOf(color)
                    )
                )
            )
        }
    }

    override fun run() {
        try {
            while (true) {
                val packet = player.receive()
                if (packet !is Packet) {
                    println("Received invalid packet from player ${player.id}: $packet")
                    removePlayerFromApplication()
                    break
                }
                if (packet is AttendancePacket) {
                    addPlayerToGameRoom(packet)
                    continue
                } else if (packet is ExitPacket) {
                    removePlayerFromGameRoom()
                    continue
                }

                // Broadcast the packet to all players in the game rooms and waiting room (e.g. Chats, moves)
                gameRooms.forEach { room ->
                    if (room.players.contains(player)) {
                        room.broadcast(packet)
                    }
                }
                if (waitingRoom.players.contains(player)) {
                    waitingRoom.broadcast(packet)
                }
            }
        } catch (e: Exception) {
            println("Error reading message from player ${player.id}: ${e.message}")
        } finally {
            removePlayerFromApplication()
        }
    }
}