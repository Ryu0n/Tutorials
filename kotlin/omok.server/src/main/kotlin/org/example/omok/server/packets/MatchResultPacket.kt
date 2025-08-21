package org.example.omok.server.packets

import org.example.omok.server.packets.data.MatchResultPacketData

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