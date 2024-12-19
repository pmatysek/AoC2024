package solutions

import utils.*
import kotlin.math.pow

fun main() {
    val (input1, input2) = "day17".getInputLines().chunkedByEmptyLine()
    val (registerA, registerB, registerC) = input1.map { it.drop(12).toLong() }
    val program = input2.first().drop(9).split(",").map { it.toInt() }
    val output = getOutput(registerA, registerB, registerC, program)
    println(output)
    val programsToRun = arrayDequeOf<Pair<Long, Int>>()
    fun addRange(range: LongRange, index: Int) = programsToRun.addAll(range.map { it to index })
    addRange(1..7L, index = program.lastIndex)
    var part2Result = 0L
    while (programsToRun.isNotEmpty()) {
        println(programsToRun.size)
        val (possibleA, index) = programsToRun.removeFirst()
        val result = getOutput(possibleA, 0, 0, program).first()
        if (result == program[index]) {
            if (index == 0) {
                part2Result = possibleA
                break
            }
            else addRange((possibleA * 8)..<((possibleA + 1) * 8), index - 1)
        }
    }
    println(part2Result)
}
/**
 * Program: A = 8 * i + [ ]
 * 2,4, B = A % 8
 * 1,6, B = B xor 6
 * 7,5, C = A / 2^B
 * 4,4, B = B xor C
 * 1,7, B = B xor 7
 * 0,3, A = A / 8
 * 5,5, print B % 8
 * 3,0 goto 0 or end if A = 0
 **/


private fun getOutput(
    registerA: Long,
    registerB: Long,
    registerC: Long,
    program: List<Int>
): MutableList<Int> {
    var registerA1 = registerA
    var registerB1 = registerB
    var registerC1 = registerC
    var output = mutableListOf<Int>()
    var pointer = 0
    fun getComboOperand(operand: Int) = when (operand) {
        4 -> registerA1
        5 -> registerB1
        6 -> registerC1
        7 -> throw IllegalArgumentException()
        else -> operand.toLong()
    }
    while (pointer < program.indices.last) {
        if(output.size > program.size) {
            return mutableListOf(0)
        }
        val instruction = program[pointer]
        val operand = program[pointer + 1]
        when (instruction) {
            0 -> registerA1 = (registerA1 / 2.0.pow(getComboOperand(operand).toDouble())).toLong()
            1 -> registerB1 = registerB1 xor operand.toLong()
            2 -> registerB1 = getComboOperand(operand) % 8
            3 -> if (registerA1 != 0L) {
                pointer = operand
                continue
            }

            4 -> registerB1 = registerB1 xor registerC1
            5 -> output.add((getComboOperand(operand) % 8).toInt())
            6 -> registerB1 = (registerA1 / 2.0.pow(getComboOperand(operand).toDouble())).toLong()
            7 -> registerC1 = (registerA1 / 2.0.pow(getComboOperand(operand).toDouble())).toLong()
        }
        pointer += 2
    }
    return output
}