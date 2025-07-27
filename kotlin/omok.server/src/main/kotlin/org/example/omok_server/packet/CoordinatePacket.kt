package org.example.omok_server.packet

import org.example.omok_server.packet.data.CoordinatePacketData

class CoordinatePacket(
    override val packetData: CoordinatePacketData
) : Packet(packetData) {
    override fun serialize(): ByteArray {
        return "${startDelimeter}${PacketType.COORDINATE.name}${delimeter}${packetData.x}${delimeter}${packetData.y}${endDelimeter}".toByteArray()
    }
}