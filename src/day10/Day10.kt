package day10

import readInput
import readSample

fun main() {

    val corruptedScores = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val incompleteScores = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)
    val opposingBrackets = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
    val closingBrackets = setOf(')', ']', '}', '>')

    fun isCorruptedLine(line: CharArray): Int {
        val bracketsStack = mutableListOf<Char>()
        for ((i, bracket) in line.iterator().withIndex()) {
            if (closingBrackets.contains(bracket)) {
                if (bracketsStack.removeLast() != opposingBrackets[bracket]) {
                    return i
                }
            } else bracketsStack.add(bracket)
        }
        return 0
    }

    fun part1(input: List<String>): Int {
        return input.asSequence().map { it.toCharArray() }.filter { isCorruptedLine(it) != 0 }
            .map { line ->
                corruptedScores[line[isCorruptedLine(line)]]
            }.filterNotNull().sum()
    }

    fun part2(input: List<String>): Long {
        val points = mutableListOf<Long>()
        input.map { it.toCharArray() }.filter { isCorruptedLine(it) == 0 }
            .map { line ->
                val bracketsStack = mutableListOf<Char>()
                for (bracket in line) {
                    if (closingBrackets.contains(bracket)) {
                        bracketsStack.removeLast()
                    } else bracketsStack.add(bracket)
                }
                points.add(bracketsStack.reversed().mapNotNull { incompleteScores[it] }
                    .fold(0) { sum, el -> sum * 5 + el })
            }
        return points.sortedBy { it }[points.size / 2]
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readSample("day10")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("day10")
    println(part1(input))
    println(part2(input))
}

