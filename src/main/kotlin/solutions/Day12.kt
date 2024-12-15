package solutions

import utils.*
import java.io.File
import java.io.PrintWriter
import java.math.BigDecimal

fun main() {
    val input = "day12".getInputLines().map { it.toCharArray().toList() }
    val regions = Regions()
    input.forEachIndexedOnMatrix { point, plant ->
        regions.addPoint(point, plant)
    }
    val prices = regions.regions.flatMap { it.value }.map { it.price() }
    println(
        prices.sum()
    )
    val prices2 = regions.regions.flatMap { it.value }.map { it.plant to it.price2() }
    println(
        prices2.sumOf { it.second }
    )
}

data class Region(
    val plant: Char,
    val points: MutableSet<Point> = mutableSetOf(),
) {
    private fun area() = points.count()

    private fun outSidePerimeterPoints() = points.flatMap { point ->
        Direction4.entries.map { point.moveInDirection(it) }.filter { !points.contains(it) }.toSet()
    }

    private fun perimeter(): Int {
        return outSidePerimeterPoints().size
    }

     fun numberOfSides(): Int {
         val outSidePerimeterPoints = points.flatMap { point ->
             Direction4.entries.mapNotNull {
                 val moved = point.moveInDirection(it)
                 if (!points.contains(moved)) {
                     point.moveInDirectionOfFloatSize(it, 0.25)
                 } else {
                     null
                 }
             }
         }
         val pointsCount: MutableMap<BigDecimalPoint, Int> = mutableMapOf()
         val pointsToAdd = outSidePerimeterPoints.map { point ->
             if(point in pointsCount){
                 pointsCount[point] = pointsCount[point]!! +  1
             } else {
                 pointsCount[point] = 1
             }
             point to pointsCount[point]!!
         }
         val newRegions = BigDecimalRegions()
         pointsToAdd.forEach { (p, idx) -> newRegions.addPoint(p, 'R') } // idx.toChar()
         return newRegions.regions.flatMap { it.value }.size
     }

    fun price(): Int = area() * perimeter()
    fun price2(): Int = area() * numberOfSides()
}

data class Regions(
    val regions: MutableMap<Char, MutableList<Region>> = mutableMapOf()
) {
    fun addPoint(point: Point, plant: Char) {
        if (!regions.contains(plant)) {
            regions[plant] = mutableListOf(
                Region(plant, mutableSetOf(point))
            )
            return
        }
        val plantRegions = regions[plant]!!
        val matchingRegions = plantRegions.filter {
            it.points.any { existingPoint ->
                manhattanDistance(existingPoint, point) == 1
            }
        }
        when {
            matchingRegions.isEmpty() -> {
                regions[plant]!!.add(
                    Region(plant, mutableSetOf(point))
                )
            }

            matchingRegions.size == 1 -> {
                matchingRegions.first().points.add(point)
            }

            else -> {
                val allPoints = matchingRegions.flatMap { it.points }.toMutableSet()
                allPoints.add(point)
                regions[plant]!!.removeAll(matchingRegions)
                regions[plant]!!.add(
                    Region(plant, allPoints)
                )
            }
        }
    }
}

data class BigDecimalRegion(
    val plant: Char,
    val points: MutableSet<BigDecimalPoint> = mutableSetOf(),
)
data class BigDecimalRegions(
    val regions: MutableMap<Char, MutableList<BigDecimalRegion>> = mutableMapOf()
) {
    fun addPoint(point: BigDecimalPoint, plant: Char) {
        if (!regions.contains(plant)) {
            regions[plant] = mutableListOf(
                BigDecimalRegion(plant, mutableSetOf(point))
            )
            return
        }
        val plantRegions = regions[plant]!!
        val matchingRegions = plantRegions.filter {
            it.points.any { existingPoint ->
                manhattanDistance(existingPoint, point).compareTo(BigDecimal.ONE) == 0
            }
        }
        when {
            matchingRegions.isEmpty() -> {
                regions[plant]!!.add(
                    BigDecimalRegion(plant, mutableSetOf(point))
                )
            }

            matchingRegions.size == 1 -> {
                matchingRegions.first().points.add(point)
            }

            else -> {
                val allPoints = matchingRegions.flatMap { it.points }.toMutableSet()
                allPoints.add(point)
                regions[plant]!!.removeAll(matchingRegions)
                regions[plant]!!.add(
                    BigDecimalRegion(plant, allPoints)
                )
            }
        }
    }
}