package org.example.echo_server

import org.example.echo_server.server.EchoServer
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application(
    private val echoServer: EchoServer
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        println("Starting Echo server...")
        echoServer.start()
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
