package solutions

import utils.*
import utils.Direction8.*
import kotlin.math.pow

fun main() {
    val input = "day7".getInputLines().map {
        val inputs = it.split(": ")
        val parameters = inputs.second().split(" ").map { it.toLong() }
        Equation(inputs.first().toLong(), parameters)
    }
    val possibleEquations = input.filter { it.isPossible(listOf(Operator.PLUS, Operator.MULTIPLY)) }
    val possibleEquations2 = input.filter { it.isPossible(Operator.entries) }
    println(possibleEquations.sumOf { it.expectedValue })
    println(possibleEquations2.sumOf { it.expectedValue })
}


data class Equation(
    val expectedValue: Long,
    val parameters: List<Long>
) {
    fun isPossible(operators: List<Operator>): Boolean {
        val possibleOperatorsCombinations = operators.generateCombinations(parameters.size - 1)
        possibleOperatorsCombinations.forEach { combination ->
            val equationValue = parameters.reduceIndexed { index, acc, parameter ->
                when(combination[index - 1]) {
                    Operator.PLUS -> acc + parameter
                    Operator.MULTIPLY -> acc * parameter
                    Operator.CONCAT -> "$acc$parameter".toLong()
                }
            }
            if (equationValue == expectedValue) {
                return true
            }
        }
        return false
    }
}

enum class Operator {
    PLUS, MULTIPLY, CONCAT
}