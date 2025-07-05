package org.example.echo_server.server

import org.springframework.stereotype.Component
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket


@Component
class CoroutineEchoServer {
    private val serverSocket = ServerSocket(
        9092,
        50,
        InetAddress.getByName("localhost"),
    )

//    suspend fun start() {
//        while (true) {
//            val socket = serverSocket.accept()
//            println("Accepted connection from ${socket.inetAddress.hostAddress}, port: ${socket.port}")
//            CoroutineScope(Dispatchers.IO).launch { handleClient(socket) }
//        }
//    }
    suspend fun start() = coroutineScope {
        while (true) {
            val socket = serverSocket.accept()
            println("Accepted connection from ${socket.inetAddress.hostAddress}, port: ${socket.port}")
            launch(Dispatchers.IO) { handleClient(socket) }
        }
    }


    private suspend fun handleClient(socket: Socket) {
        socket.use {
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))
            val output = PrintWriter(socket.getOutputStream(), true)
            println("Listening on ${socket.inetAddress.hostAddress}, port: ${socket.port}")

            while (true) {
                val message = input.readLine() ?: break
                println("Received: $message")
                output.println("Echo: $message")
            }
        }
    }
}
