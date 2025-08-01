package org.example.omok.server.listeners

import org.example.omok.server.packet.example.deserializeMultiPropertyPacket
import org.example.omok.server.packet.example.serializeMultiPropertyPacket
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
                val mpPacket = deserializeMultiPropertyPacket(receivedMessageBytes)
                println("Received message: ${mpPacket.message}, number: ${mpPacket.number}, flag: ${mpPacket.flag}")

                val convertedMpBytes = serializeMultiPropertyPacket(mpPacket)
                outputStream.write(convertedMpBytes)
                println("Sent bytes: ${convertedMpBytes.joinToString(", ")}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream.close()
            outputStream.close()
        }
    }
}
