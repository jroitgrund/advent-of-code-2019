package common

import com.google.common.io.CharSource
import com.google.common.io.Resources
import java.nio.charset.StandardCharsets

fun <T> printSolutions(clazz: Class<T>, solution: Solution) {
    val input = getInput(clazz)
    println(solution.part1(input))
    println(solution.part2(input))
}

private fun <T> getInput(clazz: Class<T>): CharSource {
    return Resources.asCharSource(
            Resources.getResource(clazz, "input.txt"),
            StandardCharsets.UTF_8);
}