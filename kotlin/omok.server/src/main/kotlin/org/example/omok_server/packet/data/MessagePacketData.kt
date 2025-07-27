package org.example.omok_server.packet.data

class MessagePacketData(
    override val payload: List<String>,
) : PacketData {
    val message: String = payload[0]
}