package omok

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import omok.model.Game
import omok.view.BoardView

class Main : Application() {
    override fun start(primaryStage: Stage) {
        val game = Game()
        val boardView = BoardView(game)

        val newGameButton = Button("New Game")
        newGameButton.setOnAction {
            game.reset()
            boardView.draw()
        }

        val root = BorderPane()
        root.center = boardView
        root.bottom = newGameButton

        val scene = Scene(root)

        primaryStage.title = "Omok"
        primaryStage.scene = scene
        primaryStage.show()

        game.onGameEnd = {
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
