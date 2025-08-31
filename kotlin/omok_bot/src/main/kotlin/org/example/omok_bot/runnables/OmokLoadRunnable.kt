package org.example.omok_bot.runnables

import java.net.Socket
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

class OmokLoadRunnable(
    private val host: String = "localhost",
    private val port: Int = 9090,
): Runnable {
    private val socket = Socket(host, port)
    private val inputStream = socket.inputStream
    private val outputStream = socket.outputStream
    private val buffer = StringBuilder()

    private val lock = ReentrantLock()
    private val roomChanged = lock.newCondition()
    private val colorSet = lock.newCondition()

    var playerId: String? = null
    var roomName: String? = null
    var playerColor: String? = null

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
                lock.withLock {
                    roomName = payload[0]
                    if (roomName == "Waiting Room") {
                        playerColor = null
                    }
                    roomChanged.signalAll()
                }
            }
            "SET_COLOR" -> {
                lock.withLock {
                    playerColor = payload[0]
                    colorSet.signalAll()
                }
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
        println("OmokLoadRunnable $playerId Sending packet: $data")
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
        thread(start = true) {
            startListening()
        }
        Thread.sleep(1000)
        try {
            while (socket.isConnected) {
                println("OmokLoadRunnable $playerId is running (current room: $roomName)")
                val colorAssigned = lock.withLock {
                    attendGame()
                    if (colorSet.await(5, TimeUnit.SECONDS)) {
                        println("Player $playerId assigned color: $playerColor")
                        true
                    } else {
                        println("Player $playerId waiting for color assignment timed out")
                        false
                    }
                }
                if (!colorAssigned) {
                    continue
                }
                println("OmokLoadRunnable $playerId is running (current room: $roomName, color: $playerColor)")
                for (num in 1..5) {
                    val x = (0..18).random()
                    val y = (0..18).random()
                    placeStone(x, y)
                    Thread.sleep(500)
                }
                lock.withLock {
                    exitGame()
                    if (roomChanged.await(5, TimeUnit.SECONDS) && roomName == "Waiting Room") {
                        println("Player $playerId returned to Waiting Room.")
                    } else {
                        println("Player $playerId failed to return to Waiting Room in time.")
                    }
                }
                Thread.sleep(1000) // 루프 간 간격
            }
        }catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        } finally {
            println("OmokLoadRunnable $playerId has finished loading")
        }
    }
}