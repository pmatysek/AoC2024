package solutions

import utils.*

fun main() {
    val (input1, input2)  = "day19".getInputLines().chunkedByEmptyLine()
    val towels = input1.first().split(", ")
    val designs = input2
    println(designs.count { it.isPossible(towels) })
    println(designs.sumOf { it.isPossibleCount(towels) })
}

private fun String.isPossible(towels: List<String>): Boolean {
    if(isEmpty()) {
        return true
    }
    return towels.any { towel ->
        if(commonPrefixWith(towel) == towel) {
            removePrefix(towel).isPossible(towels)
        } else {
            false
        }
    }
}

private val cache19 = mutableMapOf<String, Long>()
private fun String.isPossibleCount(towels: List<String>): Long {
    if(isEmpty()) {
        return 1L
    }
    return towels.sumOf { towel ->
        if (commonPrefixWith(towel) == towel) {
            cache19.getOrPut(removePrefix(towel)) { removePrefix(towel).isPossibleCount(towels) }
        } else {
            0L
        }
    }
}
