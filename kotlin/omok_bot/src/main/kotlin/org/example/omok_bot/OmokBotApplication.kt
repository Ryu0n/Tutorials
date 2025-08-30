package org.example.omok_bot

import org.example.omok_bot.clients.MultiThreadClient
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OmokBotApplication(
    val multiThreadClient: MultiThreadClient,
): CommandLineRunner {
    override fun run(vararg args: String?) {
        println("Starting Omok bot...")
        multiThreadClient.start(numThreads = 1000)
    }
}

fun main(args: Array<String>) {
    runApplication<OmokBotApplication>(*args)
}
