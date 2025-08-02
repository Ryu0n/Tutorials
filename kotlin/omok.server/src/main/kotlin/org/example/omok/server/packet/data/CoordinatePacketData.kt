package org.example.omok.server.packet.data

class CoordinatePacketData(
    override val payload: List<String>,
) : PacketData {
    val x = payload[0]
    val y = payload[1]
    val playerColor = payload[2]
}