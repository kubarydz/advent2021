package day01

import readInput
import readSample

fun main() {
    fun part1(input: List<Int>): Int {
        var previousVal = input.first()
        var increasesCounter = 0
        for (value in input) {
            if (value > previousVal) increasesCounter++
            previousVal = value
        }
        return increasesCounter
    }

    fun part2(input: List<Int>): Int {
        val windows = input.windowed(3, 1)
        return calculateWindowIncreases(windows)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readSample("day01").map { it.toInt() }
    check(part1(testInput) == 7)

    val input = readInput("day01").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}

fun calculateWindowIncreases(windows: List<List<Int>>): Int {
    var previousSum = windows.first().sum()
    var increasesCounter = 0
    for (window in windows) {
        val sum = window.sum()
        if (sum > previousSum) increasesCounter++
        previousSum = sum
    }
    return increasesCounter
}