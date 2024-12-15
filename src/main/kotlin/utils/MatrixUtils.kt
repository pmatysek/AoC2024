package utils

import utils.Direction8.*
import java.math.BigDecimal

typealias Point = Pair<Int, Int>
typealias LongPoint = Pair<Long, Long>
typealias ULongPoint = Pair<ULong, ULong>
typealias BigDecimalPoint = Pair<BigDecimal, BigDecimal>

inline fun <reified T> List<List<T>>.getValueAtOrNull(currPoint: Pair<Int, Int>) =
    this.getOrNull(currPoint.first)?.getOrNull(currPoint.second)

operator fun Point.times(it: Int): Point {
    return first * it to second * it
}

//operator fun ULongPoint.times(it: ULong): ULongPoint {
//    return first * it to second * it
//}

operator fun Point.times(it: Double): BigDecimalPoint {
    return BigDecimal.valueOf(first * it) to BigDecimal.valueOf(second * it)
}

operator fun ULongPoint.times(it: ULong): ULongPoint {
    return first * it to second * it
}

operator fun Point.minus(startPosition: Point): Point =
    this.first - startPosition.first to this.second - startPosition.second


fun <T> List<List<T>>.findCoordinatesOf(element: T): Point {
    val foundRow: Int = this.indexOf(this.first { it.contains(element) })
    val foundColumn = this[foundRow].indexOf(element)
    return foundRow to foundColumn
}

fun Point.moveByVector(vector: Point) = (first + vector.first) to (second + vector.second)
fun Point.moveInDirection(direction: Direction, distance: Int = 1) = moveByVector(direction.getVector() * distance)
fun LongPoint.moveByVectorLong(vector: LongPoint) = (first + vector.first) to (second + vector.second)
fun LongPoint.longMoveByVector(vector: Point) = (first + vector.first) to (second + vector.second)
fun ULongPoint.moveByVectorULong(vector: ULongPoint) = (first + vector.first) to (second + vector.second)


fun Point.moveByVectorWithOverlap(vector: Point, maxX: Int, maxY: Int): Point {
    var x = first + vector.first
    var y = second + vector.second
    if(x > maxX) {
        x -= (maxX + 1)
    }
    if(x < 0) {
        x += (maxX + 1)
    }
    if(y > maxY) {
        y -= (maxY + 1)
    }
    if(y < 0) {
        y += (maxY + 1)
    }
    return x to y
}

fun Point.moveInDirectionOfFloatSize(direction: Direction, distance: Double = 1.0) =
    moveByBigDecimalVector(direction.getVector() * distance)

fun Point.moveByBigDecimalVector(vector: BigDecimalPoint) = (first.toBigDecimal() + vector.first) to (second.toBigDecimal() + vector.second)

fun <T> Point.isOnMatrix(matrix: List<List<T>>) =
    first in matrix.indices && second in matrix.first().indices

interface Direction {
    fun getVector(): Point
}
enum class Direction8(
    val pointVector: Point,
): Direction {
    UP(
        pointVector = -1 to 0,
    ),
    DOWN(
        pointVector = 1 to 0,
    ),
    LEFT(
        pointVector = 0 to -1,
    ),
    RIGHT(
        pointVector = 0 to 1,
    ),
    UP_RIGHT(
        pointVector = -1 to 1,
    ),
    UP_LEFT(
        pointVector = -1 to -1,
    ),
    DOWN_RIGHT(
        pointVector = 1 to 1,
    ),
    DOWN_LEFT(
        pointVector = 1 to -1,
    );

    override fun getVector(): Point {
        return this.pointVector
    }
}

enum class Direction4(
    val pointVector: Point,
): Direction {
    UP(
        pointVector = -1 to 0,
    ),
    DOWN(
        pointVector = 1 to 0,
    ),
    LEFT(
        pointVector = 0 to -1,
    ),
    RIGHT(
        pointVector = 0 to 1,
    );

    override fun getVector(): Point {
        return this.pointVector
    }
}

fun Direction8.turn90DegreesRight(): Direction8 {
    return when (this) {
        UP -> RIGHT
        RIGHT -> DOWN
        DOWN -> LEFT
        LEFT -> UP
        UP_LEFT -> UP_RIGHT
        UP_RIGHT -> DOWN_RIGHT
        DOWN_RIGHT -> DOWN_LEFT
        DOWN_LEFT -> UP_LEFT
    }
}

fun Direction8.turn90DegreesLeft(): Direction8 {
    return when (this) {
        UP -> LEFT
        LEFT -> DOWN
        DOWN -> RIGHT
        RIGHT -> UP
        UP_LEFT -> DOWN_LEFT
        UP_RIGHT -> UP_LEFT
        DOWN_RIGHT -> UP_RIGHT
        DOWN_LEFT -> DOWN_RIGHT
    }
}

fun Direction8.turn45DegreesRight(): Direction8 {
    return when (this) {
        UP -> UP_RIGHT
        UP_RIGHT -> RIGHT
        RIGHT -> DOWN_RIGHT
        DOWN_RIGHT -> DOWN
        DOWN -> DOWN_LEFT
        DOWN_LEFT -> LEFT
        LEFT -> UP_LEFT
        UP_LEFT -> UP
    }
}

fun Direction8.turn45DegreesLeft(): Direction8 {
    return when (this) {
        UP -> UP_LEFT
        UP_LEFT -> LEFT
        LEFT -> DOWN_LEFT
        DOWN_LEFT -> DOWN
        DOWN -> DOWN_RIGHT
        DOWN_RIGHT -> RIGHT
        RIGHT -> UP_RIGHT
        UP_RIGHT -> UP
    }
}



fun Direction4.turn90DegreesRight(): Direction4 {
    return when (this) {
        Direction4.UP -> Direction4.RIGHT
        Direction4.RIGHT -> Direction4.DOWN
        Direction4.DOWN -> Direction4.LEFT
        Direction4.LEFT -> Direction4.UP
    }
}


data class Point3(
    val x: Long,
    val y: Long,
    val z: Long,
) {
    fun moveByVector(vector: LongPoint): Point3 {
        return this.copy(
            x = x + vector.first,
            y = y + vector.second,
        )
    }

    fun moveByVector(vector: Point3): Point3 {
        return this.copy(
            x = x + vector.x,
            y = y + vector.y,
            z = z + vector.z,
        )
    }
}

data class DobulePoint3(
    val x: Double,
    val y: Double,
    val z: Double,
) {
    fun moveByVector(vector: LongPoint): DobulePoint3 {
        return this.copy(
            x = x + vector.first,
            y = y + vector.second,
        )
    }

    fun moveByVector(vector: Point3): DobulePoint3 {
        return this.copy(
            x = x + vector.x,
            y = y + vector.y,
            z = z + vector.z,
        )
    }
}

data class BigDecimalPoint3(
    val x: BigDecimal,
    val y: BigDecimal,
    val z: BigDecimal,
) {
    fun moveByVector(vector: LongPoint): BigDecimalPoint3 {
        return this.copy(
            x = x + vector.first.toBigDecimal(),
            y = y + vector.second.toBigDecimal(),
        )
    }

    fun moveByVector(vector: Point3): BigDecimalPoint3 {
        return this.copy(
            x = x + vector.x.toBigDecimal(),
            y = y + vector.y.toBigDecimal(),
            z = z + vector.z.toBigDecimal(),
        )
    }

    fun moveByVector(vector: BigDecimalPoint3): BigDecimalPoint3 {
        return this.copy(
            x = x + vector.x,
            y = y + vector.y,
            z = z + vector.z,
        )
    }
}