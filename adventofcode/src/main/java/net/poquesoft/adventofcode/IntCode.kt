package net.poquesoft.adventofcode

class IntCode(initArray: List<Long>) {
    companion object {
        const val HALTCODE = Int.MIN_VALUE.toLong()
    }

    var keepOn = true
    var i = 0
    var k = 0
    private var d = initArray.toMutableList()
    var output = 0L
    var inputList = mutableListOf<Long>()
    var halted = false
    var relativeBase = 0

    fun input(i:Long){
        inputList.add(i)
    }

    fun addZeroes(){
        val zeroes = MutableList(100){0L}
        d.addAll(zeroes)
    }

    fun run() : Long {
        while (keepOn) {
            if (d.size > i+3)
                println("Step $i:  ${d[i]} ${d[i+1]} ${d[i+2]} ${d[i+3]}")
            var l = 4
            when (d[i].toInt()) {
                1 -> d[d[i + 3].toInt()] = d[d[i + 1].toInt()] + d[d[i + 2].toInt()]
                2 -> d[d[i + 3].toInt()] = d[d[i + 1].toInt()] * d[d[i + 2].toInt()]
                3 -> { d[d[i + 1].toInt()] = inputList[k]
                    l = 2
                    k++
                }
                4 -> {
                    output = d[d[i + 1].toInt()]
                    println ("OUTPUT: $output")
                    l=2
/*                    i += l //Special case because return
                    return output*/
                }
                5 -> {
                    if (d[d[i + 1].toInt()] != 0L) {
                        i=d[d[i + 2].toInt()].toInt()
                        l=0
                    } else l=3
                }
                6 -> {
                    if (d[d[i + 1].toInt()] == 0L) {
                        i=d[d[i + 2].toInt()].toInt()
                        l=0
                    } else l=3
                }
                7 -> {
                    if (d[d[i + 1].toInt()] < d[d[i + 2].toInt()]) d[d[i + 3].toInt()] = 1 else d[d[i + 3].toInt()] = 0
                }
                8 -> {
                    if (d[d[i + 1].toInt()] == d[d[i + 2].toInt()]) d[d[i + 3].toInt()] = 1 else d[d[i + 3].toInt()] = 0
                }
                9 -> {
                    relativeBase += d[d[i+1].toInt()].toInt()
                    l=2
                }
                99 -> keepOn = false

                in 100..Int.MAX_VALUE -> {
                    val intStr = d[i].toString()
                    val opcode = d[i] %100
                    val pmode1 = if (intStr.length > 2) intStr[intStr.length-3].toString().toInt() else 0
                    val pmode2 = if (intStr.length > 3) intStr[intStr.length-4].toString().toInt() else 0
                    val pmode3 = if (intStr.length > 4) intStr[intStr.length-5].toString().toInt() else 0
                    val p1: Long = when (pmode1) {
                        0 -> d[d[i+1].toInt()]
                        1 -> d[i+1]
                        2 -> d[relativeBase+d[i+1].toInt()]
                        else -> {
                            println("Error. Parameter mode = $pmode1")
                            HALTCODE
                        }
                    }
                    val p2: Long = try{
                        when (pmode2) {
                            0 -> d[d[i+2].toInt()]
                            1 -> d[i+2]
                            2 -> d[relativeBase+d[i+2].toInt()]
                            else -> {
                                println("Error. Parameter mode = $pmode2")
                                HALTCODE
                            }
                        }
                    } catch (e: Exception){0}
                    val p3pos:Int = try{
                        when (pmode3) {
                            0 -> d[i+3].toInt()
                            1 -> i+3
                            2 -> (relativeBase+d[i+3]).toInt()
                            else -> {
                                println("Error. Parameter mode = $pmode3")
                                HALTCODE.toInt()
                            }
                        }
                    } catch (e: Exception){0}
                    when (opcode.toInt()){
                        1 -> d[p3pos] = p1 + p2
                        2 -> d[p3pos] = p1 * p2
                        3 -> {
                            d[p3pos] = inputList[k]
                            l = 2
                            k++
                        }
                        4 -> {
                            output = p1
                            println ("OUTPUT: $output")
                            l=2
                            /*i += l //Special case because return
                            return output*/
                        }
                        5 -> {
                            if (p1 != 0L) {
                                i=p2.toInt()
                                l=0
                            }else l=3
                        }
                        6 -> {
                            if (p1 == 0L) {
                                i=p2.toInt()
                                l=0
                            }else l=3
                        }
                        7 -> {
                            if (p1 < p2) d[p3pos] = 1 else d[p3pos] = 0
                        }
                        8 -> {
                            if (p1 == p2) d[p3pos] = 1 else d[p3pos] = 0
                        }
                        9 -> {
                            relativeBase += p1.toInt()
                            l=2
                        }
                        99 -> keepOn = false
                    }
                }
                else -> {
                    keepOn = false
                    println("Error: Command is ${d[i]}")
                }
            }
//        d.forEach { print("$it ") }
            println("")
            i += l
        }
        halted = true
        return HALTCODE
    }

}