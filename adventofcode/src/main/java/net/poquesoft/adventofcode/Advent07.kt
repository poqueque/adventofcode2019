package net.poquesoft.adventofcode

fun main() {
    val test1  = amplify(
        listOf(3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0),
        listOf(4,3,2,1,0)
    )
    assert(test1 == 43210L)
    println("E = $test1 should be 43210")

    val test2  = amplify(
        listOf(3,23,3,24,1002,24,10,24,1002,23,-1,23,
            101,5,23,23,1,24,23,23,4,23,99,0,0),
        listOf(0,1,2,3,4)
    )
    assert(test2 == 54321L)
    println("E = $test2 should be 54321")

    val test3  = amplify(
        listOf(3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,
            1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0),
        listOf(1,0,4,3,2)
    )
    assert(test3 == 65210L)
    println("E = $test3 should be 65210")

    val initArray: List<Long> = listOf(3,8,1001,8,10,8,105,1,0,0,21,34,43,64,85,98,179,260,341,422,99999,3,9,1001,9,3,9,102,3,9,9,4,9,99,3,9,102,5,9,9,4,9,99,3,9,1001,9,2,9,1002,9,4,9,1001,9,3,9,1002,9,4,9,4,9,99,3,9,1001,9,3,9,102,3,9,9,101,4,9,9,102,3,9,9,4,9,99,3,9,101,2,9,9,1002,9,3,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,99)

    val phaseSetting: List<Long> = listOf(0, 1, 2, 3, 4)
    val listPS = permute(phaseSetting)
    var max = 0L
    listPS.map {
        val a = amplify(initArray, it)
        if (a > max) max = a
    }
    assert(max == 30940L)
    println("MAX: $max should be 30940")


    // SECOND PART

    val test4  = amplify(
        listOf(3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
            27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5),
        listOf(9,8,7,6,5)
    )
    assert(test4 == 139629729L)
    println("E = $test4 should be 139629729")


    val test5  = amplify(
        listOf(3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,
            -5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,
            53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10),
        listOf(9,7,8,5,6)
    )
    assert(test5 == 18216L)
    println("E = $test5 should be 18216")

    val phaseSetting2: List<Long> = listOf(5, 6, 7, 8, 9)
    val listPS2 = permute(phaseSetting2)
    var max2 = 0L
    listPS2.map {
        val a = amplify(initArray, it)
        if (a > max2) max2 = a
    }
    assert(max2 == 76211147L)
    println("MAX: $max2 should be 76211147")

}
//30940
//76211147

fun <Long> permute(list: List<Long>): List<List<Long>> {
    if (list.size == 1) return listOf(list)
    val perms = mutableListOf<List<Long>>()
    val sub = list[0]
    for (perm in permute(list.drop(1)))
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, sub)
            perms.add(newPerm)
        }
    return perms
}

fun amplify(initArray: List<Long>, phaseSetting: List<Long>): Long {
//    val initArray: List<Long> = listOf(3,8,1001,8,10,8,105,1,0,0,21,34,43,64,85,98,179,260,341,422,99999,3,9,1001,9,3,9,102,3,9,9,4,9,99,3,9,102,5,9,9,4,9,99,3,9,1001,9,2,9,1002,9,4,9,1001,9,3,9,1002,9,4,9,4,9,99,3,9,1001,9,3,9,102,3,9,9,101,4,9,9,102,3,9,9,4,9,99,3,9,101,2,9,9,1002,9,3,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,99)
//    val initArray = listOf(3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,
//        -5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,
//        53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10)
    var lastE = 0L

    val amplifierA = IntCode(initArray, true)
    val amplifierB = IntCode(initArray, true)
    val amplifierC = IntCode(initArray, true)
    val amplifierD = IntCode(initArray, true)
    val amplifierE = IntCode(initArray, true)

    amplifierA.input(phaseSetting[0])
    amplifierB.input(phaseSetting[1])
    amplifierC.input(phaseSetting[2])
    amplifierD.input(phaseSetting[3])
    amplifierE.input(phaseSetting[4])
    amplifierA.input(0)
    loop@ for (i in 1..20) {
        val ampAret = amplifierA.run()
        println("A = $ampAret")
        if (ampAret == IntCode.HALTCODE) break@loop

        amplifierB.input(ampAret)
        val ampBret = amplifierB.run()
        println("B = $ampBret")
        if (ampBret == IntCode.HALTCODE) break@loop

        amplifierC.input(ampBret)
        val ampCret = amplifierC.run()
        println("C = $ampCret")
        if (ampCret == IntCode.HALTCODE) break@loop

        amplifierD.input(ampCret)
        val ampDret = amplifierD.run()
        println("D = $ampDret")
        if (ampDret == IntCode.HALTCODE) break@loop

        amplifierE.input(ampDret)
        val ampEret = amplifierE.run()
        println("E = $ampEret")
        lastE = ampEret
        if (ampEret == IntCode.HALTCODE) break@loop

        amplifierA.input(ampEret)
    }

    return lastE
}
