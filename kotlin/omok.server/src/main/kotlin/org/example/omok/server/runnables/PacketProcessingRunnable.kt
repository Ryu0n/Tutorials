package org.example.omok.server.runnables

import reactor.core.publisher.Flux

class PacketProcessingRunnable(
    val messageQueue: Flux<String>,
) : Runnable {
    override fun run() {
        messageQueue
            .doOnNext({ message ->
                println(
                    "'" + message + "' received by " + Thread.currentThread().getName()
                )
            })
            .subscribe()
        while (true) {
            println("Hello World")
            Thread.sleep(3000)
        }
    }
}