package solutions

import utils.*
import java.io.File
import java.io.PrintWriter


/*
* Button A: X+46, Y+19
* Button B: X+16, Y+38
* Prize: X=3836, Y=2432
 */
fun main() {
    val input = "day13".getInputLines().chunkedByEmptyLine().map { (buttonA, buttonB, prize) ->
        val (buttonAx, buttonAy) = buttonA.removePrefix("Button A: ").split(", ").map { it.toULongExcludingNonDigits() }
        val (buttonBx, buttonBy) = buttonB.removePrefix("Button B: ").split(", ").map { it.toULongExcludingNonDigits() }
        val (prizeX, prizeY) = prize.removePrefix("Prize: ").split(", ").map { it.toULongExcludingNonDigits() }
        Machine(
            buttonA = buttonAx to buttonAy,
            buttonB = buttonBx to buttonBy,
            prize = prizeX to prizeY,
        )
    }
    val numOfTickets = input.map { it.numberOfTicketsNeeded2(0UL) }
    println(numOfTickets.sum())


    val numOfTickets2 = input.map { it.numberOfTicketsNeeded2(10000000000000UL) }
    println(numOfTickets2.sum())
}

private data class Machine(
    val buttonA: ULongPoint,
    val buttonB: ULongPoint,
    val prize: ULongPoint,
) {
    fun numberOfTicketsNeeded(): ULong {
        val currPosition: ULongPoint = 0uL to 0uL
        val winningCombinations = mutableListOf<ULong>()
        (0..Int.MAX_VALUE).forEach { i ->
            val movedByA = currPosition.moveByVectorULong(buttonA * i.toULong() )
            if(prize.first < movedByA.first || prize.second < movedByA.second) {
                return winningCombinations.minOrNull() ?: 0UL
            }
            run lit@{
                (0..Int.MAX_VALUE).forEach { j ->
                    val movedByB = currPosition.moveByVectorULong(buttonB * j.toULong() )
                    if(prize.first < movedByB.first || prize.second < movedByB.second) {
                        return@lit
                    }
                    val position = currPosition.moveByVectorULong(buttonA * i.toULong() ).moveByVectorULong(buttonB * j.toULong())
                    if(position == prize) {
                        winningCombinations.add(3UL * i.toULong() + j.toULong())
                    }
                }
            }
        }
        return winningCombinations.minOrNull() ?: 0UL
    }

    fun numberOfTicketsNeeded2(d: ULong = 0UL): Long {
        val pxd = prize.first + d
        val pyd = prize.second + d

        val na = (pyd * buttonB.first - pxd * buttonB.second).toLong() /
                (buttonA.second * buttonB.first - buttonA.first * buttonB.second).toLong()

        val nb = (pxd - buttonA.first * na.toULong()).toLong() / buttonB.first.toLong()

        return if (na.toULong() * buttonA.first + nb.toULong() * buttonB.first == pxd &&
            na.toULong() * buttonA.second + nb.toULong() * buttonB.second == pyd) {
            na * 3 + nb
        } else {
            0
        }
    }
}