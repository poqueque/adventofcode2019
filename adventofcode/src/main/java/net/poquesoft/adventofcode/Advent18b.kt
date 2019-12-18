package net.poquesoft.adventofcode

import java.io.File

class Advent18b {
    val random = true

    var levelCompleted = 26

    var minTotal = Int.MAX_VALUE
    val cache = mutableMapOf<Pair<Char, String>, Pair<String, Int>>()

    var pos = Coor(0, 0)
    var nextItems: MutableList<Item> = mutableListOf(Item('@', 0, Coor(0, 0)))

    val items = mutableMapOf<Char, Coor>()
    var minDis = mutableMapOf<Pair<Item, String>, Int>()
    var validateMinDis = mutableMapOf<Pair<Item, String>, Int>()

    var m = readMap()

    var i=0

    init {
        Time.start()
        var minSteps = Int.MAX_VALUE
        for (i in 1..100000) {
            m = readMap()
            nextItems = mutableListOf(Item('@', 0, Coor(0, 0)))
            val p = process()
            val steps = p.second
            Time.printEach(60, "Tries: $i")
            if (steps < minSteps) {
                minSteps = steps
                println("$i - Steps: $steps MinSteps: $minSteps ${p.first}")
            } else if (i % 10000 == 0) {
                println("$i - Steps: <$steps> MinSteps: $minSteps ${p.first}")
            }
        }
    }

    private fun process(): Pair<String, Int> {
        var steps = 0
        var onlyOne = '-'
        var onlyOneStart = 0
        var onlyOneEnd = 0
        var onlyOneString = ""

        var order = ""

        val paso = mutableMapOf<Pair<Item, String>, Int>()

        val remaining = ('a'..'z').toMutableList()
        while (nextItems.size > 0) {
            findItems()
            if (nextItems.size > 0) {
                if (nextItems.size == 1) {
                    if (onlyOne == '-') {
                        onlyOne = nextItems[0].name
/*
                        if (onlyOne == 'i') return Pair(order+"ipyndxe",steps + nextItems[0].distance+96)
                        if (onlyOne == 'p') return Pair(order+"pyndxe",steps + nextItems[0].distance+82)
                        if (onlyOne == 'y') return Pair(order+"yndxe",steps + nextItems[0].distance+66)
                        if (onlyOne == 'n') return Pair(order+"ndxe",steps + nextItems[0].distance+48)
                        if (onlyOne == 'd') return Pair(order+"dxe",steps + nextItems[0].distance+32)
                        if (onlyOne == 'x') return Pair(order+"xe",steps + nextItems[0].distance+20)
*/
                        onlyOneStart = steps + nextItems[0].distance
                    }
                }
                if (nextItems.isEmpty())
                    return Pair(order,Int.MAX_VALUE)
                var n = nextItems.take(4).random()
//                if (order.isEmpty()) n = nextItems[1]
//                if (order.length == 1)
//                    n = nextItems[1]
                if (onlyOne != '-') onlyOneString += n.name
                remaining.remove(n.name)
                order += n.name
                /*
                val p = Pair(n,remaining.joinToString())
                paso[p] = steps
                if (validateMinDis.contains(p) && validateMinDis[p]!! > 10){
                    return steps - minDis[p]!!
                }*/

                steps += n.distance
                //println("Steps: $steps Picked: ${n.name}")
                //pickKey
                m[items[n.name]!!] = '.'
                //unlock Door
                if (items[n.name.toUpperCase()] != null) m[items[n.name.toUpperCase()]!!] = '.'
                //position @
                m[items['@']!!] = '.'
                m[n.c] = '@'
                items['@'] = n.c
            }
        }
        onlyOneEnd = steps
/*
        if (onlyOne != '-')
            println("LAST: $onlyOne - STEPS: ${onlyOneEnd-onlyOneStart} - $onlyOneString")

        paso.map{
            if(!minDis.containsKey(it.key)){
                minDis[it.key] = steps-paso[it.key]!!
                validateMinDis[it.key] = 0
            } else {
                if (steps-it.value < minDis[it.key]!!) {
                    minDis[it.key] = steps-it.value
                    validateMinDis[it.key] = 0
                } else
                    validateMinDis[it.key] = validateMinDis[it.key]!! + 1
            }
        }

 */
        return Pair(order, steps)
    }

    private fun findItems(): MutableList<Item> {
        nextItems.clear()
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

fun main() {
    val input1 = Advent18b()
    input1.run()
}
