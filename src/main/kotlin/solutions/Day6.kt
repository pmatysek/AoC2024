package solutions

import utils.*
import utils.Direction8.*

fun main() {
    val map = "day6".getInputLines().map { it.toCharArray().toList() }
    val guardStartPosition = map.findCoordinatesOf('^')
    val visitedPoints = mutableSetOf<Point>()
    var currentPoint = guardStartPosition
    var currentDirection = UP
    while (currentPoint.isOnMatrix(map)) {
        visitedPoints.add(currentPoint)
        val nextPoint = currentPoint.moveInDirection(currentDirection)
        when (map.getValueAtOrNull(nextPoint)) {
            '.' -> currentPoint = nextPoint
            '^' -> currentPoint = nextPoint
            '#' -> {
                currentDirection = currentDirection.turn90DegreesRight()
                currentPoint = currentPoint.moveInDirection(currentDirection)
            }

            else -> break
        }
    }
    println(visitedPoints.size)

    var counter = 0
    visitedPoints.forEach() { point ->
        val value = map.getValueAtOrNull(point)
        if(value == '.') {
            val newMap = map.toMutableList().map { it.toMutableList() }
            newMap[point.first][point.second] = '#'
            if(willStuckInALoop(newMap, guardStartPosition)) {
                counter++
            }
        }
    }
    println(counter)
}

fun willStuckInALoop(map: List<List<Char>>, guardStartPosition: Pair<Int, Int>): Boolean {
    val visitedPoints = mutableSetOf<Pair<Point, Direction>>()
    var currentPoint = guardStartPosition
    var currentDirection = UP
    while (currentPoint.isOnMatrix(map)) {
        if(!visitedPoints.add(currentPoint to currentDirection)){
            return true
        }
        val nextPoint = currentPoint.moveInDirection(currentDirection)
        when (map.getValueAtOrNull(nextPoint)) {
            '.' -> currentPoint = nextPoint
            '^' -> currentPoint = nextPoint
            '#' -> {
                currentDirection = currentDirection.turn90DegreesRight()
                var newPoint = currentPoint.moveInDirection(currentDirection)
                var newValue = map.getValueAtOrNull(newPoint)
                while (newValue == '#') {
                    currentDirection = currentDirection.turn90DegreesRight()
                    newPoint = currentPoint.moveInDirection(currentDirection)
                    newValue = map.getValueAtOrNull(newPoint)
                }
                currentPoint = newPoint
            }

            else -> return false
        }
    }
    return false
}
