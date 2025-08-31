package org.example.omok_bot.clients

import org.example.omok_bot.runnables.OmokLoadRunnable
import org.springframework.stereotype.Component

@Component
class MultiThreadClient {
    fun start(
        numThreads: Int,
        connectionInterval: Long = 1000,
    ) {
        println("MultiThreadClient started")
        for (i in 1..numThreads) {
            println("MultiThreadClient $i")
            Thread(OmokLoadRunnable()).start()
            Thread.sleep(connectionInterval)
        }
    }
}