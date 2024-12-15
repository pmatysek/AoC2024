package solutions

import utils.*
import utils.Direction8.*

fun main() {
    val (input1, input2) = "day5".getInputLines().chunkedByEmptyLine()
    val rulesInput = input1.splittedToLong("|")
    val rules = mutableMapOf<Long, MutableList<Long>>()
    rulesInput.forEach {
        if(rules.contains(it.second())) {
            rules[it.second()]?.add(it.first())
        } else {
            rules[it.second()] = mutableListOf(it.first())
        }
    }
    val updates = input2.splittedToLong(",")
    val correctUpdates = updates.filter { update ->
        update.isCorrect(rules)
    }
    println(correctUpdates.sumOf {
        it[it.size/2]
    })

    val inCorrectUpdates = updates.filter { update ->
        !update.isCorrect(rules)
    }
    var newIncorrect = inCorrectUpdates
    repeat(rules.maxOf { it.value.size }) {
        newIncorrect = newIncorrect.map { update ->
            val newUpdate = mutableListOf<Long>()
            update.forEachIndexed { index, value ->
                val pagesToBeBefore = rules[value] ?: emptyList()
                pagesToBeBefore.forEach {
                    val indexOfPageTobeBefore = update.indexOf(it)
                    if(indexOfPageTobeBefore == -1 || indexOfPageTobeBefore < index) {
                        // dont know what to do
                    } else {
                        newUpdate.add(it)
                    }
                }
                if(!newUpdate.contains(value)) {
                    newUpdate.add(value)
                }
            }
            newUpdate
        }
    }

    println(newIncorrect.sumOf {
        it[it.size/2]
    })

}

private fun List<Long>.isCorrect(
    rules: MutableMap<Long, MutableList<Long>>
) = mapIndexed { index, value ->
    val pagesToBeBefore = rules[value] ?: emptyList()
    pagesToBeBefore.all {
        val indexOfPageTobeBefore = indexOf(it)
        indexOfPageTobeBefore == -1 || indexOfPageTobeBefore < index
    }
}.all { it }