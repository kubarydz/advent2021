fun main() {
    fun part1(input: List<String>): Int {
        var depth = 0
        var horizontalPosition = 0
        for (value in input) {
            val movement = value.split(" ")
            val movementValue = movement.last().toInt()
            when (movement.first()) {
                "forward" -> horizontalPosition += movementValue
                "up" -> depth -= movementValue
                "down" -> depth += movementValue
            }
        }
        return depth * horizontalPosition
    }

    fun part2(input: List<String>): Int {
        var depth = 0
        var horizontalPosition = 0
        var aim = 0
        for (value in input) {
            val movement = value.split(" ")
            val movementValue = movement.last().toInt()
            when (movement.first()) {
                "forward" -> {
                    horizontalPosition += movementValue
                    depth += aim * movementValue
                }
                "up" -> aim -= movementValue
                "down" -> aim += movementValue
            }
        }
        return depth * horizontalPosition
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))

}
