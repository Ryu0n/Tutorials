package org.example.omok_server.packet

import org.example.omok_server.packet.data.NotifyPacketData

class NotifyPacket(
    override val packetData: NotifyPacketData,
) : Packet(
    packetData
) {
    override fun serialize(): ByteArray {
        return "${startDelimeter}${PacketType.NOTIFY.name}${delimeter}${packetData.notice}${endDelimeter}".toByteArray()
    }
}