package common

import com.google.common.io.CharSource

interface Solution {
    fun part1(input: CharSource): Int
    fun part2(input: CharSource): Int
}