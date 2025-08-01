package org.example.omok_server.packet

import org.example.omok_server.packet.data.SetPlayerIdPacketData

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