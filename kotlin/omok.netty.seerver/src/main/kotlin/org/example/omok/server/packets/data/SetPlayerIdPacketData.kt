package org.example.omok.netty.server.packets.data

class SetPlayerIdPacketData(
    override val payload: List<String>,
) : PacketData {
    val playerId: String = payload[0]
}