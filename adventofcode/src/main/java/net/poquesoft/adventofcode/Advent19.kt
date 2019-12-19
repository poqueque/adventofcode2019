package net.poquesoft.adventofcode

class Advent19{

    val m = mutableMapOf<Coor,Int>()
    private val intCode = IntCode(
        listOf(109,424,203,1,21101,11,0,0,1106,0,282,21102,1,18,0,1105,1,259,1202,1,1,221,203,1,21101,0,31,0,1105,1,282,21102,38,1,0,1106,0,259,21002,23,1,2,21202,1,1,3,21102,1,1,1,21102,57,1,0,1106,0,303,1202,1,1,222,20102,1,221,3,20102,1,221,2,21102,259,1,1,21102,80,1,0,1105,1,225,21101,0,145,2,21102,91,1,0,1105,1,303,2101,0,1,223,20101,0,222,4,21102,259,1,3,21101,225,0,2,21102,1,225,1,21102,1,118,0,1105,1,225,20101,0,222,3,21101,0,197,2,21101,133,0,0,1106,0,303,21202,1,-1,1,22001,223,1,1,21101,0,148,0,1105,1,259,1202,1,1,223,21001,221,0,4,21001,222,0,3,21102,1,19,2,1001,132,-2,224,1002,224,2,224,1001,224,3,224,1002,132,-1,132,1,224,132,224,21001,224,1,1,21102,195,1,0,105,1,109,20207,1,223,2,21002,23,1,1,21102,-1,1,3,21101,0,214,0,1105,1,303,22101,1,1,1,204,1,99,0,0,0,0,109,5,1201,-4,0,249,22101,0,-3,1,22101,0,-2,2,21202,-1,1,3,21102,250,1,0,1106,0,225,22101,0,1,-4,109,-5,2105,1,0,109,3,22107,0,-2,-1,21202,-1,2,-1,21201,-1,-1,-1,22202,-1,-2,-2,109,-3,2106,0,0,109,3,21207,-2,0,-1,1206,-1,294,104,0,99,22102,1,-2,-2,109,-3,2105,1,0,109,5,22207,-3,-4,-1,1206,-1,346,22201,-4,-3,-4,21202,-3,-1,-1,22201,-4,-1,2,21202,2,-1,-1,22201,-4,-1,1,21201,-2,0,3,21101,343,0,0,1105,1,303,1105,1,415,22207,-2,-3,-1,1206,-1,387,22201,-3,-2,-3,21202,-2,-1,-1,22201,-3,-1,3,21202,3,-1,-1,22201,-3,-1,2,22101,0,-4,1,21102,384,1,0,1106,0,303,1106,0,415,21202,-4,-1,-4,22201,-4,-3,-4,22202,-3,-2,-2,22202,-2,-4,-4,22202,-3,-2,-3,21202,-4,-1,-2,22201,-3,-2,1,22102,1,1,-4,109,-5,2105,1,0),
        true
    )

    init{
        repeat(100) {intCode.addZeroes()}
    }

    fun start() {
        var sta = 0
        var pulled = 0
        var scanStart = 1400
        var scanEnd = 2000
        val lastX = mutableMapOf<Int,Int>()
        val lastY = mutableMapOf<Int,Int>()
        for(i in scanStart..scanEnd) {
            for (j in scanStart..scanEnd) {
                val a1=getValueFromIntCode(i,j)
                if (m[Coor(i,j)] == 1) pulled++
                val esquinas = listOf(
                    a1, getValueFromIntCode(i+99,j), getValueFromIntCode(i,j+99), getValueFromIntCode(i+99,j+99))
                if (esquinas == listOf(1,1,1,1)){
                    println()
                    println("($i,$j) -> ${Coor(i,j).mod()}")
                    return
                }
            }
            print(".")
            if (i%100 == 0)println()
        }
        println("Pulled: $pulled")
/*
        for(j in 0..scan) {
            //find last x
            var x = lastY[j]
            if (x!=null) {
                var y = j
                var diagonal = 0
                while (m[Coor(x, y)] == 1) {
                    diagonal++
                    x--
                    y--
                }
                println("Linea $j: Last Y: ${lastY[j]} Diagonal = $diagonal")
            }
        }
*/

        loop@ for (size in 90..100 step 10) {
            var min = 100000.0
            var minX = 0
            var minY = 0
            for (y in scanStart..scanEnd-size+1) {
                for (x in scanStart..scanEnd-size+1) {
                    val c = checkSquare(x, y, size)
                    if (c) {
                        //                    println("Square ($x,$y) = $c")
                        val distance = Coor(x, y).mod()
                        if (distance < min) {
                            min = distance
                            minX = x
                            minY = y
                            println("For size $size: ($minX,$minY) -> $min")
//                            continue@loop
                        }
                    }
                }
            }
            println("For size $size: ($minX,$minY) -> $min")
        }
    }

    private fun getValueFromIntCode(x: Int, y: Int): Int {
        if (m[Coor(x,y)] != null) return m[Coor(x,y)]!!
        intCode.reset()
        repeat(10) { intCode.addZeroes() }
        intCode.input(x.toLong())
        intCode.input(y.toLong())
        m[Coor(x,y)] = intCode.run().toInt()
        return m[Coor(x,y)]!!
    }

    private fun checkSquare(x: Int, y: Int, size: Int): Boolean {
        for (i in x until x+size)
            for (j in y until y+size)
                if (m[Coor(i,j)] == null || m[Coor(i,j)]==0) return false
        return true
    }

}

fun main() {
    Time.start()
    val a = Advent19()
    a.start()
    Time.print()
}
