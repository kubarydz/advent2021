package day07

import readInput
import readInputToIntsComma
import readSample
import kotlin.math.abs
import kotlin.math.min

fun main() {

    fun part1(input: List<Int>): Int {
        var lowestFuelCost = Int.MAX_VALUE
        for (i in input.indices) {
            val fuelCost = input.sumOf { abs(it - i) }
            lowestFuelCost = min(lowestFuelCost, fuelCost)
        }
        return lowestFuelCost
    }

    fun part2(input: List<Int>): Int {
        var lowestFuelCost = Int.MAX_VALUE
        for (i in input.indices) {
            val fuelCost = input.sumOf { fuelCostExpensive(abs(it - i)) }
            lowestFuelCost = min(lowestFuelCost, fuelCost)
        }
        return lowestFuelCost
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readSample("day07").first().split(',').map { it.toInt() }
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("day07").first().split(',').map { it.toInt() }
    println(part1(input))
    println(part2(input))
}

private fun fuelCostExpensive(distance: Int): Int {
    return distance * (distance + 1)/2
}