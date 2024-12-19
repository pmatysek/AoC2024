package solutions

import utils.*

fun main() {
    val corrupted = "day18".getInputLines().take(1024).map { it.split(",").map { it.toInt() }.toPair() }.toSet()
    val startPosition = 0 to 0
    val endPosition = 70 to 70
    val pointsToGo = arrayDequeOf(startPosition to 0)
    val visitedPoints = mutableSetOf<Point>()
    val pathsLengths = mutableListOf<Int>()
    while(pointsToGo.isNotEmpty()) {
        val (currPoint, currPathLength) = pointsToGo.removeFirst()
        if(currPoint in corrupted || currPoint in visitedPoints || currPoint.first !in (0..70) || currPoint.second !in (0..70)) {
            continue
        }
        if(currPoint == endPosition) {
            pathsLengths.add(currPathLength)
            continue
        }
        visitedPoints.add(currPoint)
        Direction4.entries.forEach { direction ->
            pointsToGo.add(currPoint.moveInDirection(direction) to currPathLength + 1)
        }
    }
    println(pathsLengths.min())
// part2
    run breaking@ {
        (1024..3450).forEach { numberOfBytes ->
            val corrupted2 = "day18".getInputLines().take(numberOfBytes).map { it.split(",").map { it.toInt() }.toPair() }.toSet()
            val pointsToGo2 = arrayDequeOf(startPosition to 0)
            val visitedPoints2 = mutableSetOf<Point>()
            val pathsLengths2 = mutableListOf<Int>()
            while(pointsToGo2.isNotEmpty()) {
                val (currPoint, currPathLength) = pointsToGo2.removeFirst()
                if(currPoint in corrupted2 || currPoint in visitedPoints2 || currPoint.first !in (0..70) || currPoint.second !in (0..70)) {
                    continue
                }
                if(currPoint == endPosition) {
                    pathsLengths2.add(currPathLength)
                    continue
                }
                visitedPoints2.add(currPoint)
                Direction4.entries.forEach { direction ->
                    pointsToGo2.add(currPoint.moveInDirection(direction) to currPathLength + 1)
                }
            }
            if (pathsLengths2.isEmpty()) {
                println(corrupted2.last())
                return@breaking
            }
        }
    }


}
