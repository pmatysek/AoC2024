package solutions

import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Test
import utils.moveByVectorWithOverlap

class VectorTest {
    @Test
    fun `should move by vector on minus with overlap`() {
        // given
        val point = 0 to 0
        val maxX = 10
        val maxY = 10

        // when
        val resultPoint = point.moveByVectorWithOverlap(-1 to -1, maxX, maxY)

        // then
        resultPoint shouldBeEqual (maxX to maxY)
    }

    @Test
    fun `should move by vector on above with overlap`() {
        // given
        val point = 10 to 10
        val maxX = 10
        val maxY = 10

        // when
        val resultPoint = point.moveByVectorWithOverlap(1 to 1, maxX, maxY)

        // then
        resultPoint shouldBeEqual (0 to 0)
    }
}

