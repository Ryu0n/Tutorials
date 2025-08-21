package org.example.omok.server.listeners

import org.example.omok.server.packet.AttendancePacket
import org.example.omok.server.packet.ExitPacket
import org.example.omok.server.packet.NotifyPacket
import org.example.omok.server.packet.Packet
import org.example.omok.server.packet.SetColorPacket
import org.example.omok.server.packet.SetPlayerIdPacket
import org.example.omok.server.packet.data.NotifyPacketData
import org.example.omok.server.packet.data.SetColorPacketData
import org.example.omok.server.packet.data.SetPlayerIdPacketData
import org.example.omok.server.player.Player
import org.example.omok.server.room.GameRoom
import org.example.omok.server.room.Room


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
        } else {
            player.send(
                NotifyPacket(
                    NotifyPacketData(
                        listOf(
                            "Failed",
                            "[SYSTEM] ${player.id} is not in a game room."
                        )
                    )
                )
            )
        }
    }

    fun addPlayerToGameRoom(packet: AttendancePacket) {
        val roomId = packet.packetData.roomId
        if (roomId.isNotEmpty()) {
            if (gameRooms.any { it.players.any { p -> p.id == player.id } }) {
                player.send(
                    NotifyPacket(
                        NotifyPacketData(
                            listOf(
                                "Failed",
                                "[SYSTEM] ${player.id} is already in a game room."
                            )
                        )
                    )
                )
                return
            }
            if (waitingRoom.players.contains(player)) {
                waitingRoom.removePlayer(player)
            }
            val gameRoom = gameRooms.find { it.players.size < 2 } ?: GameRoom()
            gameRoom.addPlayer(player)
            if (!gameRooms.contains(gameRoom)) {
                gameRooms.add(gameRoom)
            }

            var color = "black"
            player.playerColor = 1 // Default color
            if (gameRoom.players.size == 2) {
                color = "white"
                player.playerColor = 2
            }
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
        player.send(
            SetPlayerIdPacket(
                SetPlayerIdPacketData(
                    listOf(player.id)
                )
            )
        )
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

                // TODO: Consider about dispatcher
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
            println("Error reading message from player ${player.id}: ${e.message}\n${e.stackTraceToString()}")
        } finally {
            removePlayerFromApplication()
        }
    }
}