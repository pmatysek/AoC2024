package solutions

import utils.getInputLines
import utils.second
import utils.toFrequencyMap
import utils.transpose
import kotlin.math.abs

fun main() {
    val input = "day1".getInputLines()
    val distances = input
        .map { inputLine ->
            inputLine.split("   ")
                .map { it.toInt() }
        }.transpose()
        .map { it.sorted() }
    val differences = distances.first().mapIndexed { index, distance -> abs(distance - distances.second()[index]) }
    println(differences.sum())
    val frequencyOfNumbers = distances.second().toFrequencyMap()
    val frequenciesOfFirstList = distances.first().sumOf { frequencyOfNumbers.getOrDefault(it, 0) * it }
    println(frequenciesOfFirstList)
}