fun main() {

    fun part1(input: List<String>): Int {
        return getLowestPoints(input).fold(0) { sum, el -> sum + el.value + 1 }
    }


    fun part2(input: List<String>): Int {
        val lowestPoints = getLowestPoints(input)
        val rows = input.map { it.toCharArray() }
        val maxColIndex = rows.first().size - 1
        val maxRowIndex = rows.size - 1
        val largestBasins = emptyList<Int>().toMutableList()
        lowestPoints.forEach { lowPoint ->
            val basin =
                calculateHigherAdjacent(rows, lowPoint, maxRowIndex, maxColIndex, emptyList<LowPoint>().toMutableList())
            if (largestBasins.size < 3) largestBasins.add(basin)
            else if (largestBasins.minOf { it } < basin){
                largestBasins.remove(largestBasins.minOf { it })
                largestBasins.add(basin)
            }
        }
        return largestBasins.fold(1) { sum, el -> sum * el }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

private data class LowPoint(val position: Pair<Int, Int>, val value: Int)

private fun getLowestPoints(input: List<String>): MutableList<LowPoint> {
    val rows = input.map { it.toCharArray() }
    val maxColIndex = rows.first().size - 1
    val maxRowIndex = rows.size - 1
    val lowestPoints = emptyList<LowPoint>().toMutableList()
    rows.iterator().withIndex().forEach { (rowIndex, rowValue) ->
        rowValue.iterator().withIndex().forEach { (colIndex, colValue) ->
            if ((colIndex == 0 || rowValue[colIndex - 1] > colValue)
                && (colIndex == maxColIndex || rowValue[colIndex + 1] > colValue)
                && (rowIndex == 0 || rows[rowIndex - 1][colIndex] > colValue)
                && (rowIndex == maxRowIndex || rows[rowIndex + 1][colIndex] > colValue)
            )
                lowestPoints.add(LowPoint(Pair(rowIndex, colIndex), colValue.digitToInt()))
        }
    }
    return lowestPoints
}

private fun calculateHigherAdjacent(
    cave: List<CharArray>,
    point: LowPoint,
    maxRow: Int,
    maxColumn: Int,
    calculatedPoints: MutableList<LowPoint>
): Int {
    if(calculatedPoints.contains(point)) return 0
    calculatedPoints.add(point)
    var higherPoints = 0
    if (point.position.first != 0 && cave[point.position.first - 1][point.position.second].digitToInt() > point.value
        && cave[point.position.first - 1][point.position.second].digitToInt() != 9
    ) {
        higherPoints += calculateHigherAdjacent(
            cave, LowPoint(
                Pair(point.position.first - 1, point.position.second),
                cave[point.position.first - 1][point.position.second].digitToInt()
            ), maxRow, maxColumn, calculatedPoints
        )
    }
    if (point.position.first != maxRow && cave[point.position.first + 1][point.position.second].digitToInt() > point.value
        && cave[point.position.first + 1][point.position.second].digitToInt() != 9
    ) {
        higherPoints += calculateHigherAdjacent(
            cave, LowPoint(
                Pair(point.position.first + 1, point.position.second),
                cave[point.position.first + 1][point.position.second].digitToInt()
            ), maxRow, maxColumn, calculatedPoints
        )
    }
    if (point.position.second != 0 && cave[point.position.first][point.position.second - 1].digitToInt() > point.value
        && cave[point.position.first][point.position.second - 1].digitToInt() != 9
    ) {
        higherPoints += calculateHigherAdjacent(
            cave, LowPoint(
                Pair(point.position.first, point.position.second - 1),
                cave[point.position.first][point.position.second - 1].digitToInt()
            ), maxRow, maxColumn, calculatedPoints
        )
    }
    if (point.position.second != maxColumn && cave[point.position.first][point.position.second + 1].digitToInt() > point.value
        && cave[point.position.first][point.position.second + 1].digitToInt() != 9
    ) {
        higherPoints += calculateHigherAdjacent(
            cave, LowPoint(
                Pair(point.position.first, point.position.second + 1),
                cave[point.position.first][point.position.second + 1].digitToInt()
            ), maxRow, maxColumn, calculatedPoints
        )
    }
    return higherPoints + 1
}
