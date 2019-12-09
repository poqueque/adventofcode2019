package net.poquesoft.adventofcode

class IntCode6(initArray: List<Int>) {
    companion object {
        const val HALTCODE = Int.MIN_VALUE
    }

    var keepOn = true
    var i = 0
    var k = 0
    private val d = initArray.toMutableList()
    var output = 0
    var inputList = mutableListOf<Int>()
    var halted = false

    fun input(i:Int){
        inputList.add(i)
    }

    fun run() : Int {
        while (keepOn) {
            if (d.size > i+3)
                println("Step $i:  ${d[i]} ${d[i+1]} ${d[i+2]} ${d[i+3]}")
            var l = 4
            when (d[i]) {
                1 -> d[d[i + 3]] = d[d[i + 1]] + d[d[i + 2]]
                2 -> d[d[i + 3]] = d[d[i + 1]] * d[d[i + 2]]
                3 -> { d[d[i + 1]] = inputList[k]
                    l = 2
                    k++
                }
                4 -> {
                    output = d[d[i + 1]]
                    println ("OUTPUT: $output")
                    l=2
                    i += l //Special case because return
                    return output
                }
                5 -> {
                    if (d[d[i + 1]] != 0) {
                        i=d[d[i + 2]]
                        l=0
                    } else l=3
                }
                6 -> {
                    if (d[d[i + 1]] == 0) {
                        i=d[d[i + 2]]
                        l=0
                    } else l=3
                }
                7 -> {
                    if (d[d[i + 1]] < d[d[i + 2]]) d[d[i + 3]] = 1 else d[d[i + 3]] = 0
                }
                8 -> {
                    if (d[d[i + 1]] == d[d[i + 2]]) d[d[i + 3]] = 1 else d[d[i + 3]] = 0
                }
                99 -> keepOn = false
                in 100..Int.MAX_VALUE -> {
                    val intStr = d[i].toString()
                    val opcode = d[i] %100
                    val pmode1 = if (intStr.length > 2) intStr[intStr.length-3].toString().toInt() else 0
                    val pmode2 = if (intStr.length > 3) intStr[intStr.length-4].toString().toInt() else 0
                    val pmode3 = if (intStr.length > 4) intStr[intStr.length-5].toInt() else 0
                    val p1 = if (pmode1 == 1) d[i+1] else d[d[i+1]]
                    val p2 = try{if (pmode2 == 1) d[i+2] else d[d[i+2]]} catch (e: Exception){0}
                    val p3 = try{if (pmode3 == 1) d[i+3] else d[d[i+3]]} catch (e: Exception){0}
                    when (opcode){
                        1 -> d[d[i+3]] = p1 + p2
                        2 -> d[d[i+3]] = p1 * p2
                        3 -> {
                            d[d[i + 1]] = inputList[k]
                            l = 2
                            k++
                        }
                        4 -> {
                            output = p1
                            println ("OUTPUT: $output")
                            l=2
                            i += l //Special case because return
                            return output
                        }
                        5 -> {
                            if (p1 != 0) {
                                i=p2
                                l=0
                            }else l=3
                        }
                        6 -> {
                            if (p1 == 0) {
                                i=p2
                                l=0
                            }else l=3
                        }
                        7 -> {
                            if (p1 < p2) d[d[i + 3]] = 1 else d[d[i + 3]] = 0
                        }
                        8 -> {
                            if (p1 == p2) d[d[i + 3]] = 1 else d[d[i + 3]] = 0
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