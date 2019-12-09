package net.poquesoft.adventofcode

import java.io.File

fun part1() {
    val data = File("input08.txt").readText()
    println(data.length)
    val layers = data.chunked(150)
    var k = 0
    var min = 100000
    layers.map {
        var c = mutableListOf(0, 0, 0)
        for (char in it) {
            when (char) {
                '0' -> c[0]++
                '1' -> c[1]++
                '2' -> c[2]++
            }
        }
        k++
        if (c[0] <= min) {
            min = c[0]
            println("Layer $k = $c ${c[1] * c[2]}")
        }
    }
}

fun main() {
    part2()
}

fun part2() {
    val data = File("input08.txt").readText()
    println(data.length)
    val layers = data.chunked(150)
    var k = 0
    var min = 100000
    var l = 0
    var lines = mutableListOf<MutableList<String>>()
    var result = "2".repeat(150)
    layers.map {
        for (i in 0 until 149) {
            if (result[i] == '2') {
                val pref = if (i>0) result.substring(0, i) else ""
                val suff = if (i<149) result.substring(i + 1) else ""
                result = pref + it[i].toString() + suff
            }
        }
    }
    println()

    result.chunked(25).map{
        for (char in it){
            when(char){
                '0' -> print (" ")
                '1' -> print ("█")
                '2' -> print (" ")
            }
        }
        println()
    }
}
