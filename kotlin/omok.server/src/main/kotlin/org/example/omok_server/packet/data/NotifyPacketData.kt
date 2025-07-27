package org.example.omok_server.packet.data

class NotifyPacketData(
    override val payload: List<String>,
): PacketData {
    val notice: String = payload[0]
}