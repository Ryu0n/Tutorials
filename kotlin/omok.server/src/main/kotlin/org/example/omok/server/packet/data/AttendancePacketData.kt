package org.example.omok.server.packet.data

class AttendancePacketData(
    override val payload: List<String>,
) : PacketData {
    val roomId: String = payload[0]
}