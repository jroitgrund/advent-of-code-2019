package day1

import com.google.common.io.CharSource
import common.getInput
import kotlin.streams.asSequence

fun main() {
    val input = getInput(object {}.javaClass);

    println(part1(input));
    println(part2(input));
}

private fun part1(input: CharSource): Int {
    return input.lines()
            .asSequence()
            .map(String::toInt)
            .map { it / 3 - 2 }
            .sum();
}

private fun part2(input: CharSource): Int {
    return input.lines()
            .asSequence()
            .map(String::toInt)
            .map { it / 3 - 2 }
            .map(::getTotalFuel)
            .sum();
}

private fun getTotalFuel(initialFuel: Int): Int {
    return generateSequence(initialFuel, { it / 3 - 2})
            .takeWhile { it > 0 }
            .sum();
}