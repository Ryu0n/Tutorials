package org.example.omok.netty.server.packets

import org.example.omok.netty.server.packets.data.PacketData

abstract class Packet (
    open val packetData: PacketData
) {
    val startDelimeter: String = "<"
    val endDelimeter: String = ">"
    val delimeter: String = ":"

    abstract fun serialize(): ByteArray
}