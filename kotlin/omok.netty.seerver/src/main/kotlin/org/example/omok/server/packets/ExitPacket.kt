package org.example.omok.netty.server.packets

import org.example.omok.netty.server.packets.data.ExitPacketData

class ExitPacket(
    override val packetData: ExitPacketData,
) : Packet(packetData) {
    override fun toString(): String {
        return "${startDelimeter}${PacketType.EXIT.name}${delimeter}${packetData.roomId}${endDelimeter}"
    }

    override fun serialize(): ByteArray {
        return toString().toByteArray()
    }
}