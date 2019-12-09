package net.poquesoft.adventofcode

fun main() {
    val test1 = IntCode(listOf(109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99)) //takes no input and produces a copy of itself as output.
    test1.addZeroes()
    test1.run()
    println("Should have output (109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99)")
}