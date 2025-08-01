package org.example.omok.server.packet

import org.example.omok.server.packet.data.CoordinatePacketData

class CoordinatePacket(
    override val packetData: CoordinatePacketData
) : Packet(packetData) {
    override fun toString(): String {
        return "${startDelimeter}${PacketType.COORDINATE.name}${delimeter}${packetData.x}${delimeter}${packetData.y}${endDelimeter}"
    }

    override fun serialize(): ByteArray {
        return toString().toByteArray()
    }
}