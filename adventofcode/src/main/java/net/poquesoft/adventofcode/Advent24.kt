package net.poquesoft.adventofcode

import java.io.File

class Advent24(val file: String) {

    var grid = arrayOf<Array<Boolean>>()
    var biodiversities = mutableListOf<Long>()

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

        printGrid(grid)
        println("Starting")

        var minutes = 0
        var bio = getBio(grid)
        while (!biodiversities.contains(bio)) {
            println("Minutes: $minutes")
            biodiversities.add(bio)
            grid = step(grid)
            printGrid(grid)
            bio = getBio(grid)
            minutes++
        }
        println("BiodiversityIndex = $bio")

        Time.print()
    }

    private fun printGrid(grid: Array<Array<Boolean>>) {
        for (i in 0..4) {
            for (j in 0..4) {
                print(if (grid[i][j]) "#" else ".")
            }
            println()
        }

    }

    private fun step(grid: Array<Array<Boolean>>): Array<Array<Boolean>> {
        var newGrid = grid.map { it.clone() }.toTypedArray()

        for (i in 0..4) {
            for (j in 0..4) {
                val around = listOf(
                    Coor(i - 1, j),
                    Coor(i + 1, j),
                    Coor(i, j - 1),
                    Coor(i, j + 1)
                )
                var b = 0
                around.map {
                    if (it.x >= 0 && it.y >= 0 && it.x < 5 && it.y < 5) {
                        if (grid[it.x][it.y])
                            b++
                    }
                }
                //bug dies
                if (grid[i][j] && b != 1)
                    newGrid[i][j] = false
                //bug appears
                if (!grid[i][j] && (b == 1 || b == 2))
                    newGrid[i][j] = true
            }
        }
        return newGrid
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
    val input1 = Advent24("input24a.txt")
    input1.run()
}
