package org.example.omok.server.packets

import org.example.omok.server.packets.data.SetRoomPacketData

class SetRoomPacket(
    override val packetData: SetRoomPacketData,
) : Packet(packetData) {
    override fun toString(): String {
        return "${startDelimeter}${PacketType.SET_ROOM.name}${delimeter}${packetData.roomName}${endDelimeter}"
    }

    override  fun serialize(): ByteArray {
        return toString().toByteArray()
    }
}