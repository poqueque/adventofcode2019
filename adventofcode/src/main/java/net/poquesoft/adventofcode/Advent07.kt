package net.poquesoft.adventofcode

fun main(){
    val phaseSetting = listOf(5,6,7,8,9)
    val listPS = permute(phaseSetting)
    var max = 0
    listPS.map{
        val a = amplify((it))
        if (a > max) max = a
    }
    println("MAX: $max")

/*
    val e  = amplify(listOf(9,7,8,5,6))
    println("E = $e")
*/
}
//30940

fun <Int> permute(list:List <Int>):List<List<Int>>{
    if(list.size==1) return listOf(list)
    val perms=mutableListOf<List <Int>>()
    val sub=list[0]
    for(perm in permute(list.drop(1)))
        for (i in 0..perm.size){
            val newPerm=perm.toMutableList()
            newPerm.add(i,sub)
            perms.add(newPerm)
        }
    return perms
}

fun amplify(phaseSetting: List<Int>) : Int{
    val initArray = listOf(3,8,1001,8,10,8,105,1,0,0,21,34,43,64,85,98,179,260,341,422,99999,3,9,1001,9,3,9,102,3,9,9,4,9,99,3,9,102,5,9,9,4,9,99,3,9,1001,9,2,9,1002,9,4,9,1001,9,3,9,1002,9,4,9,4,9,99,3,9,1001,9,3,9,102,3,9,9,101,4,9,9,102,3,9,9,4,9,99,3,9,101,2,9,9,1002,9,3,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,99)
//    val initArray = listOf(3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,
//        -5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,
//        53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10)
    var lastE = 0

    val amplifierA = IntCode6(initArray)
    val amplifierB = IntCode6(initArray)
    val amplifierC = IntCode6(initArray)
    val amplifierD = IntCode6(initArray)
    val amplifierE = IntCode6(initArray)

    amplifierA.input(phaseSetting[0])
    amplifierB.input(phaseSetting[1])
    amplifierC.input(phaseSetting[2])
    amplifierD.input(phaseSetting[3])
    amplifierE.input(phaseSetting[4])
    amplifierA.input(0)
    loop@ for (i in 1..20) {
        val ampAret = amplifierA.run()
        println("A = $ampAret")
        if (ampAret == IntCode6.HALTCODE) break@loop

        amplifierB.input(ampAret)
        val ampBret = amplifierB.run()
        println("B = $ampBret")
        if (ampBret == IntCode6.HALTCODE) break@loop

        amplifierC.input(ampBret)
        val ampCret = amplifierC.run()
        println("C = $ampCret")
        if (ampCret == IntCode6.HALTCODE) break@loop

        amplifierD.input(ampCret)
        val ampDret = amplifierD.run()
        println("D = $ampDret")
        if (ampDret == IntCode6.HALTCODE) break@loop

        amplifierE.input(ampDret)
        val ampEret = amplifierE.run()
        println("E = $ampEret")
        lastE = ampEret
        if (ampEret == IntCode6.HALTCODE) break@loop

        amplifierA.input(ampEret)
    }

    return lastE
}
