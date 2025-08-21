package org.example.omok.server.packets

import org.example.omok.server.packets.data.AttendancePacketData

class AttendancePacket(
    override val packetData: AttendancePacketData,
) : Packet(packetData) {
    override fun toString(): String {
        return "${startDelimeter}${PacketType.ATTENDANCE.name}${delimeter}${packetData.roomId}${endDelimeter}"
    }

    override  fun serialize(): ByteArray {
        return toString().toByteArray()
    }
}