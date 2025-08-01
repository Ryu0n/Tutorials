package org.example.omok.server.packet.example

val MAX_MESSAGE_LENGTH = 20 // Maximum length of the message in bytes
val MAX_NUMBER_LENGTH = 4 // Maximum length of the number in bytes

fun serializeMultiPropertyPacket(packet: MultiPropertyPacket) : ByteArray {
    // Fixed size byte arrays for each property
    val messageBytes = ByteArray(MAX_MESSAGE_LENGTH)
    val numberBytes = ByteArray(MAX_NUMBER_LENGTH)
    val flagBytes = ByteArray(1)

    // Fill message's bytes and padding 0 with rest of bytes
    for (i in packet.message.indices) {
        messageBytes[i] = packet.message[i].code.toByte()
    }
    for (i in packet.message.length until messageBytes.size) {
        messageBytes[i] = 0 // Fill remaining bytes with 0
    }

    // shr : Shift Right operator
    // Save the number & split into 4 bytes
    numberBytes[0] = (packet.number shr 24).toByte()
    numberBytes[1] = (packet.number shr 16).toByte()
    numberBytes[2] = (packet.number shr 8).toByte()
    numberBytes[3] = packet.number.toByte()

    flagBytes[0] = if (packet.flag) 1 else 0

    val bytes = messageBytes + numberBytes + flagBytes

    println("Serialized message: ${packet.message}, number: ${packet.number}, flag: ${packet.flag}")
    println("Message bytes: ${messageBytes.joinToString(", ")}")
    println("Number bytes: ${numberBytes.joinToString(", ")}")
    println("Flag bytes: ${flagBytes.joinToString(", ")}")
    println("Serialized bytes: ${bytes.joinToString(", ")}")

    return bytes
}

fun deserializeMultiPropertyPacket(bytes: ByteArray) : MultiPropertyPacket {
    println("Serialized bytes: ${bytes.joinToString(", ")}")
    val messageBytes = bytes.sliceArray(0..(MAX_MESSAGE_LENGTH - 1)) // from 0 to 19
    val nextToMessage = messageBytes.size // 20
    val numberBytes = bytes.sliceArray(nextToMessage..(nextToMessage + MAX_NUMBER_LENGTH) - 1) // from 20 to 23
    val nextToNumber = nextToMessage + numberBytes.size // 20 + 4 = 24
    val flagBytes = bytes[nextToNumber]

    val message = messageBytes.map { it.toInt().toChar() }.joinToString("").trimEnd('\u0000')
    val number = (numberBytes[0].toInt() shl 24) or
            (numberBytes[1].toInt() shl 16) or
            (numberBytes[2].toInt() shl 8) or
            numberBytes[3].toInt()
    val flag = flagBytes == 1.toByte()
    val packet = MultiPropertyPacket(message, number, flag)
    println("Deserialized message: ${packet.message} number: ${packet.number}, flag: ${packet.flag}")
    return packet
}

class MultiPropertyPacket (
    val message: String,
    val number: Int,
    val flag: Boolean,
)