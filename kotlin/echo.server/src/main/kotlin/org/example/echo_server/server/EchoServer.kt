package org.example.echo_server.server

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.ServerSocket

class EchoServer {
    private val serverSocket = ServerSocket(
        9090,
        50, // Maximum number of queued connections
        InetAddress.getByName("localhost"),
    )
    private val socket = serverSocket.accept()

    private val inputStream = socket.inputStream
    private val outputStream = socket.outputStream

//    private val input = BufferedInputStream(inputStream)
    private val input = BufferedReader(InputStreamReader(inputStream))
    private val output = PrintWriter(outputStream, true)

    fun start() {
        try {
            while (true) {
                input.readLine().let { byte ->
                    val message = byte.toString()
                    println("Received: $message")
                    output.println("Echo: $message")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            output.flush()
            input.close()
            output.close()
        }
    }
}