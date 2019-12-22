package net.poquesoft.adventofcode

import java.io.File

class Advent22(val file: String) {

    val size = 119315717514047

    fun run() {
        Time.start()
        println("Creating deck...")
        var deck = (0 until size).toMutableList()
        println("Shuffling...")
        File(file).forEachLine {
            if (it.startsWith("deal with increment ")){
                println(it)
                val step = it.substring(20).toInt()
                deck = dealWithIncrement(deck,step)
            }
            if (it.startsWith("cut ")){
                println(it)
                var step = it.substring(4).toInt()
                if (step < 0) step += deck.size
                deck = (deck.takeLast(deck.size - step) + deck.take(step)).toMutableList()
            }
            if (it.startsWith("deal into new stack")){
                println(it)
                deck.reverse()
            }
        }
        val result = deck.joinToString(" ")
        //println("Result: $result")
        println("Result 2019: ${deck.indexOf(2019)}")
        Time.print()
    }
}

private fun dealWithIncrement(list: MutableList<Long>, step: Int): MutableList<Long> {
    val retList = MutableList(list.size) { 0L }
    var p =0
    for (i in list.indices) {
        retList[p] = list[i]
        p+=step
        p%=list.size
    }
    return retList
}

fun main() {
    val input1 = Advent22("input22.txt")
    input1.run()
}
