package utils

import java.math.BigDecimal
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt
import kotlin.math.pow

fun cartesianDistance(point1: Point, point2: Point): Double {
    return sqrt(
        (point1.first - point2.first).toDouble().pow(2.0)
        + (point1.second - point2.second).toDouble().pow(2.0)
    )
}

fun manhattanDistance(point1: Point, point2: Point): Int {
    return abs(point1.first - point2.first) + abs(point1.second - point2.second)
}

fun manhattanDistance(point1: BigDecimalPoint, point2: BigDecimalPoint): BigDecimal {
    return (point1.first - point2.first).abs() + (point1.second - point2.second).abs()
}


fun minkovskiDistance(p1: List<Long>, p2: List<Long>, p: Int = 1): Double {
    return p1.zip(p2).sumOf { (p1i, p2i) ->
        abs(p1i - p2i).toDouble().pow(p)
    }.pow(1/p)
}

fun chebyshevDistance(p1: List<Long>, p2: List<Long>): Long {
    return p1.zip(p2).maxOf { (p1i, p2i) ->
        abs(p1i - p2i)
    }
}

fun chebyshevDistance(p1: Point, p2: Point): Int {
    return max(abs(p1.first - p2.first), abs(p1.second - p2.second))
}