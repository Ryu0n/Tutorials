package org.example.omok.server.packet

import org.example.omok.server.packet.data.PacketData

abstract class Packet (
    open val packetData: PacketData
) {
    val startDelimeter: String = "<"
    val endDelimeter: String = ">"
    val delimeter: String = ":"

    abstract fun serialize(): ByteArray
}