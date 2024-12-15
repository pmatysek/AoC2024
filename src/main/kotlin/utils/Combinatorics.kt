package utils

import kotlin.math.pow

fun <T> List<T>.combinations(): Sequence<Pair<T, T>> = sequence {
    for (i in 0..<size - 1)
        for (j in i + 1..<size)
            yield(get(i) to get(j))
}

fun <T> List<T>.generateCombinations(length: Int): List<List<T>> {
    val result = mutableListOf<MutableList<T>>()

    val totalCombinations = size.toDouble().pow(length.toDouble()).toInt()


    for (i in 0 until totalCombinations) {
        val combination = mutableListOf<T>()
        var current = i
        for (j in 0 until length) {
            val index = current % size
            combination.add(this[index])
            current /= size

        }

        result.add(combination)
    }
    return result
}