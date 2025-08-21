package org.example.omok.server.packets.data

class AttendancePacketData(
    override val payload: List<String>,
) : PacketData {
    val roomId: String = payload[0]
}