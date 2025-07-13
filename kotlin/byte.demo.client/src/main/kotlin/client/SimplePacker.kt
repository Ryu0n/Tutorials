package org.example.client

import org.example.packet.SimplePacket
import org.example.packet.deserialize
import org.example.packet.serialize
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class SimplePacker (
    var host: String = "localhost",
    var port: Int = 9090,
) {
    val buffer = ByteArray(32)
    val socket = Socket(host, port)
    val outputStream: OutputStream? = socket.outputStream
    val inputStream: InputStream? = socket.inputStream

    fun sendMessageAndAwaitResponse() {
        var packet = SimplePacket("Hello, Server!")
        val bytes = serialize(packet)
        outputStream?.write(bytes)
        println("Sent bytes: ${bytes.joinToString(", ")}")

        val byteRead = inputStream?.read(buffer)
        if (byteRead == -1) {
            println("No more data to read.")
            return
        }
        val convertedMessageByte = buffer.copyOf(byteRead?: 0)
        packet = deserialize(convertedMessageByte)
        println("Received message: ${packet.message}")
    }
}