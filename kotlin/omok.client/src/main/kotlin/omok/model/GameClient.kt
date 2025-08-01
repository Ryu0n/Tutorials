package omok.model

import javafx.scene.control.Alert
import javafx.scene.control.TextArea
import java.net.Socket

class GameClient(
    private val chatArea: TextArea,
    private val host: String = "localhost",
    private val port: Int = 9090,
) {
    val socket = Socket(host, port)
    val buffer = ByteArray(128)
    val inputStream = socket.inputStream
    val outputStream = socket.outputStream

    var playerId: String
    lateinit var playerColor: String


    init {
        val joinMessage = receivePacket() // Read initial handshake message (e.g. Notice from server)
        val playerIdPacket = receivePacket()
        playerId = getPayloadFromPacket(playerIdPacket)[0]
    }

    var currentPlayer = 1

    val board = Array(19) { IntArray(19) }
    var onGameEnd: ((Int) -> Unit)? = null

    fun getPayloadFromPacket(packet: String): List<String> {
        return packet.removeSurrounding("<", ">").split(":").drop(1)
    }

    fun receivePacket(): String {
        val bytesRead = inputStream.read(buffer)
        val bytes = buffer.copyOf(bytesRead)
        return String(bytes)
    }

    fun sendPacket(data: String) {
        outputStream.write(data.toByteArray())
        outputStream.flush()
    }

    fun attendGame() {
        val packet = "<ATTENDANCE:roomId>"
        sendPacket(packet)
        val noticePacket = receivePacket()
        val noticePayload = getPayloadFromPacket(noticePacket)
        if (noticePayload[0] == "Failed") {
            val alert = Alert(Alert.AlertType.ERROR)
            alert.title = "Error"
            alert.headerText = "Failed to join game room"
            alert.contentText = noticePayload[1]
            alert.showAndWait()
            return
        }
        chatArea.appendText(noticePayload[1] + "\n")

        val setColorPacket = receivePacket()
        val setColorPayload = getPayloadFromPacket(setColorPacket)
        playerColor = setColorPayload[0]
        chatArea.appendText("You are playing as $playerColor.\n")
    }

    fun exitGame() {
        val packet = "<EXIT:roomId>"
        sendPacket(packet)
        val noticePacket = receivePacket()
        val noticePayload = getPayloadFromPacket(noticePacket)
        if (noticePayload[0] == "Failed") {
            val alert = Alert(Alert.AlertType.ERROR)
            alert.title = "Error"
            alert.headerText = "Failed to exit game room"
            alert.contentText = noticePayload[1]
            alert.showAndWait()
            return
        }
        chatArea.appendText(noticePayload[1] + "\n")
    }

    fun sendMessage(message: String) {
        val packet = "<MESSAGE:${playerId}; ${message}>"
        sendPacket(packet)
    }

    fun placeStone(x: Int, y: Int): Boolean {
        if (board[y][x] == 0) {
            board[y][x] = currentPlayer
            if (checkWin(x, y)) {
                onGameEnd?.invoke(currentPlayer)
            } else {
                currentPlayer = if (currentPlayer == 1) 2 else 1
            }
            return true
        }
        return false
    }

    fun reset() {
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = 0
            }
        }
        currentPlayer = 1
    }

    private fun checkWin(x: Int, y: Int): Boolean {
        val directions = listOf(
            Pair(1, 0), // Horizontal
            Pair(0, 1), // Vertical
            Pair(1, 1), // Diagonal
            Pair(1, -1) // Anti-diagonal
        )

        for (direction in directions) {
            var count = 1
            for (i in 1..4) {
                val newX = x + i * direction.first
                val newY = y + i * direction.second
                if (newX in 0..18 && newY in 0..18 && board[newY][newX] == currentPlayer) {
                    count++
                } else {
                    break
                }
            }
            for (i in 1..4) {
                val newX = x - i * direction.first
                val newY = y - i * direction.second
                if (newX in 0..18 && newY in 0..18 && board[newY][newX] == currentPlayer) {
                    count++
                } else {
                    break
                }
            }
            if (count >= 5) {
                return true
            }
        }
        return false
    }
}
