package org.example.omok_server.packet

import org.example.omok_server.packet.data.SetColorPacketData

class SetColorPacket(
    override val packetData: SetColorPacketData,
) : Packet(packetData) {
    override  fun serialize(): ByteArray {
        return "${startDelimeter}${PacketType.ATTENDANCE.name}${delimeter}${packetData.color}${endDelimeter}".toByteArray()
    }
}