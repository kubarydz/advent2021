import java.util.*

fun main() {


    fun part1(input: List<String>): Int {
        val cavePaths = input.map { entry -> entry.split('-') }.map { it.first() to it.last() }
        val caveTree = CaveTree("start", emptyList())

        var pathAdded = true
        while (pathAdded) {
            pathAdded = false
            for (cave in cavePaths) {
                if (caveTree.addChild(cave)) pathAdded = true
            }
        }
        return caveTree.findExitNumbers()
    }

    fun part2(input: List<String>): Int {
        val cavePaths = input.map { entry -> entry.split('-') }.map { it.first() to it.last() }
        val smallCaves = cavePaths.flatMap { listOf(it.second, it.first) }.filter { it != "start" && it != "end" }
            .filter { it.lowercase(Locale.getDefault()) == it }

        val paths = mutableListOf<List<String>>()
        for (smallCave in smallCaves) {
            val caveTree = CaveTree("start", emptyList(), smallCave)
            var pathAdded = true
            while (pathAdded) {
                pathAdded = false
                for (cave in cavePaths) {
                    if (caveTree.addChild(cave)) pathAdded = true
                }
            }
            paths += caveTree.findExitPaths()
        }
        return paths.distinct().count()
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 36)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}

private class CaveTree(
    val value: String,
    val smallCaves: List<String>,
    val allowedSmallCave: String? = null,
    val parent: CaveTree? = null
) {
    var children: MutableList<CaveTree> = mutableListOf()

    fun isSmallCave(): Boolean {
        return value.lowercase(Locale.getDefault()) == value
    }

    fun addChild(child: Pair<String, String>): Boolean {
        if (value == "end") return false
        if (child.first != value && child.second != value) return addChildToChildren(child)
        val nodeValue = if (child.first == value) child.second else child.first
        if (children.map { it.value }.contains(nodeValue)) {
            return if (!isSmallCave() || value == allowedSmallCave) addChildToChildren(child) else false
        }
        if (smallCaves.contains(nodeValue)) {
            if (allowedSmallCave == null) return false
            if (nodeValue == allowedSmallCave && smallCaves.count { it == allowedSmallCave } > 1) return false
        }
        val nodeSmallCaves = if (isSmallCave()) smallCaves + value else smallCaves
        children.add(CaveTree(nodeValue, nodeSmallCaves, allowedSmallCave, this))
        return true
    }

    private fun addChildToChildren(child: Pair<String, String>): Boolean {
        var childAdded = false
        for (node in children) {
            if (node.addChild(child)) childAdded = true
        }
        return childAdded
    }

    fun findExitNumbers(): Int {
        if (value == "end") return 1
        if (children.isEmpty()) return 0
        return children.fold(0) { sum, child -> sum + child.findExitNumbers() }
    }

    private fun buildExitPath(path: MutableList<String>): List<String> {
        if (value == "start") return path.toList()
        path.add(value)
        return parent!!.buildExitPath(path)
    }

    fun findExitPaths(): List<List<String>> {
        return if (value == "end") listOf(parent!!.buildExitPath(mutableListOf(this.value)))
        else {
            val paths = mutableListOf<List<String>>()
            for (child in children) {
                paths += child.findExitPaths()
            }
            paths
        }
    }

}