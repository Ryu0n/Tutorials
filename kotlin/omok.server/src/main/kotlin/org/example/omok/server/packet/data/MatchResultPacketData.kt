package org.example.omok.server.packet.data

class MatchResultPacketData(
    override val payload: List<String>,
) : PacketData {
    val winPlayerId: String = payload[0]
}