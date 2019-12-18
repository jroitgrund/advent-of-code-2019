package day4;

import com.google.common.io.CharSource
import common.Solution
import common.printSolutions

fun main() {
        printSolutions(object {}.javaClass, Day4Solution)
}

object Day4Solution : Solution {
        override fun part1(input: CharSource): Int {
                val rangeParts = input.readFirstLine()!!.split("-")
                val rangeStart = rangeParts[0].toLong()
                val rangeEnd = rangeParts[1].toLong()
                return (rangeStart..rangeEnd).asSequence()
                        .map(::numberMetadata)
                        .filter { it.hasTwoConsecutiveIdenticalNumbers && !it.hasDecreased }
                        .count()
        }

        override fun part2(input: CharSource): Int {
                val rangeParts = input.readFirstLine()!!.split("-")
                val rangeStart = rangeParts[0].toLong()
                val rangeEnd = rangeParts[1].toLong()
                return (rangeStart..rangeEnd).asSequence()
                        .map(::numberMetadata)
                        .filter { it.hasTwoConsecutiveIdenticalNumbersNotPartOfGroup && !it.hasDecreased }
                        .count()
        }

}

data class NumberMetadataIterator(
        val lastDigit: Int,
        val hasDecreased: Boolean,
        val hasTwoConsecutiveIdenticalNumbers: Boolean,
        val lastTwoNumbersIdentical: Boolean,
        val moreThanTwoNumbersIdentical: Boolean,
        val hasTwoConsecutiveIdenticalNumbersNotPartOfGroup: Boolean) {

        fun next(n: Int): NumberMetadataIterator {
                return NumberMetadataIterator(
                        n,
                        hasDecreased || lastDigit > n,
                        hasTwoConsecutiveIdenticalNumbers || lastDigit == n,
                        !moreThanTwoNumbersIdentical && lastDigit == n,
                        (moreThanTwoNumbersIdentical || lastTwoNumbersIdentical) && lastDigit == n,
                        hasTwoConsecutiveIdenticalNumbersNotPartOfGroup
                                || (lastTwoNumbersIdentical && !moreThanTwoNumbersIdentical && lastDigit != n))
        }
}

data class NumberMetadata(
        val hasDecreased: Boolean,
        val hasTwoConsecutiveIdenticalNumbers: Boolean,
        val hasTwoConsecutiveIdenticalNumbersNotPartOfGroup: Boolean)

fun numberMetadata(n: Long): NumberMetadata {
        val digits = digits(n)
        val metadata = digits.fold(
                NumberMetadataIterator(
                        lastDigit = -1,
                        hasDecreased = false,
                        hasTwoConsecutiveIdenticalNumbers = false,
                        lastTwoNumbersIdentical = false,
                        moreThanTwoNumbersIdentical = false,
                        hasTwoConsecutiveIdenticalNumbersNotPartOfGroup = false),
                NumberMetadataIterator::next)

        return NumberMetadata(
                metadata.hasDecreased,
                metadata.hasTwoConsecutiveIdenticalNumbers,
                metadata.hasTwoConsecutiveIdenticalNumbersNotPartOfGroup
                        || (metadata.lastTwoNumbersIdentical && !metadata.moreThanTwoNumbersIdentical))
}

private fun digits(n: Long): List<Int> {
        val asString = n.toString()
        return (0 until asString.length).asSequence()
                .map { asString[it] - '0' }
                .toList()
}