package org.example.omok.netty.server.packets.data

class SetColorPacketData(
    override val payload: List<String>,
) : PacketData {
    val color: String = payload[0]
}