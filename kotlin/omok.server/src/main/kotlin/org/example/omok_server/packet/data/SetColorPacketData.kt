package org.example.omok_server.packet.data

class SetColorPacketData(
    override val payload: List<String>,
) : PacketData {
    val color: String = payload[0]
}