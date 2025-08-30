package org.example.omok_bot.runnables

import java.net.Socket
import java.nio.charset.Charset

class OmokLoadRunnable(
    private val host: String = "localhost",
    private val port: Int = 9090,
): Runnable {
    private val socket = Socket(host, port)
    private val inputStream = socket.inputStream
    private val outputStream = socket.outputStream
    private val buffer = StringBuilder()

    var playerId: String? = null
    var roomName: String? = null
    lateinit var playerColor: String

    private fun extractNextPacketFromBuffer(): String? {
        val startIndex = buffer.indexOf("<")
        val endIndex = buffer.indexOf(">")
        return if (startIndex != -1 && endIndex > startIndex) {
            val packet = buffer.substring(startIndex, endIndex + 1)
            buffer.delete(0, endIndex + 1)
            packet
        } else {
            if (endIndex < startIndex && endIndex != -1) {
                buffer.delete(0, startIndex)
            }
            null
        }
    }

    private fun processPacket(packet: String) {
        val command = packet.substringAfter("<").substringBefore(":")
        val payload = packet.removeSurrounding("<", ">").split(":").drop(1)

        when (command) {
            "SET_PLAYER_ID" -> {
                playerId = payload[0]
            }
            "SET_ROOM" -> {
                roomName = payload[0]
            }
            "SET_COLOR" -> {
                playerColor = payload[0]
            }
            "COORDINATE" -> {
                val x = payload[0].toInt()
                val y = payload[1].toInt()
                val player = payload[2].toInt()
            }
            "MESSAGE" -> {
                val message = payload[0]
//                println(message)
            }
            "NOTIFY" -> {
                val status = payload[0]
                val message = payload[1]
//                println("Notification: [$status] $message")
            }
            "MATCH_RESULT" -> {
                val winnerId = payload[0]
            }
            else -> {
                println("Unknown command: $command with payload: $payload")
            }
        }
    }

    private fun startListening() {
        try {
            val tempBuffer = ByteArray(1024)
            while (socket.isConnected) {
                val bytesRead = inputStream.read(tempBuffer)
                if (bytesRead == -1) break

                buffer.append(String(tempBuffer, 0, bytesRead, Charset.defaultCharset()))

                while (true) {
                    val packet = extractNextPacketFromBuffer() ?: break
                    processPacket(packet)
                }
            }
        } catch (e: Exception) {
            println("Error in OmokLoadRunnable: ${e.message}\n${e.stackTraceToString()}")
        } finally {
            socket.close()
        }
    }
    fun sendPacket(data: String) {
        outputStream.write(data.toByteArray(Charset.defaultCharset()))
        outputStream.flush()
    }

    fun attendGame() {
        sendPacket("<ATTENDANCE:roomId>")
    }

    fun exitGame() {
        sendPacket("<EXIT:roomId>")
    }

    fun sendMessage(message: String) {
        playerId?.let { id ->
            val packet = "<MESSAGE:[${id}] ${message}>"
            sendPacket(packet)
        }
    }

    fun placeStone(x: Int, y: Int): Boolean {
        var pc = "1"
        if (playerColor == "white") {
            pc = "2"
        }
        sendPacket("<COORDINATE:$x:$y:$pc>")
        return true
    }

    override fun run() {
        println("OmokLoadRunnable ${playerId} is running")
        startListening()
        println("OmokLoadRunnable ${playerId} has finished loading")
    }
}