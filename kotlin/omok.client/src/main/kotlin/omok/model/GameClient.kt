package omok.model

import javafx.application.Platform
import javafx.scene.control.TextArea
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.Socket

class GameClient(
    private val chatArea: TextArea,
    private val host: String = "localhost",
    private val port: Int = 9090,
) {
    private val socket = Socket(host, port)
    private val inputStream = socket.inputStream
    private val outputStream = socket.outputStream

    lateinit var playerId: String
    lateinit var playerColor: String

    var onStonePlaced: ((Int, Int, Int) -> Unit)? = null
    var onGameEnd: ((Int) -> Unit)? = null
    var onMessageReceived: ((String) -> Unit)? = null

    init {
        // 초기 핸드셰이크 메시지는 동기적으로 처리
        val joinMessage = receivePacket() // 서버로부터의 공지 등
        val playerIdPacket = receivePacket()
        playerId = getPayloadFromPacket(playerIdPacket)[0]
    }

    fun startListening() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                while (true) {
                    val packet = receivePacket()
                    if (packet.isEmpty()) continue

                    val payload = getPayloadFromPacket(packet)
                    val command = packet.substringBefore(":").removePrefix("<")

                    Platform.runLater {
                        when (command) {
                            "SET_COLOR" -> {
                                playerColor = payload[0]
                                chatArea.appendText("You are playing as $playerColor.\n")
                            }
                            "COORDINATE" -> {
                                val x = payload[0].toInt()
                                val y = payload[1].toInt()
                                val player = payload[2].toInt()
                                onStonePlaced?.invoke(x, y, player)
                            }
                            "MESSAGE" -> {
                                onMessageReceived?.invoke(payload[0])
                            }
                            "GAME_END" -> {
                                onGameEnd?.invoke(payload[0].toInt())
                            }
                            "NOTIFY" -> {
                                chatArea.appendText("${payload[1]}\n")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Platform.runLater {
                    chatArea.appendText("Disconnected from server.\n")
                }
            }
        }
    }

    private fun getPayloadFromPacket(packet: String): List<String> {
        return packet.removeSurrounding("<", ">").split(":").drop(1)
    }

    private fun receivePacket(): String {
        val buffer = ByteArray(256)
        val bytesRead = inputStream.read(buffer)
        return if (bytesRead > 0) String(buffer, 0, bytesRead) else ""
    }

    fun sendPacket(data: String) {
        outputStream.write(data.toByteArray())
        outputStream.flush()
    }

    fun attendGame() {
        val packet = "<ATTENDANCE:roomId>"
        sendPacket(packet)
    }

    fun exitGame() {
        val packet = "<EXIT:roomId>"
        sendPacket(packet)
    }

    fun sendMessage(message: String) {
        val packet = "<MESSAGE:[${playerId}] ${message}>"
        sendPacket(packet)
    }

    fun placeStone(x: Int, y: Int) {
        var pc = "1"
        if (playerColor == "white") {
            pc = "2"
        }
        val packet = "<COORDINATE:$x:$y:$pc>"
        sendPacket(packet)
    }
}