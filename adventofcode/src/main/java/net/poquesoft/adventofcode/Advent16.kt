package net.poquesoft.adventofcode

import java.util.*
import kotlin.math.abs

class Advent16(private var inputSignal: String) {

    val time = Date().time

    fun run(test: Boolean = true): String {
        var mOffset = 0
        var intInputSignal = inputSignal.map { it.toString().toInt() }
        if (!test) {
            mOffset = inputSignal.substring(0, 7).toInt()
            println("OFFSET= $mOffset")
            val nInput = intInputSignal.toMutableList()
            for (i in 1..9999) {
                nInput.addAll(intInputSignal)
            }
            intInputSignal = nInput
        }
        println (intInputSignal.size)
        println (inputSignal.length)

        println("Initial offset: ${intInputSignal.subList(mOffset, mOffset + 8)}")

        var outputSignal = intInputSignal
        for (i in 1..100) {
            outputSignal = phaseInt(outputSignal, mOffset)
            val data =
                if (mOffset == 0) outputSignal else outputSignal.subList(mOffset, mOffset + 8)
            println("After $i phases: $data")
            val elapsed = Date().time - time
            println("Elapsed: ${elapsed/1000} seconds")
        }

        return if (mOffset == 0) outputSignal.toString() else outputSignal.subList(
            mOffset,
            mOffset + 8
        ).toString()
    }

    fun runF(test: Boolean = true): String {
        var mOffset = 0
        if (!test) {
            mOffset = inputSignal.substring(0, 7).toInt()
            println("OFFSET= $mOffset")
            var nInput = ""
            for (i in 1..10000) {
                nInput += inputSignal
            }
            inputSignal = nInput
        }
        val intInputSignal = inputSignal.map { it.toString().toInt() }

        println("Initial offset: ${intInputSignal.subList(mOffset, mOffset + 8)}")

        var outputSignal = inputSignal
        for (i in 1..100) {
            outputSignal = phase(outputSignal, mOffset)
            val data =
                if (mOffset == 0) outputSignal else outputSignal.substring(mOffset, mOffset + 8)
            println("After $i phases: $data")
        }

        return if (mOffset == 0) outputSignal else outputSignal.substring(mOffset, mOffset + 8)
    }

    private fun phase(inputSignal: String, mOffset: Int): String {
        var newSignal = "0".repeat(mOffset - 1)
        for (j in mOffset until inputSignal.length) {
            var n = 0
            if (false/*mOffset * 2 > inputSignal.length*/) {
                n = inputSignal.substring(j).map { it.toString().toInt() }.sum() % 10
            } else {
                if (j >= mOffset) {
                    val fr = 4 * (j + 1)
                    var pos = j
                    while (pos < inputSignal.length) {
                        for (k in 0..j) {
                            if (pos + k < inputSignal.length) n =
                                (n + inputSignal[(pos + k)].toString().toInt())
                        }
                        pos += fr
                    }
                    pos = j + fr / 2
                    while (pos < inputSignal.length) {
                        for (k in 0..j) {
                            if (pos + k < inputSignal.length) n =
                                (n - inputSignal[pos + k].toString().toInt())
                        }
                        pos += fr
                    }

                    n = abs(n) % 10
                }
            }
            newSignal += n.toString()
            if (j % 10000 == 0) print(".")

//            if (j % 10000 == 0) println("$j/${inputSignal.length} indices processed")
//            print(n.toString())
        }
        println()
        return newSignal
    }


    private fun phaseInt2(inputSignal: List<Int>, mOffset: Int): List<Int> {
        val newSignal = if (mOffset == 0) mutableListOf() else MutableList(mOffset-1) { 0 }
        var n=0
        val suffix = mutableListOf<Int>()
        for (j in inputSignal.size-1 downTo mOffset-1) {
            n = (n + inputSignal[j]) % 10
            suffix.add(0,n)
        }
        newSignal.addAll(suffix)
        return newSignal
    }

    private fun phaseInt(inputSignal: List<Int>, mOffset: Int): List<Int> {
        val newSignal = if (mOffset == 0) mutableListOf() else MutableList(mOffset) { 0 }
        val cm = "."
        var remaining = inputSignal.toMutableList().drop(mOffset).sum() %10

        for (j in mOffset until inputSignal.size) {
            var n = 0
            if (mOffset * 2 > inputSignal.size) {
//                n = inputSignal.subList(j, inputSignal.size).sum() % 10
                n = (remaining + 10 - inputSignal[j-1]) %10
                remaining = n
/*
                inputSignal.subList(j, inputSignal.size).map {
                    if (n==0) n=it
                    else if (it > 0){
                        n = (n+it) %10
                    }
                }
*/
//                cm = "."
            } else {
                if (j >= mOffset) {
                    val fr = 4 * (j + 1)
                    var pos = j
                    while (pos < inputSignal.size) {
                        for (k in 0..j) {
                            if (pos + k < inputSignal.size) n =
                                (n + inputSignal[(pos + k)])
                        }
                        pos += fr
                    }
                    pos = j + fr / 2
                    while (pos < inputSignal.size) {
                        for (k in 0..j) {
                            if (pos + k < inputSignal.size) n =
                                (n - inputSignal[pos + k])
                        }
                        pos += fr
                    }

                    n = abs(n) % 10
                }
//                cm = "c"
            }
            newSignal.add(n)

            if (j % 10000 == 0) {
                print(cm)
//                val elapsed = Date().time - time
//                println("Elapsed: $elapsed")
            }
//            if (j % 10000 == 0) println("$j/${inputSignal.size} indices processed")
//            print(n.toString())
        }
        println()
        return newSignal
    }

    private fun mutatePattern(pattern: List<Int>, j: Int, length: Int): List<Int> {
        val rp = mutableListOf<Int>()
        repeat(j) { rp.add(0) }
        var i = 0
        while (rp.size < length) {
            repeat(j + 1) { rp.add(pattern[i % pattern.size]) }
            i++
        }
        return rp
    }

}

fun main() {

/*

    val input = Advent16("03036732577212944063491565474664")
    println("Output : ${input.run(false)}")


    val input0 = Advent16(
        "03036732577212944063491565474664"
    )
    println("Output : ${input0.run(false)}")
*/

    val input1 = Advent16(
        "59775675999083203307460316227239534744196788252810996056267313158415747954523514450220630777434694464147859581700598049220155996171361500188470573584309935232530483361639265796594588423475377664322506657596419440442622029687655170723364080344399753761821561397734310612361082481766777063437812858875338922334089288117184890884363091417446200960308625363997089394409607215164553325263177638484872071167142885096660905078567883997320316971939560903959842723210017598426984179521683810628956529638813221927079630736290924180307474765551066444888559156901159193212333302170502387548724998221103376187508278234838899434485116047387731626309521488967864391")
    println("Output : ${input1.run(false)}")

/*
    val input2 = Advent16(
    "59775675999083203307460316227239534744196788252810996056267313158415747954523514450220630777434694464147859581700598049220155996171361500188470573584309935232530483361639265796594588423475377664322506657596419440442622029687655170723364080344399753761821561397734310612361082481766777063437812858875338922334089288117184890884363091417446200960308625363997089394409607215164553325263177638484872071167142885096660905078567883997320316971939560903959842723210017598426984179521683810628956529638813221927079630736290924180307474765551066444888559156901159193212333302170502387548724998221103376187508278234838899434485116047387731626309521488967864391")
    println("Output : ${input2.run(false)}")
*/
//    println("Output : ${input2.run(false)} has to start with 24176176")

}
