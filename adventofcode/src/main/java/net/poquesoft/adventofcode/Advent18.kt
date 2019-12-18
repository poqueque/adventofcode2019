package net.poquesoft.adventofcode

import java.io.File
import java.lang.Thread.sleep

class Advent18 {
    val random = false

    var levelCompleted = 1

    var minTotal = Int.MAX_VALUE
    val cache = mutableMapOf<Pair<Char, String>, Pair<String, Int>>()
    val seen = mutableMapOf<Pair<List<Coor>, List<Char>>, Int>()

    val items = mutableMapOf<Char, Coor>()
    val starts = mutableListOf<Coor>()
    var minDis = mutableMapOf<Pair<Item, String>, Int>()
    var validateMinDis = mutableMapOf<Pair<Item, String>, Int>()

    var i = 0

    init {
        Time.start()
        var minSteps = Int.MAX_VALUE
        val m = readMap()
//            val p = processRecursive(m, Pair("", 0), items.toMutableMap(), mutableListOf<Char>())
//            val steps = p.second
        val steps = processRecursive2(m,starts.toMutableList(),items.toMutableMap(),mutableListOf())
        Time.printEach(60)
        if (steps < minSteps) {
            minSteps = steps
            println(
                "${i.toString().padStart(4, '0')} Steps: $steps MinSteps: $minSteps"
            )
        }
    }

    var processes = 0

    private fun processRecursive2(
        m: MutableMap<Coor, Char>,
        starts: MutableList<Coor>,
        items: MutableMap<Char,Coor>,
        haveKeys: List<Char>
    ): Int {
        val arroba = starts
        val str = haveKeys.sorted().toList()

        if (seen[Pair(arroba, str)]!= null)
            return seen[Pair(arroba, str)]!!

        Time.printEach(60, "Processes: $processes")
        processes++
        if (haveKeys.size < 6 ) {
            println("LEVEL: ${haveKeys.sorted().joinToString("")}")
        }

        val next1 = findItems(m,starts[0])
        val next2 = findItems(m,starts[1])
        val next3 = findItems(m,starts[2])
        val next4 = findItems(m,starts[3])
        val nexts = next1 + next2 + next3 + next4
        if (nexts.isEmpty()) {
            i++
            if (i % 2000 == 0) println("$i")
            return if (haveKeys.size == 26)
                0
            else
                1000
        }

        var min = Int.MAX_VALUE
        nexts.map {
            val m2 = m.toMutableMap()
            val items2 = items.toMutableMap()
            val starts2 = starts.toMutableList()
            //pickKey
            m2[items2[it.name]!!] = '.'
            //unlock Door
            if (items2[it.name.toUpperCase()] != null) m2[items2[it.name.toUpperCase()]!!] = '.'
            //position @
            if (next1.contains(it)) {
                m2[starts2[0]] = '.'
                m2[it.c] = '@'
                starts2[0] = it.c
            }
            if (next2.contains(it)) {
                m2[starts2[1]] = '.'
                m2[it.c] = '@'
                starts2[1] = it.c
            }
            if (next3.contains(it)) {
                m2[starts2[2]] = '.'
                m2[it.c] = '@'
                starts2[2] = it.c
            }
            if (next4.contains(it)) {
                m2[starts2[3]] = '.'
                m2[it.c] = '@'
                starts2[3] = it.c
            }
            val hk2 = haveKeys.toMutableList()
            hk2.add(it.name)
            val p2 = it.distance + processRecursive2(
                m2,
                starts2,
                items2,
                hk2
            )
            if (p2 < min) {
                min = p2
            }
        }
        if (haveKeys.size < 6 ) {
            println("LEVEL: ${haveKeys.sorted().joinToString("")} $min")
        }
        seen[Pair(arroba, haveKeys.sorted())] = min
        processes--
        return min
    }


    private fun findItems(m: MutableMap<Coor, Char>, start: Coor): MutableList<Item> {
        val nextItems = mutableListOf<Item>()
        val m2 = m.toMutableMap()
        var positions = listOf(start)
        var steps = 0
        while (positions.isNotEmpty()) {
            val newPos = mutableListOf<Coor>()
            steps++
            positions.map {
                m2[it] = '$'
                val around = listOf(
                    Coor(it.x - 1, it.y),
                    Coor(it.x + 1, it.y),
                    Coor(it.x, it.y - 1),
                    Coor(it.x, it.y + 1)
                )
                around.map { a ->
                    when (m2[a]) {
                        '.' -> {
                            m2[a] = '@'
                            newPos.add(a)
                        }
                        '#' -> Unit == Unit
                        '@' -> Unit == Unit
                        '$' -> Unit == Unit
                        null -> Unit == Unit
                        in ('a'..'z') -> {
                            nextItems.add(Item(m2[a]!!, steps, a)); m2[a] = '#'
                        }
                        else -> Unit == Unit
                    }
                }
            }
//            positions = m2.filter { it.value == '@' }.keys.toList()
            positions = newPos.toList()
        }
        return nextItems
    }

    private fun readMap(): MutableMap<Coor, Char> {
        var y = 0
        val m = mutableMapOf<Coor, Char>()
        File("input18.txt").forEachLine {
            it.forEachIndexed { x, c ->
                m[Coor(x, y)] = c
                if (c == '@') starts.add(Coor(x, y))
                if (c != '.' && c != '#' && c != '@') items[c] = Coor(x, y)
            }
            y++
        }
        return m
    }

    fun run() {
        Time.print()
    }

}

data class Item(val name: Char, val distance: Int, val c: Coor)

fun main() {
    val input1 = Advent18()
    sleep(2)
    input1.run()
}
