package net.poquesoft.adventofcode

import java.io.File
import java.math.BigInteger
import kotlin.math.abs

class Advent22c(val file: String) {

    val size = 119315717514047
    var position = 0L
    val X = 2020L
    var Y = 2020L
    var Z = 2020L
    val orders = mutableListOf<String>()

    fun run() {
        Time.start()
        println("Creating deck...")
        println("Shuffling...")
        File(file).forEachLine {
            orders.add(it)
        }
        orders.reverse()
        println("           [$position]")
        for (k in 0 until 2) {
            position = f(position)
            print("step $k: ")
            println(position)
            if (k == 0) Y = position
            if (k == 1) Z = position
        }


        val X = 2020L
        val Y = f(X)
        val Z = f(Y)
/*
        var A = modprod((Y-Z), modinv(X-Y,size), size)
        if (A<0) A+=size
        var B = (Y - modprod(A,X,size)) % size
        if (B<0) B+=size
*/

        val B = f(0L)
        val A = (f(1L) - B + size) % size

        println("A: $A")
        println("B: $B")
        println("f(2): ${f(2L)}")
        println("A*X + B: ${(A * 2 + B) % size}")


        val s0 = (A * 2020 + B) % size
        val s1 = (modprod(A, s0,size) + B) % size
        println("Comprobacion: Step 0 = $s0  ${f(2020)}")
        println("Comprobacion: Step 1 = $s1  ${f(s0)}")

        val times = 101741582076661
        val p1 = modprod(powmod(A, times, size),X,size)
        val p2 = powmod(A, times, size) - 1
        val p3 = modprod(modinv(A - 1, size),B, size)
        println("p1 = $p1")
        println("p2 = $p2")
        println("p3 = $p3")
        println((p1 + modprod(p2, p3,size)) % size)

//        println("Result 2020: $position")
        Time.print()
    }

    private fun f(positionInit: Long): Long {
        var position = positionInit
        orders.map {
            if (it.startsWith("deal with increment ")) {
//                    println(it)
                val step = it.substring(20).toLong()
                position = modprod(modinv(step, size), position, size)
                if (position < 0) position += size
//                    println("           [$position]")
                if (position > size) println("WARNING: SIZE MISMATCH")
            }
            if (it.startsWith("cut ")) {
//                    println(it)
                val step = it.substring(4).toLong()
                position = (position + step + size) % size
//                    println("           [$position]")
                if (position < 0) position += size
                if (position > size) println("WARNING: SIZE MISMATCH")
            }
            if (it.startsWith("deal into new stack")) {
//                    println(it)
                position = (size - 1 - position) % size
//                    println("           [$position]")
                if (position < 0) position += size
                if (position > size) println("WARNING: SIZE MISMATCH")
            }
        }
        return position
    }

    private fun powmod(a1: Long, a2: Long, mod: Long): Long {
        val a = BigInteger.valueOf(a1)
        val b = BigInteger.valueOf(a2)
        val m = BigInteger.valueOf(mod)
        return (a.modPow(b, m)).toLong()
    }

    private fun powmod2(a1: Long, a2: Long, p: Long): Long {
        var x = a1
        var y = a2
        // Initialize result
        // Initialize result
        var res = 1
        x %= p

        while (y > 0) {
            if (y and 1 == 1L) res = (res * x % p).toInt()
            y = y shr 1
            x = x * x % p
        }
        return res.toLong()
    }

    private fun modinv(a1: Long, mod: Long): Long {
        val a = BigInteger.valueOf(a1)
        val m = BigInteger.valueOf(mod)
        return (a.modInverse(m).toLong())
    }

    private fun modinv2(a1: Long, mod: Long): Long {
        var c1 = 1L
        var c2 = -(mod / a1)

        var t1 = 0L
        var t2 = 1L

        var r = mod % a1

        var x = a1
        var y = r
        var c: Long
        while (r != 0L) {
            c = x / y
            r = x % y
            c1 *= -c
            c2 *= -c
            c1 += t1
            c2 += t2
            t1 = -(c1 - t1) / c
            t2 = -(c2 - t2) / c
            x = y
            y = r
        }
        return t2
    }

    fun modprod(a1: Long, b1: Long, mod: Long): Long {
        val a = BigInteger.valueOf(a1)
        val b = BigInteger.valueOf(b1)
        val m = BigInteger.valueOf(mod)
        return (a*b%m).toLong()
    }

    fun modprod2(a1: Long, b1: Long, mod: Long): Long{
        var a = a1
        var b = b1
        var res: Long = 0

        a %= mod

        while (b > 0) { // If b is odd, add a with result
            if (b and 1 > 0) {
                res = (res + a) % mod
            }
            a = 2 * a % mod
            b = b shr 1 // b = b / 2
        }
        return res
    }
}

private fun dealWithIncrementPosK(listSize: Long, step: Int, k: Long): Long {
    val kModStep = k % step
    val completeLoops = 0 - kModStep
    var pos = 0L
    if (kModStep == 0L) return pos + (k / step) + 1
    for (i in 1 until step) {
        pos = (listSize * i - 1) / step
        val offset = abs(step - (listSize * i % step))
        if (offset == kModStep) return pos + (k / step) + 1
    }
    return -1
}

fun main() {
    val input1 = Advent22c("input22.txt")
    input1.run()
}
