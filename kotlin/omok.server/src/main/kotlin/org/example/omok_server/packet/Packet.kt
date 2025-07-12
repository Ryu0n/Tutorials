package org.example.omok_server.packet

fun serialize(packet: Packet): ByteArray {
    // val header = byteArrayOf(0xAA.toByte(), 0xBB.toByte())
    // val footer = byteArrayOf(0xCC.toByte(), 0xDD.toByte())
    val body = packet.message.toByteArray(Charsets.UTF_8)
    // return header + body + footer
    return body
}

fun deserialize(bytes: ByteArray): Packet {
    val body = bytes.toString(Charsets.UTF_8)
    return Packet(body)
}

data class Packet (
    var message: String
)
