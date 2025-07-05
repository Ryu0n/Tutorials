package org.example.echo_server.server

import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket


class ListenRunnable(private val socket: Socket) : Runnable {
    override fun run() {
        val input: BufferedReader? = null
        val output: PrintWriter? = null

        try {
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))
            val output = PrintWriter(socket.getOutputStream(), true)

            println("Listening on ${socket.inetAddress.hostAddress}, port: ${socket.port}")

            while (true) {
                val message = input.readLine() ?: break
                println("Received: $message")
                output.println("Echo: $message")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            input?.close()
            output?.close()
        }
    }
}

@Component
class EchoServer {
    private val serverSocket = ServerSocket(
        9090,
        50, // Maximum number of queued connections
        InetAddress.getByName("localhost"),
    )

    fun start() {
        while (true) {
            val socket = serverSocket.accept()
            Thread(ListenRunnable(socket)).start()
        }
    }
}