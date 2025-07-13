package org.example.client

import org.example.packet.MultiPropertyPacket
import org.example.packet.SimplePacket
import org.example.packet.deserialize
import org.example.packet.deserializeMultiPropertyPacket
import org.example.packet.serialize
import org.example.packet.serializeMultiPropertyPacket
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class MultiPropertyPacker (
    var host: String = "localhost",
    var port: Int = 9090,
) {
    val buffer = ByteArray(32)
    val socket = Socket(host, port)
    val outputStream: OutputStream? = socket.outputStream
    val inputStream: InputStream? = socket.inputStream

    fun sendMessageAndAwaitResponse(packet: MultiPropertyPacket) {
        val bytes = serializeMultiPropertyPacket(packet)
        outputStream?.write(bytes)
        println("Sent bytes: ${bytes.joinToString(", ")}")

        val byteRead = inputStream?.read(buffer)
        if (byteRead == -1) {
            println("No more data to read.")
            return
        }
        val convertedMessageByte = buffer.copyOf(byteRead ?: 0)
        val deserializedPacket = deserializeMultiPropertyPacket(convertedMessageByte)
    }
}
