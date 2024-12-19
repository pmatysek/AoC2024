package solutions

import utils.*
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

private const val MAX_X = 100
private const val MAX_Y = 102
fun main() {
    var robots = "day14".getInputLines().map { line ->
        val (p, v) = line.split(" ").map { it.drop(2).split(",") }
        Robot(
            position = p.first().toInt() to p.second().toInt(),
            velocity = v.first().toInt() to v.second().toInt(),
        )
    }
    repeat(10000) {
        val sumOfDistances = robots.map { it.position }.combinations().sumOf { cartesianDistance(it.first, it.second) }
        println("Iterations: $it --- sum of distances: $sumOfDistances")
        if (sumOfDistances < 5000000) {
            println("----------")
            println("Iterations: $it")
            robots.print()
            robots.printAsImage("$it")
        }
        robots = robots.map {
            Robot(
                velocity = it.velocity,
                position = it.position.moveByVectorWithOverlap(it.velocity, MAX_X, MAX_Y),
            )
        }
    }
    val q1 = robots.filter { robot ->
        val position = robot.position
        position.first in 0..<MAX_X / 2 && position.second in 0..<MAX_Y / 2
    }
    val q2 = robots.filter { robot ->
        val position = robot.position
        position.first in (MAX_X / 2) + 1..MAX_X && position.second in (MAX_Y / 2) + 1..MAX_Y
    }
    val q3 = robots.filter { robot ->
        val position = robot.position
        position.first in (MAX_X / 2) + 1..MAX_X && position.second in 0..<MAX_Y / 2
    }
    val q4 = robots.filter { robot ->
        val position = robot.position
        position.first in 0..<MAX_X / 2 && position.second in (MAX_Y / 2) + 1..MAX_Y
    }
    println(
        q1.count() * q2.count() * q3.count() * q4.count()
    )


}

data class Robot(
    val position: Point,
    val velocity: Point
)

private fun List<Robot>.print() {
    val robotsAtPointsCount = map { robot -> robot.position }.toFrequencyMap()
    (0..MAX_Y).forEach { i ->
        (0..MAX_X).forEach { j ->
            if (robotsAtPointsCount.containsKey(j to i)) {
                print("${robotsAtPointsCount[j to i]} ")
            } else {
                print(". ")
            }
        }
        println()
    }
}

private fun List<Robot>.printAsImage(imageName: String) {
    val img = BufferedImage(MAX_X + 1, MAX_Y + 1, BufferedImage.TYPE_INT_RGB)
    forEach { robot ->
        img.setRGB(robot.position.first, robot.position.second, 0x7CFC00)
    }
    ImageIO.write(img, "BMP", File("robots/frame$imageName.bmp"))
}