import kotlin.math.abs

fun main() {

    fun part1(input: List<Int>): Int {
        var lowestFuelCost = Int.MAX_VALUE
        for (i in input.indices) {
            var fuelCost = 0
            for (position in input)
                fuelCost += abs(position - i)
            if (fuelCost < lowestFuelCost)
                lowestFuelCost = fuelCost
        }
        return lowestFuelCost
    }

    fun part2(input: List<Int>): Int {
        var lowestFuelCost = Int.MAX_VALUE
        for (i in input.indices) {
            var fuelCost = 0
            for (position in input) {
                fuelCost += fuelCost(abs(position - i), 1)
            }
            if (fuelCost < lowestFuelCost)
                lowestFuelCost = fuelCost
        }
        return lowestFuelCost
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInputToIntsComma("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInputToIntsComma("Day07")
    println(part1(input))
    println(part2(input))
}

private fun fuelCost(distanceLeft: Int, costMultiplier: Int): Int {
    return if (distanceLeft == 0) {
        0
    } else {
        costMultiplier + fuelCost(distanceLeft - 1, costMultiplier + 1)
    }
}