package org.example.echo_server.server


import org.springframework.stereotype.Component
import java.net.InetAddress
import java.net.ServerSocket
import java.util.concurrent.Executors


@Component
class FixedThreadPoolEchoServer {
    private val serverSocket = ServerSocket(
        9091,
        50, // Maximum number of queued connections
        InetAddress.getByName("localhost"),
    )
    val executor = Executors.newFixedThreadPool(3)
//    val executor = Executors.newCachedThreadPool()

    fun start() {
        while (true) {
            val socket = serverSocket.accept()
            executor.execute(ListenRunnable(socket))
            println("Accepted connection from ${socket.inetAddress.hostAddress}, port: ${socket.port}")
        }
    }
}
