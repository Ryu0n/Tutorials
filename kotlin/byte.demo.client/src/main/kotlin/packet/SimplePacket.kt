package org.example.packet



fun serialize(packet: SimplePacket): ByteArray {
    val body = packet.message.toByteArray(Charsets.UTF_8)
    return body
}

fun deserialize(bytes: ByteArray): SimplePacket {
    val body = bytes.toString(Charsets.UTF_8)
    return SimplePacket(body)
}

data class SimplePacket (
    val message: String
)
