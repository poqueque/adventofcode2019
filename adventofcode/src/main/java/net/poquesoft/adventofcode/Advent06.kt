package net.poquesoft.adventofcode

import java.io.File

var map = mutableMapOf<String, String>()
var orbits = mutableMapOf<String, Int>()
var queue = mutableListOf<String>()

fun main() {
    File("input06.txt").forEachLine {
        val parts = it.split(")")
        map[parts[1]] = parts[0]
    }
    orbits["COM"] = 0
    queue.add("COM")
    while (queue.size > 0){
        val next = queue[0]
        addOrbits(next)
        queue.removeAt(0)
        println("Queue Size: ${queue.size}")
    }

    val total = orbits.map { it.value }.sum()
    println(total)

    val l1 = pathToCom("YOU")
    val l2 = pathToCom("SAN")
    val int = l1.intersect(l2)
    println ("L1 : ${l1.size}")
    println ("L2 : ${l2.size}")
    println ("L1 int L2 : ${int.size}")
    println ("L1 + L2 - int*2 : ${l1.size + l2.size - 2*int.size}")

}

fun addOrbits(astro: String){
    map.map {
        if (it.value == astro) {
            val prevOrbits = orbits[it.value] ?: 0
            orbits[it.key] = prevOrbits + 1
            queue.add(it.key)
        }
    }
}

fun pathToCom(astro: String): MutableList<String> {
    val l = mutableListOf<String>()
    var a = astro
    while (a != "COM" && a != "---"){
        a = map[a] ?: "---"
        print (" ${a}")
        l.add(a)
    }
    println ("")
    println ("")
    return l
}