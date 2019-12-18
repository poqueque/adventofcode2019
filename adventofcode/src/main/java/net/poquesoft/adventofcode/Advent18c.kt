package net.poquesoft.adventofcode

import java.io.File
import java.lang.Thread.sleep

class Advent18 {
    val random = false

    var levelCompleted = 1

    var minTotal = Int.MAX_VALUE
    val cache = mutableMapOf<Pair<Char, String>, Pair<String, Int>>()
    val seen = mutableMapOf<Pair<Coor, List<Char>>, Int>()

    var pos = Coor(0, 0)
    var nextItems: MutableList<Item> = mutableListOf(Item('@', 0, Coor(0, 0)))

    val items = mutableMapOf<Char, Coor>()
    var minDis = mutableMapOf<Pair<Item, String>, Int>()
    var validateMinDis = mutableMapOf<Pair<Item, String>, Int>()

    var i = 0

    init {
        Time.start()
        var minSteps = Int.MAX_VALUE
        nextItems = mutableListOf(Item('@', 0, Coor(0, 0)))
        val m = readMap()
//            val p = processRecursive(m, Pair("", 0), items.toMutableMap(), mutableListOf<Char>())
//            val steps = p.second
        val steps = processRecursive2(m,items.toMutableMap(),mutableListOf())
        Time.printEach(60)
        if (steps < minSteps) {
            minSteps = steps
            println(
                "${i.toString().padStart(4, '0')} Steps: $steps MinSteps: $minSteps"
            )
        }
    }

    private fun processRecursive(
        m: MutableMap<Coor, Char>,
        p: Pair<String, Int>,
        items: MutableMap<Char, Coor>,
        haveKeys: List<Char>
    ): Pair<String, Int> {
        val arroba = items['@']!!
        val str = haveKeys.sorted().toList()

/*        if (seen[Pair(arroba, str)]!= null)
            return seen[Pair(arroba, str)]!!*/
        Time.printEach(60)
        var next = findItems(m)
        //if (remaining.size > 25) next = mutableListOf(next[1])
        if (next.isEmpty()) {
            i++
            if (p.second < minTotal) {
                minTotal = p.second
                println(p)
            }
            if (i % 10000 == 0) println("$i")
            return p
        } else if (random) {
            next = mutableListOf(next.random())
        }

        var min = Int.MAX_VALUE
        var res = p
        var dif = 0
        var char = '-'
        next.map {
            val m2 = m.toMutableMap()
            val items2 = items.toMutableMap()
            //pickKey
            m2[items2[it.name]!!] = '.'
            //unlock Door
            if (items2[it.name.toUpperCase()] != null) m2[items2[it.name.toUpperCase()]!!] = '.'
            //position @
            m2[items2['@']!!] = '.'
            m2[it.c] = '@'
            items2['@'] = it.c
            val hk2 = haveKeys.toMutableList()
            hk2.add(it.name)
            val p2 = processRecursive(
                m2,
                Pair(p.first + it.name, p.second + it.distance),
                items2,
                hk2
            )
            if (p2.second < min) {
                min = p2.second
                res = p2
                dif = p.second + it.distance
                char = it.name
            }
        }
        val steps = res.second - dif
        if (steps > 0)
            if (haveKeys.size > levelCompleted) {
                levelCompleted = haveKeys.size
                println("LEVEL COMPLETED: $levelCompleted")
            }
//       seen[Pair(arroba, (haveKeys+char).sorted())] = res
        return res
    }

    var processes = 0

    private fun processRecursive2(
        m: MutableMap<Coor, Char>,
        items: MutableMap<Char, Coor>,
        haveKeys: List<Char>
    ): Int {
        val arroba = items['@']!!
        val str = haveKeys.sorted().toList()


        if (seen[Pair(arroba, str)]!= null)
            return seen[Pair(arroba, str)]!!

        Time.printEach(60, "Processes: $processes")
        processes++
        if (haveKeys.size < 6 ) {
            println("LEVEL: ${haveKeys.sorted().joinToString("")}")
        }

        var next = findItems(m)
        if (next.isEmpty()) {
            i++
            if (i % 10000 == 0) println("$i")
            return 0
        }

        var min = Int.MAX_VALUE
        var char = ' '
        next.map {
            val m2 = m.toMutableMap()
            val items2 = items.toMutableMap()
            //pickKey
            m2[items2[it.name]!!] = '.'
            //unlock Door
            if (items2[it.name.toUpperCase()] != null) m2[items2[it.name.toUpperCase()]!!] = '.'
            //position @
            m2[items2['@']!!] = '.'
            m2[it.c] = '@'
            items2['@'] = it.c
            val hk2 = haveKeys.toMutableList()
            hk2.add(it.name)
            val p2 = it.distance + processRecursive2(
                m2,
                items2,
                hk2
            )
            if (p2 < min) {
                min = p2
                char = it.name
            }
        }
        if (haveKeys.size < 6 ) {
            println("LEVEL: ${haveKeys.sorted().joinToString("")} $min")
        }
        seen[Pair(arroba, haveKeys.sorted())] = min
        processes--
        return min
    }


    private fun findItems(m: MutableMap<Coor, Char>): MutableList<Item> {
        val nextItems = mutableListOf<Item>()
        val m2 = m.toMutableMap()
        var positions = m2.filter { it.value == '@' }.keys.toList()
//        var positions = listOf(items['@'])
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
                if (c == '@') pos = Coor(x, y)
                if (c != '.' && c != '#') items[c] = Coor(x, y)
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
