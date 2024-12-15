package utils

data class WordOccurrence(
    val startPosition: Point,
    val direction: Direction
) {
    fun positionOfNLetter(n: Int) = startPosition.moveInDirection(direction, n)
}

fun List<List<Char>>.findWordOccurrences(
    wordToSearch: String,
    directions: Set<Direction> = Direction8.entries.toSet()
): Set<WordOccurrence> {
    val foundOccurrences = mutableSetOf<WordOccurrence>()
    forEachIndexedOnMatrix { currentPoint, character ->
        if (character == wordToSearch.first()) {
            directions.forEach { direction ->
                getWordInDirection(
                    startingPoint = currentPoint,
                    direction = direction,
                    length = wordToSearch.length
                ).takeIf { it == wordToSearch }?.also {
                    foundOccurrences.add(
                        WordOccurrence(
                            startPosition = currentPoint,
                            direction = direction
                        )
                    )
                }
            }
        }
    }
    return foundOccurrences
}

fun List<List<Char>>.findWordOccurrences(
    regexToSearch: Regex,
    length: Int,
    directions: Set<Direction> = Direction8.entries.toSet()
): Set<WordOccurrence> {
    val foundOccurrences = mutableSetOf<WordOccurrence>()
    forEachIndexedOnMatrix { currentPoint, character ->
            directions.forEach { direction ->
                getWordInDirection(
                    startingPoint = currentPoint,
                    direction = direction,
                    length = length
                ).takeIf { it.matches(regexToSearch) }?.also {
                    foundOccurrences.add(
                        WordOccurrence(
                            startPosition = currentPoint,
                            direction = direction
                        )
                    )
                }
        }
    }
    return foundOccurrences
}

private fun List<List<Char>>.getWordInDirection(startingPoint: Point, direction: Direction, length: Int): String {
    return (0..<length).mapNotNull {
        val currPoint = startingPoint.moveInDirection(
            direction = direction,
            distance = it,
        )
        getValueAtOrNull(currPoint)
    }.joinToString("")
}