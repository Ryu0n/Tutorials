package org.example.omok.netty.server

import org.example.omok.netty.server.servers.NettyExampleServer
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application(
    val discardServer: NettyExampleServer,
): CommandLineRunner {
    override fun run(vararg args: String?) {
        discardServer.start()
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
