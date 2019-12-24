package net.poquesoft.adventofcode

import java.io.File

class Advent24b(val file: String) {

    var grid = arrayOf<Array<Boolean>>()
    var recursiveGrids: MutableList<Array<Array<Boolean>>> = mutableListOf()
    var origin = 0

    fun run() {
        Time.start()
        println("Reading...")
        var lastBio = -1L
        File(file).forEachLine {
            var array = arrayOf<Boolean>()
            for (j in 0..4) {
                array += (it[j] == '#')
            }
            grid += array
        }
        recursiveGrids.add(grid)
        origin = 0
        printGrids(recursiveGrids, origin)
        println("Starting")

        for (i in 1..200) {
            println("After $i minutes")
            //add upper and lower levels
            recursiveGrids.add(0, Array(5) { Array(5) { false } })
            recursiveGrids.add(Array(5) { Array(5) { false } })
            origin++
            recursiveGrids = step(recursiveGrids, origin)
            printGrids(recursiveGrids, origin)
        }
        val c = countBugs(recursiveGrids)
        println("Bugs at this level = $c")

        Time.print()
    }

    private fun countBugs(recursiveGrids: MutableList<Array<Array<Boolean>>>): Long {
        var b = 0L
        recursiveGrids.map {
            it.map { it2 ->
                it2.map { it3 ->
                    if (it3) b++
                }
            }
        }
        return b
    }

    private fun printGrids(recursiveGrids: MutableList<Array<Array<Boolean>>>, origin: Int) {
        recursiveGrids.forEachIndexed { index, g ->
            println("Level ${index - origin}")
            printGrid(g)
        }
    }

    private fun printGrid(grid: Array<Array<Boolean>>) {
        for (i in 0..4) {
            for (j in 0..4) {
                print(if (i==2 && j==2) "?" else if (grid[i][j]) "#" else ".")
            }
            println()
        }

    }

    private fun step(
        recursiveGrid: MutableList<Array<Array<Boolean>>>,
        origin: Int
    ): MutableList<Array<Array<Boolean>>> {
        val newRecursiveGrid =
            recursiveGrid.map { it.map { it2 -> it2.clone() }.toTypedArray() }.toMutableList()

        for (level in recursiveGrid.indices) {
            for (i in 0..4) {
                for (j in 0..4) {
                    val around = mutableListOf(
                        Coor3(i - 1, j, level),
                        Coor3(i + 1, j, level),
                        Coor3(i, j - 1, level),
                        Coor3(i, j + 1, level)
                    )
                    around.remove(Coor3(2, 2, level))
                    around.remove(Coor3(-1, 0, level))
                    around.remove(Coor3(-1, 1, level))
                    around.remove(Coor3(-1, 2, level))
                    around.remove(Coor3(-1, 3, level))
                    around.remove(Coor3(-1, 4, level))
                    around.remove(Coor3(0, -1, level))
                    around.remove(Coor3(1, -1, level))
                    around.remove(Coor3(2, -1, level))
                    around.remove(Coor3(3, -1, level))
                    around.remove(Coor3(4, -1, level))
                    around.remove(Coor3(5, 0, level))
                    around.remove(Coor3(5, 1, level))
                    around.remove(Coor3(5, 2, level))
                    around.remove(Coor3(5, 3, level))
                    around.remove(Coor3(5, 4, level))
                    around.remove(Coor3(0, 5, level))
                    around.remove(Coor3(1, 5, level))
                    around.remove(Coor3(2, 5, level))
                    around.remove(Coor3(3, 5, level))
                    around.remove(Coor3(4, 5, level))
                    //outer
                    if (i == 0) around.add(Coor3(1, 2, level - 1))
                    if (i == 4) around.add(Coor3(3, 2, level - 1))
                    if (j == 0) around.add(Coor3(2, 1, level - 1))
                    if (j == 4) around.add(Coor3(2, 3, level - 1))
                    //inner
                    if (i == 1 && j == 2) {
                        around.add(Coor3(0, 0, level + 1))
                        around.add(Coor3(0, 1, level + 1))
                        around.add(Coor3(0, 2, level + 1))
                        around.add(Coor3(0, 3, level + 1))
                        around.add(Coor3(0, 4, level + 1))
                    }
                    if (i == 3 && j == 2) {
                        around.add(Coor3(4, 0, level + 1))
                        around.add(Coor3(4, 1, level + 1))
                        around.add(Coor3(4, 2, level + 1))
                        around.add(Coor3(4, 3, level + 1))
                        around.add(Coor3(4, 4, level + 1))
                    }
                    if (i == 2 && j == 1) {
                        around.add(Coor3(0, 0, level + 1))
                        around.add(Coor3(1, 0, level + 1))
                        around.add(Coor3(2, 0, level + 1))
                        around.add(Coor3(3, 0, level + 1))
                        around.add(Coor3(4, 0, level + 1))
                    }
                    if (i == 2 && j == 3) {
                        around.add(Coor3(0, 4, level + 1))
                        around.add(Coor3(1, 4, level + 1))
                        around.add(Coor3(2, 4, level + 1))
                        around.add(Coor3(3, 4, level + 1))
                        around.add(Coor3(4, 4, level + 1))
                    }
                    var b = 0
                    around.map {
                        if (it.z < 0 || it.z >= recursiveGrid.size)
                            Unit
                        else {
                            val grid = recursiveGrids[it.z]
                            if (grid[it.x][it.y]) b++
                        }
                    }
                    //bug dies
                    if (recursiveGrids[level][i][j] && b != 1)
                        newRecursiveGrid[level][i][j] = false
                    //bug appears
                    if (!recursiveGrids[level][i][j] && (b == 1 || b == 2))
                        newRecursiveGrid[level][i][j] = true
                }
            }
            newRecursiveGrid[level][2][2] = false
        }
        return newRecursiveGrid
    }
}

private fun getBio(grid: Array<Array<Boolean>>): Long {
    var exp = 1L
    var bio = 0L
    for (i in 0..4) {
        for (j in 0..4) {
            if (grid[i][j]) bio += exp
            exp *= 2
        }
    }
    return bio
}


fun main() {
    val input1 = Advent24b("input24.txt")
    input1.run()
}
