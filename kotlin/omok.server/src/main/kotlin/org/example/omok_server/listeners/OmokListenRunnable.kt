package org.example.omok_server.listeners

import org.example.omok_server.packet.AttendancePacket
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
    override fun run() {
        try {
            while (true) {
                val packet = player.receive()
                if (packet !is Packet) {
                    continue
                }

                if (packet is AttendancePacket) {
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
                    continue
                }

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
            player.socket.close()
            waitingRoom.removePlayer(player)
        }
    }
}