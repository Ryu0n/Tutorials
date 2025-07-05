package org.example.echo_server

import kotlinx.coroutines.runBlocking
import org.example.echo_server.server.CoroutineEchoServer
import org.example.echo_server.server.FixedThreadPoolEchoServer
import org.example.echo_server.server.MultiThreadEchoServer
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application(
    private val multiThreadEchoServer: MultiThreadEchoServer,
    private val fixedThreadPoolEchoServer: FixedThreadPoolEchoServer,
    private val coroutineEchoServer: CoroutineEchoServer,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        println("Starting Echo server...")
//        multiThreadEchoServer.start()
//        fixedThreadPoolEchoServer.start()
        runBlocking {
            coroutineEchoServer.start()
        }
        println("Echo server started.")
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
