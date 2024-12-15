package solutions

import utils.*

private val REGEX_1 = """mul\((\d){1,3},(\d){1,3}\)""".toRegex()
private val REGEX_2 = """(mul\((\d){1,3},(\d){1,3}\)|do\(\)|don't\(\))""".toRegex()
fun main() {
    val input = "day3".getInputAsSingleLine()
    part1(input)
    part2(input)
}

private fun part1(input: String) {
    val matches = REGEX_1.findAll(input).map { it.value.splittedToSignedLongExcludingNonDigits(",") }
    val result = matches.sumOf { it.first() * it.second() }
    println(result)
}

private fun part2(input: String) {
    val result = REGEX_2.findAll(input)
        .fold(true to 0L) { (multiplicationEnabled, sum), instruction ->
            when {
                instruction.value.startsWith("mul") -> {
                    val newSum: Long =
                        if (multiplicationEnabled) (sum + instruction.value.splittedToSignedLongExcludingNonDigits(",").multiply()) else sum
                    multiplicationEnabled to newSum
                }

                instruction.value.startsWith("don") -> false to sum
                instruction.value.startsWith("do") -> true to sum
                else -> throw UnsupportedOperationException()
            }
        }
    println(result)
}