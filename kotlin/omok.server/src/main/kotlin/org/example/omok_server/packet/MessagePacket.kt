package org.example.omok_server.packet

import org.example.omok_server.packet.data.MessagePacketData

class MessagePacket(
    override val packetData: MessagePacketData
) : Packet(packetData) {
    override fun serialize(): ByteArray {
        return "${startDelimeter}${PacketType.MESSAGE.name}${delimeter}${packetData.message}${endDelimeter}".toByteArray()
    }
}