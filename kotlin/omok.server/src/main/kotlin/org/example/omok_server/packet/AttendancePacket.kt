package org.example.omok_server.packet

import org.example.omok_server.packet.data.AttendancePacketData

class AttendancePacket(
    override val packetData: AttendancePacketData,
) : Packet(packetData) {
    override  fun serialize(): ByteArray {
        return "${startDelimeter}${PacketType.ATTENDANCE.name}${delimeter}${packetData.roomId}${endDelimeter}".toByteArray()
    }
}