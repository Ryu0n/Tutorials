package org.example.omok.netty.server.packets

import org.example.omok.netty.server.packets.data.MessagePacketData

class MessagePacket(
    override val packetData: MessagePacketData
) : Packet(packetData) {
    override fun toString(): String {
        return "${startDelimeter}${PacketType.MESSAGE.name}${delimeter}${packetData.message}${endDelimeter}"
    }

    override fun serialize(): ByteArray {
        return toString().toByteArray()
    }
}