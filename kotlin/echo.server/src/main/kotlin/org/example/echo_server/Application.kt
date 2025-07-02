package org.example.echo_server

import org.example.echo_server.server.EchoServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
    println("Echo Server is running...")
    EchoServer().start()
}
