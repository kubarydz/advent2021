import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {

    fun List<String>.prepareFoldInstructions(): List<List<String>> {
        return filter { it.startsWith("fold") }.map { it.split("along") }
            .map { it.last().split('=') }
    }

    fun List<String>.preparePaper(): List<Pair<Int, Int>> {
        return asSequence()
            .filter { it.isNotEmpty() }
            .filter { !it.startsWith("fold") }
            .map { it.split(',') }
            .map { it.first().toInt() to it.last().toInt() }.toList()
    }

    fun foldPaper(inPaper: List<Pair<Int, Int>>, foldInstructions: List<List<String>>): List<Pair<Int, Int>> {
        var paper = inPaper
        for ((foldAxis, foldLine) in foldInstructions) {
            paper = paper.map {
                if (foldAxis.trim() == "x") {
                    if (it.first > foldLine.toInt()) it.first - 2 * abs(it.first - foldLine.toInt()) to it.second else it
                } else if (it.second > foldLine.toInt()) it.first to it.second - 2 * abs(it.second - foldLine.toInt()) else it
            }.distinct()
        }
        return paper
    }


    fun part1(input: List<String>): Int {
        val foldInstructions = input.prepareFoldInstructions()
        val paper = input.preparePaper()

        return foldPaper(paper, listOf(foldInstructions.first())).count()
    }

    fun part2(input: List<String>) {
        val foldInstructions = input.prepareFoldInstructions()
        val paper = input.preparePaper()

        val foldedPaper = foldPaper(paper, foldInstructions)
        val maxX = foldedPaper.maxOf { it.first }
        val maxY = foldedPaper.maxOf {it.second}
        val grid = Array(maxY + 1){ CharArray(maxX + 1){' '} }
        foldedPaper.forEach { dot ->
            grid[dot.second][dot.first] = '#'
        }
        grid.forEach { gridLine ->
            println(gridLine)
        }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    part2(testInput)

    val input = readInput("Day13")
    println(part1(input))
    part2(input)
}