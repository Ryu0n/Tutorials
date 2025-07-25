package omok.view

import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import omok.model.Game

class BoardView(private val game: Game) : Pane() {
    private val canvas = Canvas(600.0, 600.0)
    private val gc = canvas.graphicsContext2D

    init {
        children.add(canvas)
        draw()

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED) {
            val x = (it.x / (canvas.width / 19)).toInt()
            val y = (it.y / (canvas.height / 19)).toInt()
            if (game.placeStone(x, y)) {
                draw()
            }
        }
    }

    fun draw() {
        gc.clearRect(0.0, 0.0, canvas.width, canvas.height)

        // Draw board
        gc.stroke = Color.BLACK
        for (i in 0..18) {
            gc.strokeLine(i * (canvas.width / 19) + 20, 20.0, i * (canvas.width / 19) + 20, canvas.height - 20)
            gc.strokeLine(20.0, i * (canvas.height / 19) + 20, canvas.width - 20, i * (canvas.height / 19) + 20)
        }

        // Draw stones
        for (y in 0..18) {
            for (x in 0..18) {
                if (game.board[y][x] != 0) {
                    gc.fill = if (game.board[y][x] == 1) Color.BLACK else Color.WHITE
                    gc.fillOval(x * (canvas.width / 19) + 5, y * (canvas.height / 19) + 5, 30.0, 30.0)
                }
            }
        }
    }
}
