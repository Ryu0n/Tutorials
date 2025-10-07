package org.example.omok.netty.server.packets

import org.example.omok.netty.server.packets.data.SetColorPacketData

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