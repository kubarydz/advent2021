package day08

import readInput
import readSample

fun main() {

    fun part1(input: List<String>): Int {
        val uniqueNrOfSegments = setOf(2, 3, 4, 7)
        return input.map { line -> line.dropWhile { it != '|' } }//.drop(1) }
            .flatMap { it.split(' ') }
            .count { uniqueNrOfSegments.contains(it.length) }
    }

    fun part2(input: List<String>): Int {
        val results = emptyList<Int>().toMutableList()
        val splitInput = input.map { it.split('|') }
        for (entry in splitInput) {
            val mappedEntries = Array(10) { "" }
            val inputNumbers = entry.first().split(' ').map { it.toCharArray().sorted().joinToString("") }
            mappedEntries[1] = inputNumbers.find { it.length == 2 }.orEmpty()
            mappedEntries[4] = inputNumbers.find { it.length == 4 }.orEmpty()
            mappedEntries[7] = inputNumbers.find { it.length == 3 }.orEmpty()
            mappedEntries[8] = inputNumbers.find { it.length == 7 }.orEmpty()
            val fiveChars = inputNumbers.filter { it.length == 5 }
            mappedEntries[2] = fiveChars.first { it.filterNot { ch -> mappedEntries[4].contains(ch) }.length == 3 }
            mappedEntries[3] = fiveChars.first { it.filterNot { ch -> mappedEntries[2].contains(ch) }.length == 1 }
            mappedEntries[5] = fiveChars.first { it != mappedEntries[3] && it != mappedEntries[2] }
            val sixChars = inputNumbers.filter { it.length == 6 }.toMutableList()
            mappedEntries[9] = sixChars.first{it.filterNot { ch -> mappedEntries[3].contains(ch) }.length == 1}
            sixChars.remove(mappedEntries[9])
            mappedEntries[6] = sixChars.first{it.filterNot { ch -> mappedEntries[5].contains(ch) }.length == 1 }
            sixChars.remove(mappedEntries[6])
            mappedEntries[0] = sixChars.first()
            val entriesMap = mappedEntries.mapIndexed { index, s ->  s to index.toString()}.toMap()
            val outputNumbers = entry.last().split(' ').map { it.toCharArray().sorted().joinToString("") }
            val output = outputNumbers.joinToString("") { entriesMap.getOrDefault(it, "") }
            results += output.toInt()
        }
        return results.sum()
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readSample("day08")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("day08")
    println(part1(input))
    println(part2(input))
}