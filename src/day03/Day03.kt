package day03

import readInput
import readSample

fun main() {

    fun part1(input: List<String>): Int {
        return calculateGammaRate(input) * calculateEpsilonRate(input)
    }

    fun part2(input: List<String>): Int {
        return calculateOxygenGenRating(input) * calculateCO2ScrubRating(input)
    }

// test if implementation meets criteria from the description, like:
    val testInput = readSample("day03")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("day03")
    println(part1(input))
    println(part2(input))
}

private fun calculateGammaRate(input: List<String>): Int {
    val bitLength = input.first().length - 1
    var binaryString = ""
    for (i in 0..bitLength) {
        var zeros = 0
        var ones = 0
        input.forEach { bits ->
            if (bits[i] == '0') zeros++ else ones++
        }
        binaryString += if (zeros > ones) 0 else 1
    }
    return Integer.parseInt(binaryString, 2)
}

private fun calculateEpsilonRate(input: List<String>): Int {
    val bitLength = input.first().length - 1
    var binaryString = ""
    for (i in 0..bitLength) {
        val (zeros, ones) = countBits(input, i)
        binaryString += if (zeros < ones) 0 else 1
    }
    return Integer.parseInt(binaryString, 2)
}

private fun calculateOxygenGenRating(input: List<String>): Int {
    val bitLength = input.first().length - 1
    var remainingInput = input
    for (i in 0..bitLength) {
        remainingInput = constructRemainingOxygenGenRating(remainingInput, i)
        if (remainingInput.size == 1) break
    }
    return Integer.parseInt(remainingInput.first(), 2)
}

private fun constructRemainingOxygenGenRating(input: List<String>, i: Int): List<String> {
    val (zeros, ones) = countBits(input, i)
    val mostCommonBit = if (zeros > ones) '0' else '1'
    return input.filter { bits -> bits[i] == mostCommonBit }
}

private fun countBits(input: List<String>, i: Int): Pair<Int, Int> {
    var zeros = 0
    var ones = 0
    input.forEach { bits ->
        if (bits[i] == '0') zeros++ else ones++
    }
    return Pair(zeros, ones)
}

private fun calculateCO2ScrubRating(input: List<String>): Int {
    val bitLength = input.first().length - 1
    var remainingInput = input
    for (i in 0..bitLength) {
        remainingInput = constructRemainingCO2ScrubRating(remainingInput, i)
        if (remainingInput.size == 1) break
    }
    return Integer.parseInt(remainingInput.first(), 2)
}

private fun constructRemainingCO2ScrubRating(input: List<String>, i: Int): List<String> {
    val (zeros, ones) = countBits(input, i)
    val mostCommonBit = if (zeros <= ones) '0' else '1'
    return input.filter { bits -> bits[i] == mostCommonBit }
}