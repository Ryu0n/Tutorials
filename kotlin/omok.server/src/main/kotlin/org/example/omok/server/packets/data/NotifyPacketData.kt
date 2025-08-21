package org.example.omok.server.packets.data

class NotifyPacketData(
    override val payload: List<String>,
): PacketData {
    val status: String = payload[0]
    val notice: String = payload[1]
}