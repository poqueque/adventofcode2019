package net.poquesoft.adventofcode

import java.io.File

class Advent14 (private val file: String){

    val map = mutableMapOf<Element,List<Element>>()

    fun calculateFuel() : Long {
        File(file).forEachLine {
            val data = it.split("=>")
            val produced = data[1].trim()
            val ingredients = data[0].trim()
            val prod = Element(produced)
            val i = ingredients.split(",")
            val l = mutableListOf<Element>()
            i.map{ ing ->
                l.add(Element(ing.trim()))
            }
            map[prod] = l
        }
        //Find ORES
        val ORES = 0
        val ingredients = mutableMapOf("FUEL" to 3687786L)
        while (!hasOnlyORE(ingredients)){
            print ("Remaining ingredients: ")
            println (ingredients.filter{(k,v) -> v>0 && k != "ORE"}.keys.size)
            val newIng  = ingredients.filter{(k,v) -> v>0 && k != "ORE"}.keys.shuffled().take(1)[0]
            println ("NEW: $newIng")
            val rules  = map.filter{(k,v)-> k.element==newIng}
            val q = rules.keys.take(1)[0].quantity
            val els = rules.values.take(1)[0]
            val multiplier = ingredients[newIng]!! / q
            if (multiplier > 1) {
                ingredients[newIng] = ingredients[newIng]!! - multiplier*q
                els.forEach {
                    if (!ingredients.containsKey(it.element)) ingredients[it.element] = 0
                    ingredients[it.element] = ingredients[it.element]!! + multiplier*it.quantity
                }
            }
            while (ingredients[newIng]!!>0){
                ingredients[newIng] = ingredients[newIng]!!-q
                els.forEach{
                    if (!ingredients.containsKey(it.element)) ingredients[it.element] = 0
                    ingredients[it.element] = ingredients[it.element]!! + it.quantity
                }
            }
            println(ingredients)
        }
        return ingredients["ORE"]!!
    }

    private fun hasOnlyORE(ingredients: MutableMap<String, Long>): Boolean {
        ingredients.map{
            if (it.key != "ORE" && it.value > 0) return false
        }
        return true
    }
}

data class Element(val element: String, val quantity: Long) {
    constructor(element: String) : this(element.split(" ")[1].trim(),element.split(" ")[0].trim().toLong())

    override fun toString(): String {
        return "$quantity $element"
    }
}

fun main() {
/*    val a = Advent14("input14a.txt")
    println ("Test 1 : ${a.calculateFuel()} should be 31 ORE")
    val b = Advent14("input14b.txt")
    println ("Test 2 : ${b.calculateFuel()} should be 165 ORE")
    val c = Advent14("input14c.txt")
    println ("Test 3 : ${c.calculateFuel()} should be 13312 ORE")
*/

    val input = Advent14("input14.txt")
    println ("Input : ${input.calculateFuel()}")

}
