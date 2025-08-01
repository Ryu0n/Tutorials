package org.example.omok_server.packet.data

class SetPlayerIdPacketData(
    override val payload: List<String>,
) : PacketData {
    val playerId: String = payload[0]
}