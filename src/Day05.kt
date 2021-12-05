fun main() {

    fun part1(input: List<String>, diagramSize: Int): Int {
        val diagram = Array(diagramSize) { IntArray(diagramSize) }
        loadDataIntoStraightDiagram(diagram, input)
        return calculateDiagramValue(diagram)
    }

    fun part2(input: List<String>, diagramSize: Int): Int {
        val diagram = Array(diagramSize) { IntArray(diagramSize) }
        loadDataIntoDiagonalDiagram(diagram, input)
        return calculateDiagramValue(diagram)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput, 10) == 5)
    check(part2(testInput, 10) == 12)

    val input = readInput("Day05")
    println(part1(input, 1000))
    println(part2(input, 1000))
}

private fun loadDataIntoStraightDiagram(diagram: Array<IntArray>, input: List<String>) {
    val readyData = splitData(input)
    readyData.forEach { data ->
        val linePoints = calculateStraightCloudLines(data)
        linePoints.forEach { (y, x) ->
            when {
                diagram[x][y] < 2 -> diagram[x][y]++
            }
        }
    }
}

private fun splitData(input: List<String>): List<List<String>> {
    return input.map { it.split(" -> ") }//.map { it.split(',') } }
}

private fun calculateStraightCloudLines(input: List<String>): List<Pair<Int, Int>> {
    val coordinates = input.flatMap { it.split(',') }.map { it.toInt() }
    return when {
        coordinates[0] == coordinates[2] -> {
            val rangeY = if (coordinates[1] > coordinates[3]) coordinates[3]..coordinates[1] else coordinates[1]..coordinates[3]
            val lines = listOf<Pair<Int, Int>>().toMutableList()
            for (i in rangeY) lines.add(Pair(coordinates[0], i))
            lines.toList()
        }
        coordinates[1] == coordinates[3] -> {
            val rangeX = if (coordinates[0] > coordinates[2]) coordinates[2]..coordinates[0] else coordinates[0]..coordinates[2]
            val lines = listOf<Pair<Int, Int>>().toMutableList()
            for (i in rangeX) lines.add(Pair(i, coordinates[1]))
            lines.toList()
        }
        else -> emptyList()
    }
}

private fun calculateDiagramValue(diagram: Array<IntArray>): Int {
    var sum = 0
    diagram.forEach { el -> sum += el.count { it == 2 } }
    return sum
}

private fun loadDataIntoDiagonalDiagram(diagram: Array<IntArray>, input: List<String>) {
    val readyData = splitData(input)
    readyData.forEach { data ->
        val linePoints = calculateDiagonalCloudLines(data)
        linePoints.forEach { (y, x) ->
            when {
                diagram[x][y] < 2 -> diagram[x][y]++
            }
        }
    }
}

private fun calculateDiagonalCloudLines(input: List<String>): List<Pair<Int, Int>> {
    val coordinates = input.flatMap { it.split(',') }.map { it.toInt() }
    if (!isDiagonal(coordinates))
        return calculateStraightCloudLines(input)
    val diagonalLength = kotlin.math.abs(coordinates[0] - coordinates[2])
    val stepX = calculateStepX(coordinates)
    val stepY = calculateStepY(coordinates)
    val diagonalLines = listOf<Pair<Int, Int>>().toMutableList()
    var xCoordinate = coordinates[0]
    var yCoordinate = coordinates[1]
    (0..diagonalLength).forEach { _ ->
        diagonalLines.add(Pair(xCoordinate, yCoordinate))
        xCoordinate += stepX
        yCoordinate += stepY
    }
    return diagonalLines
}

private fun calculateStepX(line: List<Int>): Int {
    val difference = line[2] - line[0]
    if (difference == 0) return 0
    return difference / kotlin.math.abs((difference))
}

private fun calculateStepY(line: List<Int>): Int {
    val difference = line[3] - line[1]
    if (difference == 0) return 0
    return difference / kotlin.math.abs((difference))
}

private fun isDiagonal(input: List<Int>): Boolean {
    return kotlin.math.abs(input[0] - input[2]) == kotlin.math.abs(input[1] - input[3])
}