package solutions

import utils.*
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val (input1, input2) = "day15".getInputLines().chunkedByEmptyLine()
    val map = input1.map { line -> line.toCharArray().toMutableList() }.toMutableList()
    val instructions = input2.joinToString("")
    var currPosition = map.findCoordinatesOf('@')
    var rocks = map.mapIndexedOnMatrix { index, c ->
        if (c == 'O') {
            index
        } else {
            null
        }
    }.flatten().filterNotNull().toSet()
    val walls = map.mapIndexedOnMatrix { index, c ->
        if (c == '#') {
            index
        } else {
            null
        }
    }.flatten().filterNotNull().toSet()
    instructions.forEach { instruction ->
        val direction = instruction.toDirection()
        var nextPosition = currPosition
        val rocksToMove = mutableListOf<Point>()
        // printMap(map, rocks, walls, currPosition)
        // println()
        while (true) {
            nextPosition = nextPosition.moveInDirection(direction)
            when (nextPosition) {
                in rocks -> rocksToMove.add(nextPosition)
                in walls -> {
                    break
                }

                else -> {
                    currPosition = currPosition.moveInDirection(direction)
                    rocks = rocks.map {
                        if (it in rocksToMove) {
                            it.moveInDirection(direction)
                        } else {
                            it
                        }
                    }.toSet()
                    break
                }
            }
        }
    }
    printMap(map, rocks, walls, currPosition)
    println(rocks.sumOf { it.first * 100 + it.second })

    //part2
    val map2 = map.mapIndexedOnMatrix { index, c ->
        when (c) {
            '.' -> "..".toList()
            '#' -> "##".toList()
            'O' -> "[]".toList()
            '@' -> "@.".toList()
            else -> throw IllegalArgumentException().also { println("Illegal character: $c") }
        }
    }.map { it.flatten() }
    map2.forEach { line ->
        line.forEach { c ->
            print(c)
        }
        println()
    }
    var currPosition2 = map2.findCoordinatesOf('@')
    var rocks2 = map2.mapIndexedOnMatrix { index, c ->
        if (c == '[') {
            index to index.moveInDirection(Direction4.RIGHT)
        } else {
            null
        }
    }.flatten().filterNotNull().toSet()
    val walls2 = map2.mapIndexedOnMatrix { index, c ->
        if (c == '#') {
            index
        } else {
            null
        }
    }.flatten().filterNotNull().toSet()

    lateinit var nextPosition: Point
    instructions.forEach { instruction ->
        val direction = instruction.toDirection()
        nextPosition = currPosition2.moveInDirection(direction)
//        println(instruction)
//        println()
//        printMap2(map2, rocks2, walls2, currPosition2)
//        println()
        try {
            val rocksToMove = findRocksToMove(rocks2, nextPosition, direction, walls2)
            rocks2 = rocks2.map {
                if (it in rocksToMove) {
                    it.first.moveInDirection(direction) to it.second.moveInDirection(direction)
                } else {
                    it
                }
            }.toSet()
            currPosition2 = currPosition2.moveInDirection(direction)
        } catch (_: HitWallException) {
            //cant move
        }

    }
    printMap2(map2, rocks2, walls2, nextPosition)
    println(rocks2.sumOf { it.first.first * 100 + it.first.second })
}

fun findRocksToMove(
    rocks: Set<Pair<Point, Point>>,
    position: Point,
    direction: Direction,
    walls: Set<Point>
): Set<Pair<Point, Point>> {
    if (position in walls) {
        throw HitWallException()
    }
    val rock = rocks.firstOrNull { it.first == position || it.second == position }
    if (rock == null) {
        return emptySet()
    }
    val listToReturn = mutableListOf<List<Pair<Point, Point>>>()
    listToReturn.add(listOf(rock))
    if (direction == Direction4.LEFT) {
        listToReturn.add(findRocksToMove(rocks, rock.first.moveInDirection(direction), direction, walls).toList())
    }
    if (direction == Direction4.RIGHT) {
        listToReturn.add(findRocksToMove(rocks, rock.second.moveInDirection(direction), direction, walls).toList())
    }
    if (direction == Direction4.UP || direction == Direction4.DOWN) {
        listToReturn.add(findRocksToMove(rocks, rock.first.moveInDirection(direction), direction, walls).toList())
        listToReturn.add(findRocksToMove(rocks, rock.second.moveInDirection(direction), direction, walls).toList())
    }
    return listToReturn.flatten().toSet()
}

class HitWallException : RuntimeException()

private fun printMap(
    map: MutableList<MutableList<Char>>,
    rocks: Set<Point>,
    walls: Set<Point>,
    currPosition: Point
) {
    map.forEachIndexedOnMatrix { position, c ->
        when (position) {
            in rocks -> {
                print("0")
            }

            in walls -> {
                print("#")
            }

            currPosition -> {
                print("@")
            }

            else -> print(".")
        }
        if (position.second == map.first().indices.last()) {
            println()
        }
    }
}

private fun printMap2(
    map: List<List<Char>>,
    rocks: Set<Pair<Point, Point>>,
    walls: Set<Point>,
    currPosition: Point
) {
    map.forEachIndexedOnMatrix { position, c ->
        when (position) {
            in rocks.map { it.first } -> {
                print("[")
            }

            in rocks.map { it.second } -> {
                print("]")
            }

            in walls -> {
                print("#")
            }

            currPosition -> {
                print("@")
            }

            else -> print(".")
        }
        if (position.second == map.first().indices.last()) {
            println()
        }
    }
}


private fun Char.toDirection() = when (this) {
    '>' -> Direction4.RIGHT
    '^' -> Direction4.UP
    '<' -> Direction4.LEFT
    'v' -> Direction4.DOWN
    else -> throw IllegalArgumentException("Invalid direction")
}