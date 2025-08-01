package org.example.omok_server.packet

import org.example.omok_server.packet.data.MessagePacketData

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