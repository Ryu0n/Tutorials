package org.example.omok.netty.server.packets.data

class MatchResultPacketData(
    override val payload: List<String>,
) : PacketData {
    val winPlayerId: String = payload[0]
}