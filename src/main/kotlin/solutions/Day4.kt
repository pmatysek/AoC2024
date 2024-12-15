package solutions

import utils.*
import utils.Direction8.*

fun main() {
    val matrix = "day4".getInputLines().map { it.toCharArray().toList() }
    val foundWords = matrix.findWordOccurrences(
        wordToSearch = "XMAS"
    )
    println(foundWords.size)

    val foundWords2 = matrix.findWordOccurrences(
        wordToSearch = "MAS",
        directions = setOf(
            UP_LEFT,
            UP_RIGHT,
            DOWN_LEFT,
            DOWN_RIGHT
        )
    ).toList()
    println(
        foundWords2.combinations()
            .count { it.first.positionOfNLetter(1) ==  it.second.positionOfNLetter(1)}
    )
}