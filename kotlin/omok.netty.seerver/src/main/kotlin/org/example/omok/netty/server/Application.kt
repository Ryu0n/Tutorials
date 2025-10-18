package org.example.omok.netty.server

import org.example.omok.netty.server.servers.NettyOmokServer
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application(
    val omokServer: NettyOmokServer,
): CommandLineRunner {
    override fun run(vararg args: String?) {
        omokServer.start()
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
