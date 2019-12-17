package net.poquesoft.adventofcode

import kotlin.math.abs

/*
var io = Coor3(-1,0,2)
var eu = Coor3(2,-10,-7)
var ga = Coor3(4,-8,8)
var ca = Coor3(3,5,-1)
*/
/*
var io = Coor3(-8,-10,0)
var eu = Coor3(5,5,10)
var ga = Coor3(2,-7,3)
var ca = Coor3(9,-8,-3)
*/

var io = Coor3(13,-13,-2)
var eu = Coor3(16,2,-15)
var ga = Coor3(7,-18,-12)
var ca = Coor3(-3,-8,-8)


val initialMoons = listOf(io,eu,ga,ca)
var moons = mutableListOf(io,eu,ga,ca)
var velocity = mutableListOf(Coor3(0,0,0),Coor3(0,0,0),Coor3(0,0,0),Coor3(0,0,0))

var repeat = Coor3(0,0,0)

fun main() {

    loop@ for (i in 1..Int.MAX_VALUE){
        //if (i%1000 == 0) println("Step $i")
        moveMoons()
        //velocity
        if (i%1000 == 0) for (i in moons.indices){
            //println("pos=${moons[i]}  vel=${velocity[i]}")
        }

        if (moons[0].x == initialMoons[0].x
            && moons[1].x == initialMoons[1].x
            && moons[2].x == initialMoons[2].x
            && moons[3].x == initialMoons[3].x
            && velocity[0].x == 0
            && velocity[1].x == 0
            && velocity[2].x == 0
            && velocity[3].x == 0
            && repeat.x == 0) {
            repeat = Coor3(i,repeat.y,repeat.z)
            print("$i,")
            println("INITIAL STATE X: $i")
            if (repeat.x > 0 && repeat.y >0 && repeat.z > 0)
                break@loop
        }
        if (moons[0].y == initialMoons[0].y
            && moons[1].y == initialMoons[1].y
            && moons[2].y == initialMoons[2].y
            && moons[3].y == initialMoons[3].y
            && velocity[0].y == 0
            && velocity[1].y == 0
            && velocity[2].y == 0
            && velocity[3].y == 0
            && repeat.y == 0) {
            repeat = Coor3(repeat.x,i,repeat.z)
            print("$i,")
            println("INITIAL STATE Y: $i")
            if (repeat.x > 0 && repeat.y >0 && repeat.z > 0)
                break@loop
        }
        if (moons[0].z == initialMoons[0].z
            && moons[1].z == initialMoons[1].z
            && moons[2].z == initialMoons[2].z
            && moons[3].z == initialMoons[3].z
            && velocity[0].z == 0
            && velocity[1].z == 0
            && velocity[2].z == 0
            && velocity[3].z == 0
            && repeat.z == 0) {
            repeat = Coor3(repeat.x,repeat.y,i)
            print("$i,")
            println("INITIAL STATE X: $i")
            if (repeat.x > 0 && repeat.y >0 && repeat.z > 0)
                break@loop
        }
    }

    val l = mutableListOf(repeat.x.toLong(),repeat.y.toLong(),repeat.z.toLong())

    println("Calculating GCD...")
    val gcd = gcdList(l)
    println("GCD: $gcd")

    println("Calculating LCM...")
    val lcm = lcmList(l)
    println("lcm = $lcm")
    println("END")
}


fun lcm(a: Long, b: Long): Long {
    println("Calculating LCM($a,$b)")
    return a*b/gcd(a,b)
}

fun gcd(a: Long, b: Long): Long {
    var a = a
    var b = b
    while (b > 0) {
        val temp = b
        b = a % b // % is remainder
        a = temp
    }
    return a
}


fun gcdList(input: List<Long>): Long {
    var result = input[0]
    for (i in 1 until input.size) result = gcd(result, input[i])
    return result
}

fun lcmList(input: List<Long>): Long {
    var result = input[0]
    for (i in 1 until input.size) result = lcm(result, input[i])
    return result
}

fun moveMoons() {

    //gravity
    for (i in moons.indices){
        for (j in moons.indices){
            if (moons[i].x < moons[j].x) velocity[i]+=Coor3(1,0,0)
            if (moons[i].y < moons[j].y) velocity[i]+=Coor3(0,1,0)
            if (moons[i].z < moons[j].z) velocity[i]+=Coor3(0,0,1)
            if (moons[i].x > moons[j].x) velocity[i]+=Coor3(-1,0,0)
            if (moons[i].y > moons[j].y) velocity[i]+=Coor3(0,-1,0)
            if (moons[i].z > moons[j].z) velocity[i]+=Coor3(0,0,-1)
        }
    }
    //velocity
    for (i in moons.indices){
        moons[i] = moons[i]+ velocity[i]
        //println("pos=${moons[i]}  vel=${velocity[i]}")
    }
/*
    println("Energy:")
    var t = 0
    for (i in moons.indices){
        val pot = energy(moons[i])
        val kin = energy(velocity[i])
        val total = pot*kin
        t+=total
        println("pot: $pot  kin: $kin  total: $total")
    }
    println("Total Energy: $t")
*/

}

fun energy(c: Coor3): Int{
    return (abs(c.x)+abs(c.y)+abs(c.z))
}
