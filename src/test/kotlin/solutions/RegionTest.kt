package solutions

import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Test

class RegionTest {
    @Test
    fun `should calculate proper number of slides fo square`() {
        // given
        val region = Region(
            plant = 'A',
            points = mutableSetOf(
                0 to 0,
                0 to 1,
                1 to 0,
                1 to 1,
            )
        )

        // when
        val numberOfSlides = region.numberOfSides()

        // then
        numberOfSlides shouldBeEqual 4
    }

    @Test
    fun `should calculate proper number of slides for irregular shape`() {
        // given
        val region = Region(
            plant = 'A',
            points = mutableSetOf(
                0 to 0,
                0 to 1,
                0 to 2,
                1 to 0,
                2 to 0,
                2 to 1,
                2 to 2,
            )
        )

        // when
        val numberOfSlides = region.numberOfSides()

        // then
        numberOfSlides shouldBeEqual 8
    }

    @Test
    fun `should calculate proper number of slides for region in E shape`() {
        // given
        val region = Region(
            plant = 'A',
            points = mutableSetOf(
                0 to 0,
                0 to 1,
                0 to 2,
                1 to 0,
                2 to 0,
                2 to 1,
                2 to 2,
                3 to 0,
                4 to 0,
                4 to 1,
                4 to 2,
            )
        )

        // when
        val numberOfSlides = region.numberOfSides()

        // then
        numberOfSlides shouldBeEqual 12
    }

    @Test
    fun `should calculate proper number of slides fo region F from example`() {
        // given
        val region = Region(
            plant = 'F',
            points = mutableSetOf(
                0 to 8,
                0 to 9,
                1 to 9,
                2 to 7,
                2 to 8,
                2 to 9,
                3 to 7,
                3 to 8,
                3 to 9,
                4 to 8
            )
        )

        // when
        val numberOfSlides = region.numberOfSides()

        // then
        numberOfSlides shouldBeEqual 12
    }
}

