package org.example.omok.server.packet

import org.example.omok.server.packet.data.MatchResultPacketData

class MatchResultPacket(
    override val packetData: MatchResultPacketData,
): Packet(packetData) {
    override fun toString(): String {
        return "${startDelimeter}${PacketType.MATCH_RESULT.name}${delimeter}${packetData.winPlayerId}${endDelimeter}"
    }

    override fun serialize(): ByteArray {
        return toString().toByteArray()
    }
}