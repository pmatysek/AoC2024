package solutions

import utils.*

fun main() {
    val heights = "day10".getInputLines().map { it.map { it.digitToInt() } }
    val startPositions = heights.mapIndexed { i, ints ->
        ints.mapIndexed { j, value ->
            if (value == 0) {
                i to j
            } else null
        }
    }.flatten().filterNotNull().toSet()
    val scores = startPositions.map {
        visitedPoints.clear()
        calculateProperPaths(heights, it)
    }
    println(scores.sum())
}


val cache = mutableMapOf<Point, Int>()
var visitedPoints: MutableSet<Point> = mutableSetOf()
fun calculateProperPaths(heights: List<List<Int>>, startPosition: Point): Int {
    visitedPoints.add(startPosition)
    val valueAtPoint = heights.getValueAtOrNull(startPosition) ?: throw IllegalArgumentException()
    val points = Direction4.entries.map { direction ->
        startPosition.moveInDirection(direction)
    }.map { it to heights.getValueAtOrNull(it) }.filter { it.second != null && it.second!! - valueAtPoint == 1 }//for part1: && !visitedPoints.contains(it.first) }
    visitedPoints.addAll(points.map {it.first})
    return points.sumOf {
        if (it.second == 9) {
            1
        } else {
            cache.getOrPut(it.first) { calculateProperPaths(heights, it.first) }
            //instead for part1: calculateProperPaths(heights, it.first)
        }
    }
}