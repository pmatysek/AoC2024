package solutions

import utils.*

fun main() {
    val input = "day9".getInputAsSingleLine().map { it.digitToInt() }
    var currPosition = 0
    val emptySpaces = mutableListOf<ULong>()
    val memoryBlocks = input.mapIndexedNotNull {index, length ->
        val isFile = index % 2 == 0
        if (length == 0) {
            null
        } else {
            if (isFile) {
                val fileId = FileMemoryBlock.getNextFileID()
                val blocks = (0..<length).map {
                    FileMemoryBlock(
                        id = fileId,
                        position = (currPosition + it).toULong()
                    )
                }
                currPosition += blocks.size
                blocks
            } else {
                (0..<length).forEach {
                    emptySpaces.add((currPosition + it).toULong())
                }
                currPosition += length
                null
            }
        }

    }.flatten()
    val properEmptySpaces = emptySpaces.filter { it < memoryBlocks.size.toULong() }.sorted().toMutableList()
    val movedMemoryBlocks = memoryBlocks.reversed().map { memoryBlock ->
        if (properEmptySpaces.isEmpty()) {
            memoryBlock
        } else {
            FileMemoryBlock(
                id = memoryBlock.id,
                position = properEmptySpaces.removeFirst() ?: memoryBlock.position,
            )
        }
    }
    val part1Result = movedMemoryBlocks.sumOf { it.id * it.position }
    require(part1Result == 6367087064415UL)
    println(part1Result)

    //part2
    var listOfIndexes = mutableListOf<ULong>()
    var freeMemoryRanges = mutableListOf<List<ULong>>()
    emptySpaces.sorted().forEachIndexed { index, emptySpace ->
        val previousEmptySpace = emptySpaces.getOrNull(index - 1)
        if (previousEmptySpace == null || emptySpace - previousEmptySpace == 1UL) {
            listOfIndexes.add(emptySpace)
        } else {
            freeMemoryRanges.add(listOfIndexes)
            listOfIndexes = mutableListOf(emptySpace)
        }
    }
    val memoryBlocksByFileID = memoryBlocks.groupBy { it.id }.toSortedMap().reversed()
    val newMemoryBlocks2 = memoryBlocksByFileID.map { (fileID, memoryBlocks) ->
        val firstMemoryRangeToFit = freeMemoryRanges.firstOrNull {
            memoryBlocks.size <= it.size && it.first() < memoryBlocks.first().position
        }
        if (firstMemoryRangeToFit == null) {
            memoryBlocks
        } else {
            val newBlocks = memoryBlocks.mapIndexed { index, fileMemoryBlock ->
                FileMemoryBlock(
                    id = fileMemoryBlock.id,
                    position = firstMemoryRangeToFit[index]
                )
            }
            val remainingFreeSpace = (firstMemoryRangeToFit - newBlocks.map { it.position }.toSet())
            freeMemoryRanges.remove(firstMemoryRangeToFit)
            if (remainingFreeSpace.isNotEmpty()) {
                freeMemoryRanges.add(remainingFreeSpace)
               freeMemoryRanges = freeMemoryRanges.sortedBy { it.first() }.toMutableList()
            }
            newBlocks
        }
    }.flatten()
    val part2Result = newMemoryBlocks2.sumOf { it.id * it.position }
    require(part2Result == 6390781891880UL)
    println(part2Result)
}

data class FileMemoryBlock(
    val id: ULong,
    val position: ULong
) {
    companion object {
        var currentFileID = 0UL
        fun getNextFileID(): ULong {
            return currentFileID.apply { currentFileID++ }
        }
    }
}