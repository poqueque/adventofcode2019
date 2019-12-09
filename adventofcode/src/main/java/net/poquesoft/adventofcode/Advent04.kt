package net.poquesoft.adventofcode


fun meetCriteria(number: Int): Boolean {
    if (number < 100000) return false
    if (number > 999999) return false
    val strNum = number.toString()
    if (!alwaysIncrease(strNum)) return false
    if (!has2ConsecutiveDigits(strNum)) return false
    return true
}

fun alwaysIncrease(strNum: String): Boolean {
    var prev = 0
    for (i in strNum.indices){
        if (strNum[i].toInt() < prev) return false
        prev = strNum[i].toInt()
    }
    return true
}

fun has2ConsecutiveDigits(strNum: String): Boolean {
    for (i in 0 until strNum.length-1){
        if (strNum[i] == strNum[i+1] && strNum[i] != 'A') {
            return true
        }
    }
    return false
}

fun main() {

    val input = (254032..789860)
    val filtered = input.filter { meetCriteria(it) }
    val secondFilter = filtered.filter { !has3ConsecutiveDigits(it) }
    val thirdFilter = secondFilter.filter { !has3ConsecutiveDigitsTwice(it) }
    thirdFilter.map{
        println(it)
    }

    println("Total: ${thirdFilter.size}")
}

fun has3ConsecutiveDigitsTwice(number: Int): Boolean {
    val strNum = number.toString()
    if (strNum[0] == strNum[1] && strNum[0] == strNum[2]
        && strNum[3] == strNum[4] && strNum[3] == strNum[5])
        return true
    return false
}

fun has3ConsecutiveDigits(number: Int): Boolean {
    val strNum = number.toString()
    for (i in 0 until strNum.length-2){
        if (strNum[i] == strNum[i+1] && strNum[i] == strNum[i+2]) {
            val n = strNum.replace(strNum[i],'A')
            if (has2ConsecutiveDigits(n))
                return false
            return true
        }
    }
    return false}
