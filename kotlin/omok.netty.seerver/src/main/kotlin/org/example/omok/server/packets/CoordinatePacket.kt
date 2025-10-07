package org.example.omok.netty.server.packets

import org.example.omok.netty.server.packets.data.CoordinatePacketData

class CoordinatePacket(
    override val packetData: CoordinatePacketData
) : Packet(packetData) {
    override fun toString(): String {
        return "${startDelimeter}${PacketType.COORDINATE.name}${delimeter}${packetData.x}${delimeter}${packetData.y}${delimeter}${packetData.playerColor}${endDelimeter}"
    }

    override fun serialize(): ByteArray {
        return toString().toByteArray()
    }
}