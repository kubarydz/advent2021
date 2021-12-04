fun main() {

    fun part1(input: List<String>): Int {
        val (drawNumbers, boards) = separateBoards(input)
        val draws = initializeDrawsMap()
        for (board in boards) board.setDraws(draws)
        return getWinnerScore(boards, drawNumbers, draws)
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
//    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

fun separateBoards(input: List<String>): Pair<List<String>, List<Board>> {
    val boards = input.asSequence().drop(1).filter { a -> a != "" }.chunked(5).map { chunk ->
        chunk.map { v ->
            v.split(' ').filter { a -> a.isNotBlank() }
        }
    }.map { board -> Board(board) }.toList()
    return Pair(input.first().split(','), boards)
}

fun initializeDrawsMap(): HashMap<String, Boolean> {
    val draws = HashMap<String, Boolean>()
    for (i in 0..99) {
        draws[i.toString()] = false
    }
    return draws
}

fun getWinnerScore(boards: List<Board>, drawNumbers: List<String>, draws: HashMap<String, Boolean>): Int {
    for (draw in drawNumbers) {
        draws[draw] = true
        if (boards.any { board -> board.isWinner() }) {
            return boards.first { board -> board.isWinner() }.getScore(draw)
        }
    }
    return 0
}

class Board(private val board: List<List<String>>) {
    private var draws = HashMap<String, Boolean>()

    fun setDraws(draws: HashMap<String, Boolean>) {
        this.draws = draws
    }

    fun isWinner(): Boolean {
        for (row in board) {
            if (row.all { field -> draws.getOrDefault(field, false) }) return true
        }

        board.indices.forEach{ i ->
            if (board.all { row -> draws.getOrDefault(row[i], false) }) return true
        }
        return false
    }

    fun getScore(latestDraw: String): Int {
        var sumOfUnmarked = 0
        board.forEach { row ->
            row.forEach { field ->
                if (!draws.getOrDefault(field, true)) sumOfUnmarked += field.toInt()
            }
        }
        return sumOfUnmarked * latestDraw.toInt()
    }

    override fun toString(): String {
        return board.toString()
    }
}
