package org.example.packet



fun serialize(packet: Packet): ByteArray {
    val body = packet.message.toByteArray(Charsets.UTF_8)
    return body
}

fun deserialize(bytes: ByteArray): Packet {
    val body = bytes.toString(Charsets.UTF_8)
    return Packet(body)
}

data class Packet (
    val message: String
)
