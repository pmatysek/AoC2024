package utils

import java.io.File

fun String.getInputLines() = File("src/main/resources/$this.input").readLines()
fun String.getInputAsSingleLine() = File("src/main/resources/$this.input").readText()

fun List<String>.chunkedByEmptyLine() = this.chunkedBy("")
fun List<String>.chunkedBy(delimiter: String) = this.joinToString("??").split("??$delimiter??").map { it.split("??") }
fun List<String>.splittedToLong(vararg delimiters: String = arrayOf(" ")) = map { it.split(*delimiters).map { it.toLong() } }
fun List<String>.splittedToULong(vararg delimiters: String = arrayOf(" ")) = map { it.split(*delimiters).map { it.toULong() } }

fun List<String>.splittedToSignedLongExcludingNonDigits(vararg delimiters: String = arrayOf(" ")) = map { it.split(*delimiters).map { it.toSignedNumberExcludingNonDigits() } }
fun List<String>.splittedToUnSignedLongExcludingNonDigits(vararg delimiters: String = arrayOf(" ")) = map { it.split(*delimiters).map { it.toNumberExcludingNonDigits() } }
