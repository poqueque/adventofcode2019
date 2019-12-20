package net.poquesoft.adventofcode

import java.io.File

class Advent20b (val file: String) {

    var start = Coor(0, 0)
    var end = Coor(0, 0)
    val items = mutableMapOf<Char, MutablePair<Coor, Coor>>()
    lateinit var initM: MutableMap<Coor, Char>

    fun run() {
        Time.start()
        initM = readMap(file)
        setItems()
        Time.print()
        val t = findZOneLevel(initM.toMutableMap(), start)
        println("Total: $t")
    }

    private fun readMap(file: String): MutableMap<Coor, Char> {
        var y = 0
        val m = mutableMapOf<Coor, Char>()
        File(file).forEachLine {
            it.forEachIndexed { x, c ->
                m[Coor(x, y)] = c
            }
            y++
        }
        return m
    }

    private fun setItems(){
        initM.map{
            val p = it.key
            val x = p.x
            val y = p.y
            val c= it.value
            if (c == 'A') start = Coor(x, y)
            if (c == 'Z') end = Coor(x, y)
            if (c in 'B'..'Y' || c in 'a'..'z') {
                if (items[c] == null) items[c] = MutablePair(Coor(0, 0), Coor(0, 0))
                var outer = false
                if (initM[Coor(x, y - 1)] == 'o') outer = true
                if (initM[Coor(x, y + 1)] == 'o') outer = true
                if (initM[Coor(x + 1, y)] == 'o') outer = true
                if (initM[Coor(x - 1, y)] == 'o') outer = true
                if (outer)
                    items[c]!!.first = Coor(x, y)
                else
                    items[c]!!.second = Coor(x, y)
            }
        }
    }

    private fun findZ(m: MutableMap<Coor, Char>, it: Coor, level: Int): Int {
        val around = listOf(
            Coor(it.x - 1, it.y),
            Coor(it.x + 1, it.y),
            Coor(it.x, it.y - 1),
            Coor(it.x, it.y + 1)
        )
        val paths = mutableListOf<Int>()
        around.map { a ->
            when (m[a]) {
                'Z' -> {
                    if (level == 0)
                        return 1
                    else
                        Unit == Unit
                }
                '.' -> {
                    m[a] = '$'
                    paths.add(1 + findZ(m.toMutableMap(), a, level))
                }
                '#' -> Unit == Unit
                '$' -> Unit == Unit
                null -> Unit == Unit
                in 'B'..'Y' -> {
                    val l = items[m[a]]!!
                    if (a == l.first) {
                        if (level == 0) Unit == Unit
                        else {
                            paths.add(1 + findZ(initM.toMutableMap(), l.second, level - 1))
                            println("Level: ${level - 1}")
                        }
                    } else {
                        paths.add(1 + findZ(initM.toMutableMap(), l.first, level + 1))
                        println("Level: ${level + 1}")
                    }
                }
                in ('a'..'z') -> {
                    val l = items[m[a]]!!
                    if (a == l.first) {
                        if (level == 0) Unit == Unit
                        else {
                            paths.add(1 + findZ(initM.toMutableMap(), l.second, level - 1))
                            println("Level: ${level - 1}")
                        }
                    } else {
                        paths.add(1 + findZ(initM.toMutableMap(), l.first, level + 1))
                        println("Level: ${level + 1}")
                    }
                }
                else -> Unit == Unit
            }
        }
        if (paths.isEmpty()) return 100000
        return paths.min()!!
    }

    private fun findZOneLevel(m: MutableMap<Coor, Char>, it: Coor): Int {
        val around = listOf(
            Coor(it.x - 1, it.y),
            Coor(it.x + 1, it.y),
            Coor(it.x, it.y - 1),
            Coor(it.x, it.y + 1)
        )
        val paths = mutableListOf<Int>()
        around.map { a ->
            when (m[a]) {
                'Z' -> {
                    return 1
                }
                '.' -> {
                    m[a] = '$'
                    paths.add(1 + findZOneLevel(m.toMutableMap(), a))
                }
                '#' -> Unit == Unit
                '$' -> Unit == Unit
                null -> Unit == Unit
                in 'B'..'Y' -> {
                    if (m[a] != 'o'){
                        val l = items[m[a]]!!
                        if (a == l.first) {
                            paths.add(2 + findZOneLevel(m.toMutableMap(), l.second))
                        } else if (a == l.second){
                            paths.add(2 + findZOneLevel(m.toMutableMap(), l.first))
                        } else {
                            println("ERROR item not found")
                        }
                    } else
                        Unit == Unit
                }
                in ('a'..'z') -> {
                    if (m[a] != 'o'){
                        val l = items[m[a]]!!
                        if (a == l.first) {
                            paths.add(2 + findZOneLevel(m.toMutableMap(), l.second))
                        } else if (a == l.second){
                            paths.add(2 + findZOneLevel(m.toMutableMap(), l.first))
                        } else {
                            println("ERROR item not found")
                        }
                    } else
                        Unit == Unit
                }
                else -> Unit == Unit
            }
        }
        if (paths.isEmpty()) return 100000
        return paths.min()!!
    }
}

fun main() {
    val input1 = Advent20b("input20a.txt")
    input1.run()
    val input2 = Advent20b("input20b.txt")
    input2.run()
    val input3 = Advent20b("input20.txt")
    input3.run()
}
