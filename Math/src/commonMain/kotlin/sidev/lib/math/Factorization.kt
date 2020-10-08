package sidev.lib.math

import sidev.lib.collection.duplicatUnion

//import sidev.lib.collection.duplicatUnion

//import sidev.lib.
fun Int.isPrime(): Boolean{
    for(i in 2 until this)
        if(this % i == 0)
            return false
    return true
}
fun Int.nextPrime(): Int{
    var primeItr= this +1
    while(!primeItr.isPrime())
        primeItr++
    return primeItr
}

infix fun Int.isFactorOf(other: Int): Boolean = other % this == 0

fun Int.primeFactors(): List<Int>{
    val factors= ArrayList<Int>()
    var factorProgression= this
    var primeItr= 2
    while(factorProgression > 1){
        if(primeItr isFactorOf factorProgression){
            factorProgression /= primeItr
            factors += primeItr
        } else
            primeItr= primeItr.nextPrime()
    }
    return factors
}

infix fun Int.kpk(other: Int): Int = (primeFactors() duplicatUnion other.primeFactors()).reduce { acc, i -> acc * i }
infix fun Int.fpb(other: Int): Int
        = try{ (primeFactors() intersect  other.primeFactors()).reduce { acc, i -> acc * i } }
        catch(e: UnsupportedOperationException){ 1 }

fun kpk(vararg numbers: Int): Int
    = numbers.map{ it.primeFactors() }
    .reduce { acc, list -> acc duplicatUnion list }
    .reduce { acc, i -> acc * i }

fun fpb(vararg numbers: Int): Int
    = try{
        numbers.map{ it.primeFactors() }
            .reduce { acc, list -> (acc intersect list).toList() }
            .reduce { acc, i -> acc * i }
    } catch (e: UnsupportedOperationException){ 1 }

fun factorial(n: Int): Int= if(n <= 1) 1 else n * factorial(n-1)