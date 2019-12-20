package net.poquesoft.adventofcode

import java.io.File

class Advent20b (val file: String) {

    var start = Coor(0, 0)
    var end = Coor(0, 0)
    val items = mutableMapOf<String, Coor>()
    var distances = mutableMapOf<Pair<String,String>,Int>()
    lateinit var initM: MutableMap<Coor, Char>
    val cache = mutableMapOf<Pair<Coor,Int>,Int>()

    fun run() {
        Time.start()
        initM = readMap(file)
        setItems()
        items.map {
            distancesFrom(it.key)
        }
        Time.print()
        val t = findZ(initM.toMutableMap(), "A", 0, emptyList())
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
            if (c in ('B'..'Y') + ('a'..'z') - 'o') {
                var outer = false
                if (initM[Coor(x, y - 1)] == 'o') outer = true
                if (initM[Coor(x, y + 1)] == 'o') outer = true
                if (initM[Coor(x + 1, y)] == 'o') outer = true
                if (initM[Coor(x - 1, y)] == 'o') outer = true
                if (outer)
                    items["o$c"] = Coor(x, y)
                else
                    items["i$c"] = Coor(x, y)
            }
        }
        items["A"]=start
        items["Z"]=end
    }

    private fun distancesFrom(start: String) {
        val m = initM.toMutableMap()
        var positions =listOf(items[start]!!)
        var steps  = 0
        while (positions.isNotEmpty()) {
            val newPos = mutableListOf<Coor>()
            steps++
            positions.map { pos ->
                m[pos] = '$'
                val around = listOf(
                    Coor(pos.x - 1, pos.y),
                    Coor(pos.x + 1, pos.y),
                    Coor(pos.x, pos.y - 1),
                    Coor(pos.x, pos.y + 1)
                )
                around.map { a ->
                    when (m[a]) {
                        '.' -> {
                            m[a] = '@'
                            newPos.add(a)
                        }
                        '#' -> Unit == Unit
                        '@' -> Unit == Unit
                        '$' -> Unit == Unit
                        null -> Unit == Unit
                        in ('A'..'Z') + ('a'..'z') - 'o' -> {
                            val s = items.filter { it.value == a }.keys.first()
                            if (!distances.containsKey(Pair(start,s)))
                                distances[Pair(start,s)]=steps
                            else true
                        }
                        else -> Unit == Unit
                    }
                }
            }
            positions = newPos.toList()
        }
    }

    private fun findZ(m: MutableMap<Coor, Char>, start: String, level: Int, alreadyPassed: List<String>): Int {
        val paths = mutableListOf<Int>()
        if (distances[Pair(start,"Z")] != null && level == 0) {
            return distances[Pair(start, "Z")]!!
        }
        if (level > 40) return 100000
        if (alreadyPassed.size > 200) return 100000
        distances.filter { it.key.first == start }.map { it.key.second }.map{
            if (distances[Pair(start,it)] != null) {
                if (it != start && it.length == 2) {
                    if (it.startsWith("o")){
                        if (level >0 ){
                            val ap = "$it${level-1}"
                            if (alreadyPassed.contains(ap))
                                println("Avoid loop $ap")
                            else {
                                println(ap)
                                paths.add(1 + distances[Pair(start, it)]!! + findZ(m, "i${it[1]}", level - 1,alreadyPassed+ap))
                            }
                        }
                    } else {
                        val ap = "$it${level+1}"
                        if (alreadyPassed.contains(ap))
                            println("Avoid loop $ap")
                        else {
                            println(ap)
                            paths.add(1 + distances[Pair(start, it)]!! + findZ(m, "o${it[1]}", level + 1,alreadyPassed + ap))
                        }
                    }
                }
            }
        }
        if (paths.isEmpty()) return 100000
        return paths.min()!!
    }

/*
    private fun findZOneLevel(m: MutableMap<Coor, Char>, it: Coor, level: Int): Int {
        if (cache.containsKey(Pair(it,level)))
            return cache[Pair(it,level)]!!
        m[it] = '$'
        if (level > 20) return 100000
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
                        true
                }
                '.' -> {
                    m[a] = '$'
                    paths.add(1 + findZOneLevel(m.toMutableMap(), a, level))
                }
                '#' -> Unit == Unit
                '$' -> Unit == Unit
                null -> Unit == Unit
                in 'B'..'Y' -> {
                    if (m[a] != 'o'){
                        val l = items[m[a]]!!
                        if (a == l.first) {
                            if (level > 0) {
                                println("Level: ${level - 1}")
                                val r = findZOneLevel(initM.toMutableMap(), l.second, level - 1)
                                paths.add(2 + r)
                                cache[Pair(l.second,level-1)]= r
                            }else
                                true
                        } else if (a == l.second){
                            println("Level: ${level + 1}")
                            val r = findZOneLevel(initM.toMutableMap(), l.first, level + 1)
                            paths.add(2 + r)
                            cache[Pair(l.first,level+1)]= r
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
                            if (level > 0) {
                                println("Level: ${level - 1}")
                                val r = findZOneLevel(initM.toMutableMap(), l.second, level - 1)
                                paths.add(2 + r)
                                cache[Pair(l.second,level-1)]= r
                            }else
                                true
                        } else if (a == l.second){
                            println("Level: ${level + 1}")
                            val r = findZOneLevel(initM.toMutableMap(), l.first, level + 1)
                            paths.add(2 + r)
                            cache[Pair(l.first,level+1)]= r
                        } else {
                            println("ERROR item not found")
                        }
                    } else
                        Unit == Unit
                }
                else -> Unit == Unit
            }
        }
        var s = 10000
        if (paths.isNotEmpty()) s = paths.min()!!
        return s
    }
*/
}

fun main() {
    val input1 = Advent20b("input20c.txt")
//    input1.run()
    val input3 = Advent20b("input20.txt")
    input3.run()
}
