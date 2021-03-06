package day06

import readInput
import readSample

fun main() {

    fun part1(input: List<String>): Long {
        return calculateFish(input.first().split(',').map { it.toInt() }, 80)
    }

    fun part2(input: List<String>): Long {
        return calculateFish(input.first().split(',').map { it.toInt() }, 256)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readSample("day06")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539)

    val input = readInput("day06")
    println(part1(input))
    println(part2(input))
}

private fun calculateFish(input: List<Int>, days: Int): Long {
    val fish = LongArray(9)
    input.forEach { fish[it] += 1L }
    var lastFishCount = fish[8]
    (1..days).forEach { _ ->
        (fish.lastIndex downTo 0).forEach { a ->
            if (a == 0) {
                fish[6] += fish[a]
                val temp = lastFishCount
                fish[a] = lastFishCount
                lastFishCount = temp
            } else {
                val temp = fish[a]
                fish[a] = lastFishCount
                lastFishCount = temp
            }
        }
    }
    return fish.sum()
}

