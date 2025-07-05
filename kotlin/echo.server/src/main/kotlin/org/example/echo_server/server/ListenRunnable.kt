package org.example.echo_server.server

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
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
