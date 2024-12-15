package utils

import kotlin.math.abs

fun <E> List<E>.second() = this[1]

fun <T> List<List<T>>.toPairsList(): List<Pair<T, T>> = map {
    it.toPair()
}
fun <T> List<T>.toPair(): Pair<T, T> {
    require(size == 2)
    return first() to second()
}

inline fun <reified T> List<List<T>>.transpose(): List<List<T>> {
    val transposed = MutableList(this.first().size) { MutableList<T?>(this.size) { null } }
    for (i in this.indices) {
        for (j in this.first().indices) {
            transposed[j][i] = this[i][j]
        }
    }
    return transposed.map { it.map { it!! } }
}

fun <T> arrayDequeOf(vararg elements: T) = ArrayDeque(elements.toList())

fun <T> List<T>.subListCoercedIn(fromIndex: Int, toIndex: Int) =
    subList(fromIndex.coerceIn(indices), toIndex.coerceIn(0..this.size))


inline fun <reified T> List<T>.toFrequencyMap() = this.groupingBy { it }.eachCount()

inline fun <reified T> List<T>.withoutElementAt(
    index: Int
): List<T> {
    val newList = this.toMutableList()
    newList.removeAt(index)
    return newList
}

fun List<Long>.isDescendingWithDifferencesInRange(range: IntRange) = zipWithNext().all { it.first - it.second in range }
fun List<Long>.isAscendingWithDifferencesInRange(range: IntRange) = zipWithNext().all { it.second - it.first in range }
fun List<Long>.differencesBetweenNextElementsInRange(range: IntRange) = zipWithNext().all { abs(it.second - it.first) in range }
fun List<Long>.isDescending() = zipWithNext().all { it.first - it.second >= 0 }
fun List<Long>.isAscending() = zipWithNext().all { it.first - it.second <= 0 }
inline fun <reified T: Comparable<T>> List<T>.isDescendingGeneric() = sortedByDescending { it } == this
inline fun <reified T: Comparable<T>> List<T>.isAscendingGeneric() = sorted() == this

fun List<Long>.multiply() =
    this.reduce { a, b -> a * b }

fun <T> List<List<T>>.forEachIndexedOnMatrix(action: (Pair<Int, Int>, T) -> Unit) {
    for (i in indices) {
        for (j in this[i].indices) {
            action(Pair(i, j), this[i][j])
        }
    }
}

public inline fun <T, R> List<List<T>>.mapIndexedOnMatrix(transform: (index: Point, T) -> R): List<List<R>> =
    mapIndexed { i, list ->
        list.mapIndexed { j, value ->
            transform(i to j, value)
        }
    }