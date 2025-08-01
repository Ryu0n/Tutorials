package org.example.omok.server.packet.data

class SetColorPacketData(
    override val payload: List<String>,
) : PacketData {
    val color: String = payload[0]
}