package org.example.omok.server.packets.data

class CoordinatePacketData(
    override val payload: List<String>,
) : PacketData {
    val x = payload[0]
    val y = payload[1]
    val playerColor = payload[2] // 1 for black, 2 for white
}