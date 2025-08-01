package org.example.omok.server.packet

import org.example.omok.server.packet.data.NotifyPacketData

class NotifyPacket(
    override val packetData: NotifyPacketData,
) : Packet(
    packetData
) {
    override fun toString(): String {
        return "${startDelimeter}${PacketType.NOTIFY.name}${delimeter}${packetData.status}${delimeter}${packetData.notice}${endDelimeter}"
    }

    override fun serialize(): ByteArray {
        return toString().toByteArray()
    }
}