package org.example.omok.server.packets.data

class ExitPacketData(
    override val payload: List<String>,
) : PacketData {
    val roomId: String = payload[0]
}