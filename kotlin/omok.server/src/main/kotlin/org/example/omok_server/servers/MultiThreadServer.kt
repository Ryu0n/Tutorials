package org.example.omok_server.servers

import org.example.omok_server.listeners.ListenRunnable
import org.springframework.stereotype.Component
import java.net.InetAddress
import java.net.ServerSocket


@Component
class MultiThreadServer {
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