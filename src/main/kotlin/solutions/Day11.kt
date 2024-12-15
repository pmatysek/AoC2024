package solutions

import utils.*
import java.io.File
import java.io.PrintWriter

fun main() {
    val input = "day11".getInputLines().first().split(" ").map { it.toULong() }
    part1(input, true, 6)

    //part2
    // works as well for part1
    val result = input.sumOf { calculateNumberOfChilds(it, 75) }
    println(result)

}
var mermaidIndex = 0UL
fun nextMermaidIndex() = mermaidIndex.apply { mermaidIndex++ }
private fun part1(input: List<ULong>, withVisualization: Boolean = false, iterations: Int = 25) {
    var file: PrintWriter? = null
    if (withVisualization) {
        file = File("day11.MD").printWriter()
        file.println("```mermaid")
        file.println("flowchart TD")
    }
    var newArrangement = input.map { it to nextMermaidIndex() }
    repeat(iterations) {
         newArrangement = newArrangement.flatMap {(value, mermaidId) ->
            val stringValue = value.toString()
            when {
                value == 0UL -> {
                    val newMermaidId = nextMermaidIndex()
                    listOf(1UL to newMermaidId).also {
                        if(withVisualization) {
                            file!!.println("$mermaidId[$value] --> $newMermaidId[${0UL}]")
                        }
                    }
                }

                stringValue.length % 2 == 0 -> {
                    val newMermaidId1 = nextMermaidIndex()
                    val newMermaidId2 = nextMermaidIndex()
                    val (value1, value2) = stringValue.chunked(stringValue.length / 2).map { it.toULong() }
                    listOf(value1 to newMermaidId1, value2 to newMermaidId2).also {
                        if(withVisualization) {
                            file!!.println("$mermaidId[$value] --> $newMermaidId1[$value1]")
                            file!!.println("$mermaidId[$value] --> $newMermaidId2[$value2]")
                        }
                    }
                }

                else -> {
                    val newMermaidId = nextMermaidIndex()
                    listOf(value * 2024UL to newMermaidId).also {
                        if(withVisualization) {
                            file!!.println("$mermaidId[$value] --> $newMermaidId[${value * 2024UL}]")
                        }
                    }
                }
            }
        }
    }
    println(newArrangement.size)
    if (withVisualization) {
        file!!.close()
    }
}

private val cache11 = mutableMapOf<Pair<ULong, Int>, ULong>()
private fun calculateNumberOfChilds(stone: ULong, iterationsToGo: Int): ULong {
    if (iterationsToGo == 1) { return blink(stone).size.toULong() }
    cache11[stone to iterationsToGo]?.let { return it }
    return blink(stone).sumOf { newStone ->
        calculateNumberOfChilds(newStone, iterationsToGo - 1).also {
            cache11[newStone to iterationsToGo - 1] = it
        }
    }
}
private fun blink(stone: ULong): List<ULong> {
    val stringValue = stone.toString()
    return when  {
        stone == 0UL -> listOf(1UL)
        stringValue.length % 2 == 0 -> {
            listOf(
                stringValue.substring(0, stringValue.length / 2).toULong(),
                stringValue.substring(stringValue.length / 2, stringValue.length).toULong()
            )
        }
        else -> listOf(stone * 2024UL)
    }
}