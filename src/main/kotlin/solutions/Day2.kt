package solutions

import utils.getInputLines
import utils.isAscendingWithDifferencesInRange
import utils.isDescendingWithDifferencesInRange
import utils.splittedToLong
import utils.withoutElementAt

fun main() {
    val input = "day2".getInputLines().splittedToLong()
    val correct1 = input.filter { level ->
        level.isSafe()
    }
    val correct2 = input.filter { level ->
        level.indices
            .map { level.withoutElementAt(it) }
            .any { it.isSafe() }
    }
    println(correct1.size)
    println(correct2.size)
}

private fun List<Long>.isSafe() =
    isDescendingWithDifferencesInRange(1..3) || isAscendingWithDifferencesInRange(1..3)