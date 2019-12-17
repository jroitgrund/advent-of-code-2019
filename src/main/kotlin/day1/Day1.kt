package day1

import com.google.common.io.CharSource
import common.Solution
import common.printSolutions
import kotlin.streams.asSequence

fun main() {
    printSolutions(object {}.javaClass, Day1Solution)
}

object Day1Solution: Solution {
     override fun part1(input: CharSource): Int {
        return input.lines()
                .asSequence()
                .map(String::toInt)
                .map { it / 3 - 2 }
                .sum();
    }

     override fun part2(input: CharSource): Int {
        return input.lines()
                .asSequence()
                .map(String::toInt)
                .map { it / 3 - 2 }
                .map(::getTotalFuel)
                .sum();
    }
}

private fun getTotalFuel(initialFuel: Int): Int {
    return generateSequence(initialFuel, { it / 3 - 2})
            .takeWhile { it > 0 }
            .sum();
}