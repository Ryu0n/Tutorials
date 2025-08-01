package org.example.omok.server.packet.data

class SetPlayerIdPacketData(
    override val payload: List<String>,
) : PacketData {
    val playerId: String = payload[0]
}