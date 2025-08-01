package org.example.omok.server.packet.data

class ExitPacketData(
    override val payload: List<String>,
) : PacketData {
    val roomId: String = payload[0]
}