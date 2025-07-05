package org.example.echo_server.server

import org.springframework.stereotype.Component
import java.net.InetAddress
import java.net.ServerSocket


@Component
class MultiThreadEchoServer {
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