package solutions

import utils.*

fun main() {
    val input = "day8".getInputLines().map { it.toCharArray().toList() }
    val antennasLocations = input.mapIndexedOnMatrix { location, value ->
        if (value != '.') {
            Antenna(
                location = location,
                frequency = value
            )
        } else {
            null
        }
    }.flatten().filterNotNull()

    val sameAntennas = antennasLocations.combinations()
        .filter { it.first.frequency == it.second.frequency && it.first.location != it.second.location}
    val antiNodes = sameAntennas.map {
        val distance = it.first.location - it.second.location
        listOf((it.first.location.moveByVector(distance)), (it.second.location.moveByVector(distance * -1)))
    }.flatten().filter { it.isOnMatrix(input) }
    println(antiNodes.toSet().count())


    val antiNodes2 = sameAntennas.map {
        val distance = it.first.location - it.second.location
        var antinode1 = it.first.location.moveByVector(distance)
        var antinode2 = it.second.location.moveByVector(distance * -1)
        val newAntinodes = mutableListOf(antinode1, antinode2)
        while (antinode1.isOnMatrix(input) || antinode2.isOnMatrix(input)) {
            antinode1 = antinode1.moveByVector(distance)
            antinode2 = antinode2.moveByVector(distance * -1)
            newAntinodes.add(antinode1)
            newAntinodes.add(antinode2)
        }
        newAntinodes.add(it.first.location)
        newAntinodes.add(it.second.location)
        newAntinodes

    }.flatten().filter { it.isOnMatrix(input) }
    println(antiNodes2.toSet().count())
}


data class Antenna(
    val location: Point,
    val frequency: Char,
)
