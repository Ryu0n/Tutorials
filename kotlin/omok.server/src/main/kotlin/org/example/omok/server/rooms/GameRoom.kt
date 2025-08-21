package org.example.omok.server.rooms

import org.example.omok.server.packets.CoordinatePacket
import org.example.omok.server.packets.MatchResultPacket
import org.example.omok.server.packets.NotifyPacket
import org.example.omok.server.packets.Packet
import org.example.omok.server.packets.data.MatchResultPacketData
import org.example.omok.server.packets.data.NotifyPacketData
import org.example.omok.server.players.Player

class GameRoom : Room {
    var playerTurn: Int = 1 // Placeholder for player turn logic, can be expanded later
    override val players: MutableList<Player> = mutableListOf()

    private var isGameFinished = false
    private val board = Array(19) { IntArray(19) }

    override fun addPlayer(player: Player) {
        if (players.size < 2) {
            players.add(player)
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
        } else {
            broadcast(
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

    override fun broadcast(packet: Packet) {
        if (packet is CoordinatePacket) {
            if (packet.packetData.playerColor.toInt() != playerTurn)  {
                return
            } else {
                if (isGameFinished) {
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
                    isGameFinished = true
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