package day14

import readInput
import readSample

fun main() {

    fun part1(input: List<String>): Int {
        var polymerTemplate = input.first()
        val pairInsertions =
            input.subList(2, input.size).map { it.split(" -> ") }.map { it.first() to it.last() }.toMap()

        for (i in 1..10) {
            polymerTemplate = polymerTemplate.windowed(2).joinToString("") {
                it.first() + pairInsertions.getOrDefault(it, "")
            } + polymerTemplate.last()
        }
        val count = polymerTemplate.groupingBy { it }.eachCount()
        return count.maxOf { it.value } - count.minOf { it.value }
    }

    fun pairMapping(inputPair: Pair<String, String>): Pair<String, String> {
        return inputPair.first[0] + inputPair.second to inputPair.second + inputPair.first[1]
    }

    fun part2(input: List<String>): Long {
        val polymerTemplate = input.first()
        val pairInsertions = input.subList(2, input.size).map { it.split(" -> ") }.map { it.first() to it.last() }
            .map { it.first to pairMapping(it) }
            .toMap()

        var polyPairs = pairInsertions.keys.map { it to 0L }.toMap().toMutableMap()
        polymerTemplate.windowed(2).forEach { polyPairs[it] = polyPairs.getOrDefault(it, 0) + 1 }

        for (i in 1..40) {
            val tempMap = polyPairs.toMutableMap()
            tempMap.keys.forEach {
                val currentPairCount = polyPairs.getOrDefault(it, 0)
                if (currentPairCount > 0) {
                    val newPair = pairInsertions[it]
                    tempMap[newPair!!.first] = tempMap.getOrDefault(newPair.first, 0) + currentPairCount
                    tempMap[newPair.second] = tempMap.getOrDefault(newPair.second, 0) + currentPairCount
                    tempMap[it] = tempMap[it]!! - currentPairCount
                }
            }
            polyPairs = tempMap.toMutableMap()
        }
        var letterMap = mutableMapOf<Char, Long>()
        for ((key, value) in polyPairs) {
            letterMap[key[0]] = letterMap.getOrDefault(key[0], 0) + value
            letterMap[key[1]] = letterMap.getOrDefault(key[1], 0) + value
        }
        val farLeftLetter = polymerTemplate.first()
        val farRightLetter = polymerTemplate.last()
        letterMap[farLeftLetter] = letterMap.getOrDefault(farLeftLetter, 1) - 1L
        letterMap[farRightLetter] = letterMap.getOrDefault(farRightLetter, 1) - 1L

        letterMap = letterMap.map { it.key to it.value/2 }.toMap().toMutableMap()
        letterMap[farLeftLetter] = letterMap.getOrDefault(farLeftLetter, 0) + 1L
        letterMap[farRightLetter] = letterMap.getOrDefault(farRightLetter, 0) + 1L

        return letterMap.maxOf { it.value } - letterMap.minOf { it.value }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readSample("day14")
    check(part1(testInput) == 1588)
    check(part2(testInput) == 2188189693529L)

    val input = readInput("day14")
    println(part1(input))
    println(part2(input))
}