package day3

import com.google.common.io.CharSource
import common.Solution
import common.printSolutions
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

sealed class PathComponent

data class Down(val n: Int) : PathComponent()
data class Up(val n: Int) : PathComponent()
data class Left(val n: Int) : PathComponent()
data class Right(val n: Int) : PathComponent()

data class Point(val x: Int, val y: Int)
data class PointWithCost(val steps: Int, val x: Int, val y: Int)
data class Interval(val steps: Int, val p1: Point, val p2: Point) {
    fun minX(): Int {
        return min(p1.x, p2.x)
    }
    fun minY(): Int {
        return min(p1.y, p2.y)
    }
    fun maxX(): Int {
        return max(p1.x, p2.x)
    }
    fun maxY(): Int {
        return max(p1.y, p2.y)
    }
    fun cost(point: Point): Int {
        val (x, y) = point
        return steps + (x - p1.x).absoluteValue + (y - p1.y).absoluteValue
    }
}

fun main() {
    printSolutions(object {}.javaClass, Day3Solution)
}

object Day3Solution : Solution {
    override fun part1(input: CharSource): Int {
        val intersections = intersections(input)

        return intersections.asSequence()
                .filterNot { it.x == 0 && it.y == 0 }
                .map { (steps, x, y) -> x.absoluteValue + y.absoluteValue }
                .sorted()
                .first()!!
    }

    override fun part2(input: CharSource): Int {
        val intersections = intersections(input)

        return intersections.asSequence()
                .filterNot { it.x == 0 && it.y == 0 }
                .map { (steps, x, y) -> steps }
                .sorted()
                .first()!!
    }
}

private fun intersections(input: CharSource): Sequence<PointWithCost> {
    val lines = input.readLines()
    val path1 = getPath(lines[0])
    val path2 = getPath(lines[1])

    val allPath1Positions = positions(path1)
    val allPath2Positions = positions(path2)

    val allPath1Intervals = intervals(allPath1Positions)
    val allPath2Intervals = intervals(allPath2Positions)

    return allPath1Intervals.asSequence().flatMap { w1 ->
        allPath2Intervals.asSequence().flatMap { w2 -> listOfNotNull(intersection(w1, w2)).asSequence() }
    }
}

private fun getPath(line: String): List<PathComponent> {
    return line.split(",").asSequence()
            .map {
                when (it[0]) {
                    'D' -> Down(it.substring(1).toInt())
                    'U' -> Up(it.substring(1).toInt())
                    'L' -> Left(it.substring(1).toInt())
                    'R' -> Right(it.substring(1).toInt())
                    else -> throw IllegalArgumentException("Unexpected direction $it[0]")
                }
            }
            .toList()
}

private fun nextPosition(position: Point, pathComponent: PathComponent): Point {
    val (x, y) = position
    return when (pathComponent) {
        is Down -> Point(x, y - pathComponent.n)
        is Up -> Point(x, y + pathComponent.n)
        is Left -> Point(x - pathComponent.n, y)
        is Right -> Point(x + pathComponent.n, y)
    }
}

private fun positions(path1: List<PathComponent>): List<Point> {
    return path1.fold(
            listOf(Point(0, 0)),
            { positions, pathComponent ->
                positions + listOf(
                        nextPosition(positions[positions.size - 1], pathComponent))
            })
}

private fun intervals(positions: List<Point>): List<Interval> {
    val (_, _, intervals) = positions.asSequence().drop(1).fold(
            Triple(0, Point(0, 0), listOf()),
            { state: Triple<Int, Point, List<Interval>>, point: Point ->
                val (steps, intervalStart, intervals) = state
                val interval = Interval(steps, intervalStart, point)
                val nextSteps = steps + steps(interval)
                val nextIntervals = intervals + listOf(interval)
                Triple(nextSteps, point, nextIntervals)
            })

    return intervals
}

private fun steps(interval: Interval): Int {
    val (x1, y1) = interval.p1
    val (x2, y2) = interval.p2
    return when {
        x1 == x2 -> (y1 - y2).absoluteValue
        y1 == y2 -> (x1 - x2).absoluteValue
        else -> throw IllegalArgumentException("Points have no common axis: ($interval.p1, $interval.p2)")
    }
}

private fun intersection(w1: Interval, w2: Interval): PointWithCost? {
    return if (w1.minX() < w2.maxX()
            && w2.minX() < w1.maxX()
            && w1.minY() < w2.maxY()
            && w2.minY() < w1.maxY()) {
        val x = max(w1.minX(), w2.minX())
        val y = max(w1.minY(), w2.minY())
        val steps = w1.cost(Point(x, y)) + w2.cost(Point(x, y))
        PointWithCost(steps, x, y)
    } else {
        null
    }
}