package utils

import kotlin.math.max

fun List<ULong>.findCommonDivisor(): ULong {
    var divisor = 1UL
    var i = 1UL
    while (this.all { i <= it }) {
        if (this.all { it % i == 0UL }) {
            divisor = i
        }
        i++
    }
    return divisor
}

fun List<ULong>.findLowestCommonMultiple() = reduce { acc, value -> findLowestCommonMultiple(acc, value) }
fun findLowestCommonMultiple(a: ULong, b: ULong): ULong {
    val larger = max(a, b)
    val maxCommonMultiple = a * b
    var lowestCommonMultiple = larger
    while (lowestCommonMultiple <= maxCommonMultiple) {
        if (lowestCommonMultiple % a == 0uL && lowestCommonMultiple % b == 0uL) {
            return lowestCommonMultiple
        }
        lowestCommonMultiple += larger
    }
    return maxCommonMultiple
}

fun newton(sampleSize: Long, numberOfObjects: Long) =
    numberOfObjects.factorial() / (sampleSize.factorial() * (numberOfObjects - sampleSize).factorial())

fun Long.factorial() = (1..this).reduce { acc, number ->
    acc * number
}

fun emptyLongRange() = (0L..<0L)

infix fun LongRange.intersect(other: LongRange) =
    if (min() <= other.max() && other.min() <= max()) {
        maxOf(min(), other.min()).rangeTo(minOf(max(), other.max()))
    } else {
        emptyLongRange()
    }
