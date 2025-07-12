package org.example.omok_server.listeners

import org.example.omok_server.packet.Packet
import org.example.omok_server.packet.deserialize
import org.example.omok_server.packet.serialize
import java.net.Socket


class ListenRunnable(private val socket: Socket) : Runnable {
    override fun run() {
        val buffer = ByteArray(32)
        val inputStream = socket.inputStream
        val outputStream = socket.outputStream

        try {
            println("Listening on ${socket.inetAddress.hostAddress}, port: ${socket.port}")

            while (true) {
                val bytesRead = inputStream.read(buffer)
                if (bytesRead == -1) {
                    println("No more data to read.")
                    break
                }

                val receivedMessageBytes = buffer.copyOf(bytesRead)
                val packet = deserialize(receivedMessageBytes)
                println("Received message: ${packet.message}")

                val convertedMessageBytes = serialize(packet)
                outputStream.write(convertedMessageBytes)
                println("Sent bytes: ${convertedMessageBytes.joinToString(", ")}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream.close()
            outputStream.close()
        }
    }
}
