package utils

fun findCommonCharsInStrings(strings: List<String>) =
    strings.reduce { acc, next -> acc.toSet().intersect(next.toSet()).joinToString() }

fun String.findFirstUniqueSequenceOf(n: Int) = this.windowed(n).indexOfFirst { it.allCharsUnique() }

fun String.allCharsUnique(): Boolean = all(hashSetOf<Char>()::add)

fun String.takeIfMatchesRegex(regex: Regex): String? = this.takeIf { regex.matches(it) }

fun List<Char>.takeIfMatchesRegex(regex: Regex): List<Char>? =
    this.joinToString("").takeIf { regex.matches(it) }?.toCharArray()?.toList()

fun Collection<Char>.toNumberExcludingNonDigits() = this
    .filter { it.isDigit() }
    .joinToString("")
    .toInt()

fun String.toNumberExcludingNonDigits() = this
    .filter { it.isDigit() }
    .toLong()

fun String.toSignedNumberExcludingNonDigits() = this
    .filter { it.isDigit() || it == '-' }
    .toInt()

fun String.toULongExcludingNonDigits() = this
    .filter { it.isDigit() }
    .toULong()

fun String.splittedToSignedLongExcludingNonDigits(vararg delimiters: String = arrayOf(" ")) = split(*delimiters).map { it.toSignedNumberExcludingNonDigits().toLong() }
fun String.splittedToUnsignedLongExcludingNonDigits(vararg delimiters: String = arrayOf(" ")) = split(*delimiters).map { it.toNumberExcludingNonDigits() }
