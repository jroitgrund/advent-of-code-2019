package day18

import com.google.common.base.Preconditions
import com.google.common.collect.Queues
import com.google.common.io.CharSource
import common.Solution
import common.printTestSolutions
import java.util.*
import kotlin.math.min
import kotlin.streams.asSequence

fun main() {
    printTestSolutions(object {}.javaClass, Day18Solution)
}

sealed class VaultTile

object Wall : VaultTile()
object ClearPath : VaultTile()
data class Key(val id: Char) : VaultTile()
data class Door(val id: Char) : VaultTile()

data class Coordinate(val x: Int, val y: Int)

data class Vault(
        val tiles: Map<Coordinate, VaultTile>,
        val startPosition: Coordinate,
        val stepsTaken: Int,
        val remainingKeys: Map<Coordinate, Key>,
        val remainingDoors: Map<Coordinate, Door>) {
    fun bestSolution(): Int {
        val vaults: Queue<Vault> = Queues.newArrayDeque()
        var bestSolution = Integer.MAX_VALUE
        vaults.offer(this)
        while (vaults.isNotEmpty()) {
            val vault = vaults.remove()
            if (vault.remainingKeys.isEmpty()) {
                bestSolution = min(vault.stepsTaken, bestSolution)
            } else {
                vault.getNextVaults().forEach { vaults.offer(it) }
            }
        }

        return bestSolution
    }

    private fun getNextVaults(): List<Vault> {
        val nextVaults = dijkstra(startPosition)
                .filterValues { it < Integer.MAX_VALUE }
                .filterKeys(remainingKeys::containsKey)
                .map { (keyCoordinate, distance) ->
                    Vault(
                            tiles,
                            keyCoordinate,
                            distance + stepsTaken,
                            remainingKeys.filterKeys { it != keyCoordinate },
                            remainingDoors.filterValues { it.id != remainingKeys[keyCoordinate]!!.id })
                }
        Preconditions.checkState(nextVaults.isNotEmpty())
        return nextVaults
    }

    private fun dijkstra(startingNode: Coordinate): Map<Coordinate, Int> {
        val distances = mutableMapOf((startingNode to 0))

        val queue = PriorityQueue<Pair<Coordinate, Int>>(compareBy { it.second })
        queue.add((startingNode to 0))
        val visited = mutableSetOf<Coordinate>()
        while (queue.isNotEmpty()) {
            val (current, _) = queue.remove()
            visited.add(current)

            val neighbours = findNeighbours(current).asSequence().filter { !visited.contains(it) }
            for (successor in neighbours) {
                val alternativeDistance = distances[current]!! + 1
                val newDistance = min(distances.getOrDefault(successor, Integer.MAX_VALUE), alternativeDistance)
                distances[successor] = newDistance
                queue.add((successor to newDistance))
            }
        }

        return distances
    }

    private fun findNeighbours(coordinate: Coordinate): Iterator<Coordinate> {
        val (x, y) = coordinate
        return sequenceOf(Coordinate(x + 1, y), Coordinate(x - 1, y), Coordinate(x, y + 1), Coordinate(x, y - 1))
                .filter(tiles::containsKey)
                .filter {
                    val tile = tiles[it]
                    tile is ClearPath || tile is Key || (tile is Door && !remainingDoors.containsValue(tile)) }
                .iterator()
    }

    companion object {
        fun fromCharSource(input: CharSource): Vault {
            var entrance: Coordinate? = null
            val tiles = input.lines().asSequence()
                    .mapIndexed { y, line ->
                        line.asSequence().mapIndexed { x, char ->
                            Coordinate(x, y) to when (char) {
                                '@' -> {
                                    entrance = Coordinate(x, y)
                                    ClearPath
                                }
                                '.' -> ClearPath
                                '#' -> Wall
                                in 'a'..'z' -> Key(char)
                                in 'A'..'Z' -> Door(char.toLowerCase())
                                else -> throw IllegalArgumentException()
                            }
                        }
                    }
                    .flatMap { it }
                    .toMap()
            val remainingKeys = tiles
                    .filterValues { it is Key }
                    .mapValues { it.value as Key }
            val remainingDoors = tiles
                    .filterValues { it is Door }
                    .mapValues { it.value as Door }
            return Vault(
                    tiles,
                    entrance!!,
                    0,
                    remainingKeys,
                    remainingDoors)
        }
    }
}

object Day18Solution : Solution {
    override fun part1(input: CharSource): Int {
        val vault = Vault.fromCharSource(input)
        return vault.bestSolution()
    }

    override fun part2(input: CharSource): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}