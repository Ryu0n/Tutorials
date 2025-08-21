package org.example.omok.server.packets

import org.example.omok.server.packets.data.SetPlayerIdPacketData

class SetPlayerIdPacket(
    override val packetData: SetPlayerIdPacketData,
) : Packet(packetData) {
    override fun toString(): String {
        return "${startDelimeter}${PacketType.SET_PLAYER_ID.name}${delimeter}${packetData.playerId}${endDelimeter}"
    }

    override  fun serialize(): ByteArray {
        return toString().toByteArray()
    }
}