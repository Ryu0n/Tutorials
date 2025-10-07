package org.example.omok.netty.server

import org.example.omok.netty.server.servers.MultiThreadServer
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application(
    val multiThreadServer: MultiThreadServer,
): CommandLineRunner {
    override fun run(vararg args: String?) {
        println("Starting Omok server...")
        multiThreadServer.start()
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
