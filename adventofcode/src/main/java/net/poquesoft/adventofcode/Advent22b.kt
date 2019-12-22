package net.poquesoft.adventofcode

import java.io.File
import java.math.BigInteger
import kotlin.math.abs

class Advent22b(val file: String) {

    val size = 119315717514047
    var position = 2020L
    val X = 2020L
    var Y = 2020L
    var Z = 2020L

    fun run() {
        Time.start()
        println("Creating deck...")
        println("Shuffling...")
        var orders = mutableListOf<String>()
        File(file).forEachLine {
            orders.add(it)
        }
        orders.reverse()
        println("           [$position]")
        for (k in 0 until 2){
            orders.map{
                if (it.startsWith("deal with increment ")){
//                    println(it)
                    val step = it.substring(20).toInt()
                    position = dealWithIncrementPosK(size,step,position)
//                    println("           [$position]")
                    if (position > size) println("WARNING: SIZE MISMATCH")
                }
                if (it.startsWith("cut ")){
//                    println(it)
                    val step = it.substring(4).toLong()
                    if (step > 0)
                        position += step
                    else
                        position += (size + step)
                    if (position > size) position -= size
//                    println("           [$position]")
                    if (position > size) println("WARNING: SIZE MISMATCH")
                }
                if (it.startsWith("deal into new stack")){
//                    println(it)
                    position = (size - 1) - position
//                    println("           [$position]")
                    if (position > size) println("WARNING: SIZE MISMATCH")
                }
            }
            print("step $k: ")
            println(position)
            if (k == 0) Y = position
            if (k == 1) Z = position
        }
        val A = ((Y-Z) * modinv(X-Y,size)) % size
        val B = (Y - A*X) % size
        println("A: $A")
        println("B: $B")

        val s0 = (A*2020+B) % size
        val s1 = (A*s0 + B) % size
        println("Comprobacion: Step 0 = $s0")
        println("Comprobacion: Step 1 = $s1")

        val times = 101741582076661
        val p1 = powmod(A, times, size)*X
        val p2 = powmod(A, times, size)-1
        val p3 = modinv(A-1, size) * B
        println("p1 = $p1")
        println("p2 = $p2")
        println("p3 = $p3")
        println((p1 + p2 * p3) % size)
        println((powmod(A, times, size)*X + (powmod(A, times, size)-1) * modinv(A-1, size) * B) % size)

//        println("Result 2020: $position")
        Time.print()
    }

    private fun powmod(a1: Long, a2: Long, mod: Long): Long {
        val a = BigInteger.valueOf(a1)
        val b = BigInteger.valueOf(a2)
        val m = BigInteger.valueOf(mod)
        return (a.modPow(b, m)).toLong()
    }

    private fun modinv(a1: Long, mod: Long): Long {
        val a = BigInteger.valueOf(a1)
        val m = BigInteger.valueOf(mod)
        return (a.modInverse(m).toLong())
    }
}

private fun dealWithIncrementPosK(listSize: Long, step: Int, k: Long): Long {
    val kModStep = k % step
    val completeLoops = 0 - kModStep
    var pos = 0L
    if (kModStep == 0L) return pos + (k/step) + 1
    for (i in 1 until step) {
        pos = (listSize*i-1) / step
        val offset = abs(step-(listSize*i % step))
        if (offset == kModStep) return pos + (k/step) + 1
    }
    return -1
}

fun main() {
    val input1 = Advent22b("input22.txt")
    input1.run()
}
