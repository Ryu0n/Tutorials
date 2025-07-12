package org.example.omok_server.listeners

import java.net.Socket


class ListenRunnable(private val socket: Socket) : Runnable {
    override fun run() {
        val inputStream = socket.inputStream
        val outputStream = socket.outputStream

        val buffer: ByteArray = ByteArray(4)
        var bytesRead: Int = 0

        try {
            println("Listening on ${socket.inetAddress.hostAddress}, port: ${socket.port}")

            while (bytesRead != -1) {
                bytesRead = inputStream.read(buffer)
                val receivedMessage = String(buffer, 0, bytesRead)
                println("Received: $receivedMessage")
                outputStream.write(buffer, 0, bytesRead)
                println("Buffer: ${buffer.joinToString(", ")}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream.close()
            outputStream.close()
        }
    }
}
