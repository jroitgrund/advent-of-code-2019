package day2

import com.google.common.io.CharSource
import common.Solution
import common.printSolutions

private const val HALT = 99;
private const val ADD = 1;
private const val MULT = 2;

fun main() {
    printSolutions(object {}.javaClass, Day2Solution)
}

object Day2Solution: Solution {

    override fun part1(input: CharSource): Int {
        val initialState = initialState(input)
        return runProgram(initialState, 12, 2)
    }

    override fun part2(input: CharSource): Int {
        val initialState = initialState(input)

        val nounVerbPairs = generateSequence((0 to 0), { (a, b) ->
            when {
                b < 99 -> (a to b + 1)
                else -> (a + 1 to 0)
            }
        })

        val (noun, verb) = nounVerbPairs
                .takeWhile { (a, b) -> a <= 99 }
                .find { (a, b) ->
                    runProgram(initialState, a, b) == 19690720
                }!!

        return 100 * noun + verb;
    }
}

private fun initialState(input: CharSource): List<Int> {
    val initialState = input.readFirstLine()!!.split(",").asSequence().map(String::toInt).toList()
    return initialState
}

private fun runProgram(initialState: List<Int>, noun: Int, verb: Int): Int {
    val state = initialState.toMutableList()
    state[1] = noun
    state[2] = verb

    var position = 0

    loop@ while (true) {
        val opcode = state[position]
        val operation: (Int, Int) -> Int = when (opcode) {
            HALT -> break@loop
            ADD -> Int::plus
            MULT -> Int::times
            else -> throw IllegalAccessException("Unexpected opcode $opcode")
        }

        state[state[position + 3]] = operation.invoke(
                state[state[position + 1]],
                state[state[position + 2]])

        position += 4
    }

    return state[0]
}