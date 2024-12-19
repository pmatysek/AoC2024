package solutions

import utils.*

fun main() {
    val map = "day16".getInputLines().map { it.toList() }
    val startPosition = map.findCoordinatesOf('S')
    val endPosition = map.findCoordinatesOf('E')
    val visitedPoints = mutableMapOf<Pair<Point, Direction4>, Long>()
    val pointsToGo = arrayDequeOf(Triple(startPosition, Direction4.RIGHT, emptyList<Pair<Point, Direction4>>()))
    val properPaths = mutableListOf<List<Pair<Point, Direction4>>>()
    while (pointsToGo.isNotEmpty()) {
        val (currPosition, currDirection, currentPath) = pointsToGo.removeFirst()
        val currValue = map.getValueAtOrNull(currPosition)
        if(currValue == null || currValue == '#') {
            continue
        }
        var newPath: List<Pair<Point, Direction4>>?
        val lastScore = visitedPoints[currPosition to currDirection]
        val newScore = calculateScore(currentPath + (currPosition to currDirection))
        if((currPosition to currDirection) in currentPath || (lastScore != null && lastScore < (newScore))) {
            continue
        } else {
            newPath = currentPath + (currPosition to currDirection)
        }
        visitedPoints[currPosition to currDirection] = newScore
        when(currValue) {
            null, '#' -> continue
            'E' -> {
                properPaths.add(newPath)
                continue
            }
            else -> {
                val newPoint1 = currPosition.moveInDirection(currDirection)
                pointsToGo.add(Triple(newPoint1, currDirection, newPath))


                val newDirection2 = currDirection.turn90DegreesRight()
                val newPoint2 = currPosition.moveInDirection(newDirection2)
                if(map.getValueAtOrNull(newPoint2) !in listOf(null, '#')) {
                    pointsToGo.add(Triple(currPosition, newDirection2, newPath))
                }

                val newDirection3 = currDirection.turn90DegreesLeft()
                val newPoint3 = currPosition.moveInDirection(newDirection3)
                if(map.getValueAtOrNull(newPoint3) !in listOf(null, '#')) {
                    pointsToGo.add(Triple(currPosition, newDirection3, newPath))
                }

            }
        }
    }
    val scores = properPaths.map {
        calculateScore(it)
    }
    val minScore = scores.min()
    val minScorePaths = properPaths.filter { calculateScore(it) == minScore }
    val minScorePoints = minScorePaths.flatten().toSet()

    println(minScore)
    println(scores.size)
    printMap(map, minScorePoints)

    println(minScorePaths.flatten().map { it.first }.toSet().size)
}

private fun printMap(
    map: List<List<Char>>,
    path: Set<Pair<Point, Direction4>>,
    stoppedAt: Point? = null
) {
    val points = path.map { it.first }
    map.forEachIndexed { i, line ->
        line.forEachIndexed { j, value ->
            print(
                when {
                    i to j == stoppedAt -> {
                        "X"
                    }

                    i to j in points -> {
                        path.find { it.first == i to j }!!.second.toPrettySymbol()
                    }

                    else -> {
                        value
                    }
                }
            )
        }
        println()
    }
}

private fun calculateScore(it: List<Pair<Point, Direction4>>) =
    if(it.isEmpty()) {
        Long.MAX_VALUE
    } else {
        it.zipWithNext().sumOf { (p1, p2) ->
            if (p1.second != p2.second) {
                1000L
            } else {
                1L
            }
        }
    }
