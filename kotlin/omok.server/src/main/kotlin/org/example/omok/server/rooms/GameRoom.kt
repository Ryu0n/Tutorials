package org.example.omok.server.rooms

import org.example.omok.server.enums.GameRoomStatusType
import org.example.omok.server.packets.CoordinatePacket
import org.example.omok.server.packets.MatchResultPacket
import org.example.omok.server.packets.NotifyPacket
import org.example.omok.server.packets.Packet
import org.example.omok.server.packets.SetColorPacket
import org.example.omok.server.packets.SetRoomPacket
import org.example.omok.server.packets.data.MatchResultPacketData
import org.example.omok.server.packets.data.NotifyPacketData
import org.example.omok.server.packets.data.SetColorPacketData
import org.example.omok.server.packets.data.SetRoomPacketData
import org.example.omok.server.players.Player
import java.util.UUID
import java.util.concurrent.CopyOnWriteArrayList

class GameRoom : Room {
    val id: String = "ROOM-${UUID.randomUUID().toString().substring(0, 5)}"
    override val players: MutableList<Player> = CopyOnWriteArrayList()
    var playerTurn: Int = 1 // Placeholder for player turn logic, can be expanded later
    var status: String = GameRoomStatusType.WAITING.name

    private val board = Array(19) { IntArray(19) }

    override fun addPlayer(player: Player) {
        player.send(
            SetRoomPacket(
                SetRoomPacketData(
                    listOf(id)
                )
            )
        )
        if (players.isEmpty()) {
            player.playerColor = 1 // Black
            player.send(
                NotifyPacket(
                    NotifyPacketData(
                        listOf(
                            "Success",
                            "[SYSTEM] ${player.id} has joined the game room."
                        )
                    )
                )
            )
            player.send(
                SetColorPacket(
                    SetColorPacketData(
                        listOf("black")
                    )
                )
            )
        } else if (players.size == 1) {
            val prevPlayerColor = players[0].playerColor
            if (prevPlayerColor == 1) {
                player.playerColor = 2 // Set to white if the previous player is black
                player.send(
                    SetColorPacket(
                        SetColorPacketData(
                            listOf("white")
                        )
                    )
                )
            } else {
                player.playerColor = 1 // Set to black if the previous player is white
                player.send(
                    SetColorPacket(
                        SetColorPacketData(
                            listOf("black")
                        )
                    )
                )
            }
        } else if (players.size >= 2) {
            return player.send(
                NotifyPacket(
                    NotifyPacketData(
                        listOf(
                            "Failed",
                            "[SYSTEM] Game room is full. ${player.id} cannot join."
                        )
                    )
                )
            )
        }

        players.add(player)
        if (players.size >= 2) {
            status = GameRoomStatusType.IN_PROGRESS.name
        }
        broadcast(
            NotifyPacket(
                NotifyPacketData(
                    listOf(
                        "Success",
                        "[SYSTEM] ${player.id} has joined the game room."
                    )
                )
            )
        )
    }

    override fun removePlayer(player: Player) {
        players.remove(player)
        broadcast(
            NotifyPacket(
                NotifyPacketData(
                    listOf(
                        "Success",
                        "[SYSTEM] ${player.id} has left the game room."
                    )
                )
            )
        )
        if (status == GameRoomStatusType.IN_PROGRESS.name) {
            status = GameRoomStatusType.WAITING.name
            val remainingPlayer = players.firstOrNull()
            if (remainingPlayer != null) {
                broadcast(
                    MatchResultPacket(
                        MatchResultPacketData(
                            payload = listOf(
                                remainingPlayer.id,
                            )
                        )
                    )
                )
            }
        }
    }

    private fun placeStone(playerColor: Int, x: Int, y: Int): Boolean {
        if (x in 0..18 && y in 0..18 && board[y][x] == 0) {
            board[y][x] = playerColor
            return true
        }
        return false // Invalid move
    }

    private fun checkFiveInARow(playerColor: Int, x: Int, y: Int): Boolean {
        val directions = listOf(
            Pair(1, 0),  // 가로
            Pair(0, 1),  // 세로
            Pair(1, 1),  // 대각 ↘
            Pair(1, -1)  // 대각 ↗
        )
        for ((dx, dy) in directions) {
            var count = 1
            // 한 방향
            var nx = x + dx
            var ny = y + dy
            while (nx in 0..18 && ny in 0..18 && board[ny][nx] == playerColor) {
                count++
                nx += dx
                ny += dy
            }
            // 반대 방향
            nx = x - dx
            ny = y - dy
            while (nx in 0..18 && ny in 0..18 && board[ny][nx] == playerColor) {
                count++
                nx -= dx
                ny -= dy
            }
            if (count >= 5) return true
        }
        return false
    }

    private fun sendBroadcast(packet: Packet) {
        for (player in players) {
            player.send(packet)
        }
    }

    fun reset() {
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = 0
            }
        }
        playerTurn = 1
    }

    override fun broadcast(packet: Packet) {
        if (packet is CoordinatePacket) {
            if (status != GameRoomStatusType.IN_PROGRESS.name) {
                return sendBroadcast(
                    NotifyPacket(
                        NotifyPacketData(
                            listOf(
                                "Failed",
                                "[SYSTEM] Game is not in progress. Cannot place stone."
                            )
                        )
                    )
                )
            }
            if (packet.packetData.playerColor.toInt() != playerTurn)  {
                return
            } else {
                if (status == GameRoomStatusType.FINISHED.name) {
                    return
                }
                val x = packet.packetData.x.toInt()
                val y = packet.packetData.y.toInt()
                val playerColor = packet.packetData.playerColor.toInt()
                playerTurn = 3 - playerColor // Toggle between 1 and 2
                placeStone(
                    playerColor = playerColor,
                    x = x,
                    y = y,
                )
                if (checkFiveInARow(playerColor, x, y)) {
                    reset()
                    status = GameRoomStatusType.FINISHED.name
                    return sendBroadcast(
                        MatchResultPacket(
                            MatchResultPacketData(
                                payload = listOf(
                                    players.find { it.playerColor == playerColor }?.id ?: "Unknown Player",
                                )
                            )
                        )
                    )
                }
            }
        }
        sendBroadcast(packet)
    }
}