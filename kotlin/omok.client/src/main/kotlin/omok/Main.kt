package omok

import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.Stage
import omok.model.GameClient
import omok.view.BoardView

class Main : Application() {
    override fun start(primaryStage: Stage) {
        val chatArea = TextArea()
        chatArea.isEditable = false
        chatArea.prefRowCount = 5
        chatArea.prefWidth = 100.0 // 원하는 너비로 조정

        val client = GameClient(chatArea)
        val boardView = BoardView(client)

        val attendGameButton = Button("Attend to Game Room")
        attendGameButton.setOnAction {
            client.reset()
            boardView.draw()
            client.attendGame()
        }

        val exitGameButton = Button("Exit from Game Room")
        exitGameButton.setOnAction {
            client.reset()
            boardView.draw()
            client.exitGame()
        }

        val root = BorderPane()
        root.center = boardView

        val buttonBox = HBox(10.0, attendGameButton, exitGameButton)
        root.bottom = buttonBox

        val chatInput = TextField()
        chatInput.promptText = "Please enter your message"

        val sendButton = Button("Send")
        sendButton.setOnAction {
            val message = chatInput.text
            if (message.isNotBlank()) {
                chatInput.clear()
                client.sendMessage(message)
                val messagePacket = client.receivePacket()
                val messagePayload = client.getPayloadFromPacket(messagePacket)
                chatArea.appendText("${messagePayload[0]}\n")
            }
        }

        val chatSendBox = HBox(5.0, chatInput, sendButton)
        chatSendBox.alignment = Pos.BOTTOM_CENTER

        val chatBox = VBox(10.0, chatArea, chatSendBox)
        VBox.setVgrow(chatArea, Priority.ALWAYS) // chatArea가 남는 공간을 모두 차지
        chatBox.prefWidth = 500.0 // chatBox 전체 너비 지정

        root.right = chatBox

        val scene = Scene(root)

        primaryStage.title = "Omok"
        primaryStage.scene = scene
        primaryStage.show()

        client.onGameEnd = {
            val alert = Alert(Alert.AlertType.INFORMATION)
            alert.title = "Game Over"
            alert.headerText = null
            alert.contentText = "Player ${it} wins!"
            alert.showAndWait()
        }
    }
}

fun main() {
    Application.launch(Main::class.java)
}
