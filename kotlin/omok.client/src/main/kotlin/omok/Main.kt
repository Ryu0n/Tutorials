package omok

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.stage.Stage
import omok.model.GameClient
import omok.view.BoardView

class Main : Application() {
    override fun start(primaryStage: Stage) {
        val client = GameClient()
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
