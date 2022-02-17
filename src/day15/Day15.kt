package day15

import readInput
import readSample
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

typealias Point = Pair<Int, Int>

val Point.x get() = first
val Point.y get() = second
private operator fun Point.plus(other: Point) = x + other.x to y + other.y

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readSample("day15")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    val input = readInput("day15")
    println(part1(input))
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    return calculateShortestPath(input.map { it.toCharArray() }.map { a -> a.map { it.digitToInt() } })
}

// uses Dijkstra
private fun calculateShortestPath(input: List<List<Int>>): Int {
    val costMap = input.toCostMap()

    val startingPoint = Point(0, 0)
    val priority = HashMap<Point, Int>()
    priority[startingPoint] = 0

    val riskComparator: Comparator<Point> = compareBy { priority[it] }
    val frontier = PriorityQueue(riskComparator)
    val caveSize = input.size - 1
    val cameFrom = HashMap<Point, Point>()

    val goal = Point(caveSize, caveSize)
    cameFrom[startingPoint] = startingPoint

    frontier.add(startingPoint)

    val costSoFar = HashMap<Point, Int>()
    costSoFar[startingPoint] = 0

    while (frontier.isNotEmpty()) {
        val current = frontier.remove()
        if (current == goal) {
            break
        }
        for (next in current.getNeighbours(caveSize)) {
            if (!cameFrom.containsKey(next)) {
                val newCost = costSoFar[current]!! + costMap[next]!!
                if (next !in costSoFar || newCost < costSoFar[next]!!) {
                    costSoFar[next] = newCost
                    priority[next] = newCost
                    frontier.add(next)
                    cameFrom[next] = current
                }
            }
        }
    }
    val path = findShortestPath(goal, startingPoint, cameFrom)
    return path.sumOf { costMap[it]!! }
}

fun findShortestPath(goal: Point, startingPoint: Point, cameFrom: HashMap<Point, Point>): LinkedList<Point> {
    var current = goal
    val path = LinkedList<Point>()
    while (current != startingPoint) {
        path.add(current)
        current = cameFrom[current]!!
    }
    return path
}

private fun List<List<Int>>.toCostMap(): HashMap<Point, Int> {
    val costMap = HashMap<Point, Int>()
    this.forEachIndexed { y, row ->
        row.forEachIndexed { x, r ->
            costMap[Point(x, y)] = r
        }
    }
    return costMap
}

private fun Point.getNeighbours(caveSize: Int): Set<Point> {
    val neighbours = HashSet<Point>()
    if (x > 0) neighbours.add(this + Point(-1, 0))
    if (x < caveSize) neighbours.add(this + Point(1, 0))
    if (y > 0) neighbours.add(this + Point(0, -1))
    if (y < caveSize) neighbours.add(this + Point(0, 1))
    return neighbours
}

private fun Int.caveIncrement(): Int {
    val increment = (this + 1) % 10
    return if (increment == 0) 1 else increment
}

private fun part2(input: List<String>): Int {
    val biggerInput =
        input.asSequence().map { it.toCharArray() }.map { a -> a.map { it.digitToInt() } }.toMutableList()
            .map { it.toMutableList() }
            .toMutableList()
    val caveSize = input.size
    for (i in 0..3) {
        biggerInput.addAll(
            biggerInput.slice(i * caveSize until i * caveSize + caveSize)
                .map { it.map { it.caveIncrement() }.toMutableList() })
    }
    for (i in 0..3) {
        biggerInput.forEachIndexed { index, row ->
            row.addAll(biggerInput[index].slice(i * caveSize until i * caveSize + caveSize).map { it.caveIncrement() })
        }
    }
    return calculateShortestPath(biggerInput.toList().map { it.toList() })
}

