package org.example.omok.server.players

import org.example.omok.server.packets.AttendancePacket
import org.example.omok.server.packets.CoordinatePacket
import org.example.omok.server.packets.ExitPacket
import org.example.omok.server.packets.MatchResultPacket
import org.example.omok.server.packets.MessagePacket
import org.example.omok.server.packets.data.MessagePacketData
import org.example.omok.server.packets.Packet
import org.example.omok.server.packets.PacketType
import org.example.omok.server.packets.data.AttendancePacketData
import org.example.omok.server.packets.data.CoordinatePacketData
import org.example.omok.server.packets.data.ExitPacketData
import org.example.omok.server.packets.data.MatchResultPacketData
import java.net.Socket
import java.util.UUID

class Player (
    val socket: Socket,
) {
    val id: String = "Player-${UUID.randomUUID().toString().substring(0, 5)}"
    val buffer = ByteArray(256)
    val inputStream = socket.inputStream
    val outputStream = socket.outputStream
    var playerColor: Int = 0 // Placeholder for player color, can be set later

    fun deserialize(bytes: ByteArray): Packet {
        val data = String(bytes)
        val startIndex = data.indexOf("<") + 1
        val endIndex = data.indexOf(">")
        val content = data.substring(startIndex, endIndex)
        val packetAttrs = content.split(":")
        val packetTypeString = packetAttrs[0]
        val payload = packetAttrs.slice(1 until packetAttrs.size)

        return when(packetTypeString) {
            // Travel to classes
            // <MESSAGE:message>
            PacketType.MESSAGE.name -> MessagePacket(
                MessagePacketData(
                    payload,
                )
            )
            // <ATTENDANCE:romId>
            PacketType.ATTENDANCE.name -> AttendancePacket(
                AttendancePacketData(
                    payload,
                )
            )
            // <EXIT:roomId>
            PacketType.EXIT.name -> ExitPacket(
                ExitPacketData(
                    payload,
                )
            )
            // <COORDINATE:x:y:1|2>
            PacketType.COORDINATE.name -> CoordinatePacket(
                CoordinatePacketData(
                    payload,
                )
            )
            // <MATCH_RESULT:winPlayerId>
            PacketType.MATCH_RESULT.name -> MatchResultPacket(
                MatchResultPacketData(
                    payload,
                )
            )
            else -> throw IllegalArgumentException("Unknown packet type")
        }
    }

    fun receive() : Packet? {
        val bytesRead = inputStream.read(buffer)
        if (bytesRead == -1) {
            println("No more data to read")
            return null
        }

        val receivedBytes = buffer.copyOf(bytesRead)
        val packet = deserialize(receivedBytes)
        println("Received packet from player $id: ${packet::class.simpleName} with data: ${packet.toString()}")
        return packet
    }

    fun send(packet: Packet) {
        try {
            val bytes = packet.serialize()
            outputStream.write(bytes)
            println("Sent packet to player $id: ${packet::class.simpleName} with data: ${packet.toString()}")
            outputStream.flush()
        } catch (e: Exception) {
            println("Error sending message to player $id: ${e.message}")
        }
    }

    fun close() {
        try {
            if (!socket.isClosed) {
                socket.close()
            }
            println("Closed connection for player $id")
        } catch (e: Exception) {
            println("Error closing connection for player $id: ${e.message}")
        }
    }
}