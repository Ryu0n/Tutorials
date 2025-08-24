package org.example.omok.server.packets.data

class SetRoomPacketData(
    override val payload: List<String>,
) : PacketData {
    val roomName: String = payload[0]
}