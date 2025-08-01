package org.example.omok_server.packet

import org.example.omok_server.packet.data.SetColorPacketData

class SetColorPacket(
    override val packetData: SetColorPacketData,
) : Packet(packetData) {
    override fun toString(): String {
        return "${startDelimeter}${PacketType.SET_COLOR.name}${delimeter}${packetData.color}${endDelimeter}"
    }

    override  fun serialize(): ByteArray {
        return toString().toByteArray()
    }
}