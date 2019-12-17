package net.poquesoft.adventofcode

import java.io.File
import kotlin.math.atan2
import kotlin.math.sqrt

val origin = Coor(25,31)

fun main() {
    val test1 = processMap2("input10.txt")
    println("")
}


fun processMap2(file: String) {
    val asteroids = mutableListOf<Coor>()
    var i = 0
    File(file).forEachLine {
        var j=0
        it.map {it2->
            if (it2 == '#') {
                asteroids.add(Coor(j, i))
            }
            j++
        }
        i++
    }

    val control = Coor(14,31)
    val remain = asteroids.toMutableList()
    remain.remove(origin)
    var previous = Coor(24,1)
    for (i in 1..200) {
        val next = nextToVaporize(remain, previous)
        remain.remove(next)
        println ("$i -> $next")
        previous = next
        if (previous == control)
            println("CONTROL")
    }
}

fun nextToVaporize(remain: MutableList<Coor>, p: Coor): Coor {
    var next = Coor(0,0)
    var previous = p
    if (previous == Coor(14,31))
        previous = Coor(0,30)
    val d = previous - origin
    val angle =atan2(d.y.toDouble(),d.x.toDouble())
    val mod1= sqrt(d.x.toDouble()*d.x.toDouble() + d.y.toDouble()*d.y.toDouble())
    var minAngle = 1000.0
    remain.map{
        val angle2 = atan2((it-origin).y.toDouble(),(it-origin).x.toDouble())
        val mod2= sqrt((it-origin).x.toDouble()*(it-origin).x.toDouble() + (it-origin).y.toDouble()*(it-origin).y.toDouble())
        if (angle2 > angle && (angle2-angle)<minAngle){
            minAngle = angle2-angle
            next = it
        } else if ((angle2-angle) == minAngle && mod2<mod1) {
            next = it
        }
    }
    if (next == Coor(0,0)) println("ERROR")
    return next
}

fun processMap1(file: String) {
    val asteroids = mutableListOf<Coor>()
    var i = 0
    File(file).forEachLine {
        var j=0
        it.map {it2->
            if (it2 == '#') {
                asteroids.add(Coor(j, i))
            }
            j++
        }
        i++
    }
    var best = Coor(0,0)
    var max = 0
    asteroids.map {
        var visible = asteroids.toMutableList()
        val origin = Coor(it.x, it.y)
        if (origin == Coor(3,4))
            print ("")
        visible.remove(origin)
        asteroids.map { it2 ->
            if (it2 != origin) {
                val d = Coor(it2.x - origin.x, it2.y - origin.y)
                asteroids.map { hidden ->
                    if (hidden != it2 && hidden != origin) {
                        val d2 = Coor(hidden.x - origin.x, hidden.y - origin.y)
                        if (hides(d, d2))
                            if (visible.contains(hidden)) visible.remove(hidden)
                    }
                }
            }
        }
        if (visible.size > max) {
            best = origin
            max = visible.size
        }
        println ("$origin ${visible.size}")
    }
    println("")
    println ("$best $max")
}



fun hides2(d: Coor, d2: Coor): Boolean {
    if (d.x == 0 && d2.x == 0) return (d2.y > d.y && d2.y*d.y>0)
    if (d.y == 0 && d2.y == 0) return (d2.x > d.x && d2.x*d.x>0)
    if (d.x == 0 && d2.x != 0) return false
    if (d.y == 0 && d2.y != 0) return false
    if (d.x != 0 && d2.x == 0) return false
    if (d.y != 0 && d2.y == 0) return false
    if (d.x.toDouble()/d.y.toDouble() == d2.x.toDouble()/d2.y.toDouble() &&
        (d2.x > d.x || d2.y > d.y)
        && (d2.y*d.y>0)
        && (d2.x*d.x>0)
        )
        return true
    return false
}
