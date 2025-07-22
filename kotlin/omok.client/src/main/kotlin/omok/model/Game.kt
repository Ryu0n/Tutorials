package omok.model

class Game {
    val board = Array(19) { IntArray(19) }
    var currentPlayer = 1
    var onGameEnd: ((Int) -> Unit)? = null

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
