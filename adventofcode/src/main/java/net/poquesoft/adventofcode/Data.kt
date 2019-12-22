package net.poquesoft.adventofcode

import java.util.*
import kotlin.math.atan2
import kotlin.math.sqrt


object Time {

    var startTime = mutableMapOf<String, Long>()
    var ended = mutableMapOf<String, Long>()
    var lastPrint = Long.MIN_VALUE

    fun start(key: String = "") {
        startTime[key] = Date().time
    }

    fun end(key: String = "") {
        if (!startTime.containsKey(key)) return
        ended[key] = Date().time- startTime[key]!!
    }

    fun printLongestEndedKey() {
        if (ended.isEmpty()) return
        val k = ended.maxBy { it.value }!!
        println("Longest: ${k.key} -> ${k.value}")
    }

    fun print(key: String = "") {
        println("Time: ${elapsed(key)}")
    }

    fun printEach(seconds: Long, append:String = "", key: String = "") {
        if (!startTime.containsKey(key)) return
        var curTime = (Date().time - startTime[key]!!) / 1000
        if (curTime >= lastPrint + seconds) {
            println("Time: ${elapsed(key)} $append")
            lastPrint = curTime
        }
    }

    private fun elapsed(key: String = ""): String {
        if (!startTime.containsKey(key)) return "Key $key not found"
        var curTime = Date().time - startTime[key]!!
        var seconds = curTime / 1000
        val ms = curTime % 1000
        if (seconds < 60) return "0:${seconds.toString().padStart(2, '0')}.${ms.toString().padStart(
            3,
            '0'
        )}"
        var minutes = seconds / 60
        seconds %= 60
        if (minutes < 60) return "$minutes:${seconds.toString().padStart(2, '0')}"
        val hours = minutes / 60
        minutes %= 60
        return "$hours:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(
            2,
            '0'
        )}"
    }
}


private operator fun Coor.times(d: Coor): Int {
    return x * x + y * y
}

private operator fun Coor.times(i: Int): Coor {
    return Coor(x * i, y * i)
}


data class Coor(var x: Int, var y: Int) {
    operator fun plus(d: Coor): Coor {
        return Coor(x + d.x, y + d.y)
    }

    operator fun minus(d: Coor): Coor {
        return Coor(x - d.x, y - d.y)
    }

    fun mod(): Double {
        return sqrt(x*x+y*y.toDouble())
    }
}


fun hides(d: Coor, d2: Coor): Boolean {
    val angle1 = atan2(d.y.toDouble(), d.x.toDouble())
    val angle2 = atan2(d2.y.toDouble(), d2.x.toDouble())
    val mod1 = sqrt(d.x.toDouble() * d.x.toDouble() + d.y.toDouble() * d.y.toDouble())
    val mod2 = sqrt(d2.x.toDouble() * d2.x.toDouble() + d2.y.toDouble() * d2.y.toDouble())
    return (angle1 == angle2 && mod1 < mod2)
}


enum class Direction { UP, DOWN, LEFT, RIGHT }

private operator fun Coor3.plus(i: Int): Coor3 {
    return Coor3(x + i, y + i, z + i)
}


data class Coor3(val x: Int, val y: Int, val z: Int) {
    operator fun plus(d: Coor3): Coor3 {
        return Coor3(x + d.x, y + d.y, z + d.z)
    }

    operator fun minus(d: Coor3): Coor3 {
        return Coor3(x - d.x, y - d.y, z - d.z)
    }
}


data class MutablePair<T, Z>(var first: T, var second: Z)
