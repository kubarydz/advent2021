fun main() {

    class Octopus(val coords: Pair<Int, Int>, var charge: Int, var flashed: Boolean = false) {
        fun adjacentLocations(): List<Pair<Int, Int>> {
            return listOf(
                coords.first to coords.second - 1,
                coords.first to coords.second + 1,
                coords.first - 1 to coords.second - 1,
                coords.first - 1 to coords.second,
                coords.first - 1 to coords.second + 1,
                coords.first + 1 to coords.second - 1,
                coords.first + 1 to coords.second,
                coords.first + 1 to coords.second + 1
            )
                .filter { it.first in 0..9 && it.second in 0..9 }
        }
    }

    fun flashAdjacent(octopuses: List<Octopus>, adjacentLocations: List<Pair<Int, Int>>) {
        octopuses.asSequence().filter { adjacentLocations.contains(it.coords) }
            .filter { !it.flashed && ++it.charge > 9  }.forEach { octopus ->
                    octopus.flashed = true
                    flashAdjacent(octopuses, octopus.adjacentLocations())
            }
    }

    fun flash(octopus: Octopus, octopuses: List<Octopus>) {
        if (!octopus.flashed && octopus.charge > 9) {
            octopus.flashed = true
            flashAdjacent(octopuses, octopus.adjacentLocations())
        }
    }

    fun List<String>.octopuses() = map { it.toCharArray() }.map { array -> array.map { it.digitToInt() } }
        .flatMapIndexed { ri, row ->
            row.mapIndexed { ci, column -> Octopus(ri to ci, column) }
        }

    fun part1(input: List<String>): Int {
        var flashes = 0
        val octopuses = input.octopuses()
        for (i in 1..100) {
            octopuses.forEach { it.charge++ }
            octopuses.forEach { flash(it, octopuses) }
            octopuses.filter { it.flashed }.forEach { it.flashed = false; it.charge = 0; flashes++ }
        }
        return flashes
    }

    fun part2(input: List<String>): Int {
        val octopuses = input.octopuses()
        var i = 0
        while (true) {
            octopuses.forEach { it.charge++ }
            octopuses.forEach { flash(it, octopuses) }
            i++
            if (octopuses.filterNot { it.flashed }.count() == 0)
                return i
            octopuses.filter { it.flashed }.forEach { it.flashed = false; it.charge = 0 }
        }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}