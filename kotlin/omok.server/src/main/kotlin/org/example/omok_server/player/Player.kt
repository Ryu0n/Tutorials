package org.example.omok_server.player

import org.example.omok_server.packet.AttendancePacket
import org.example.omok_server.packet.CoordinatePacket
import org.example.omok_server.packet.MessagePacket
import org.example.omok_server.packet.data.MessagePacketData
import org.example.omok_server.packet.Packet
import org.example.omok_server.packet.PacketType
import org.example.omok_server.packet.data.AttendancePacketData
import org.example.omok_server.packet.data.CoordinatePacketData
import java.net.Socket

class Player (
    val id: String,
    val socket: Socket,
) {
    val buffer = ByteArray(32)
    val inputStream = socket.inputStream
    val outputStream = socket.outputStream

    fun deserialize(bytes: ByteArray): Packet {
        val data = String(bytes)
        val startIndex = data.indexOf("<") + 1
        val endIndex = data.indexOf(">")
        val content = data.substring(startIndex, endIndex)
        val packetAttrs = content.split(":")
        val packetTypeString = packetAttrs[0]
        val payload = packetAttrs.slice(1 until packetAttrs.size)

        return when(packetTypeString) {
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
            // <COORDINATE:x:y>
            PacketType.COORDINATE.name -> CoordinatePacket(
                CoordinatePacketData(
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
        return packet
    }

    fun send(packet: Packet) {
        try {
            outputStream.write(packet.serialize())
            outputStream.flush()
        } catch (e: Exception) {
            println("Error sending message to player $id: ${e.message}")
        }
    }
}