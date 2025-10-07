package org.example.omok.netty.server.packets.example

fun serializeSinglePropertyPacket(SinglePropertyPacket: SinglePropertyPacket): ByteArray {
    // val header = byteArrayOf(0xAA.toByte(), 0xBB.toByte())
    // val footer = byteArrayOf(0xCC.toByte(), 0xDD.toByte())
    val body = SinglePropertyPacket.message.toByteArray(Charsets.UTF_8)
    // return header + body + footer
    return body
}

fun deserializeSinglePropertyPacket(bytes: ByteArray): SinglePropertyPacket {
    val body = bytes.toString(Charsets.UTF_8)
    return SinglePropertyPacket(body)
}

data class SinglePropertyPacket (
    var message: String
)
