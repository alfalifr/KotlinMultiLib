package sidev.lib.math

import sidev.lib.collection.duplicatIntersect
import sidev.lib.collection.duplicatUnion
import sidev.lib.math.number.WholeNumber
import sidev.lib.math.number.asWholeNumber
import sidev.lib.math.number.rem
import sidev.lib.math.number.wholeNumber

/*
=============================
Int
=============================
 */
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
        = try{ (primeFactors() duplicatIntersect other.primeFactors()).reduce { acc, i -> acc * i } }
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

/*
=============================
Long
=============================
 */
fun Long.isPrime(): Boolean{
    for(i in 2 until this)
        if(this % i == 0L)
            return false
    return true
}
fun Long.nextPrime(): Long{
    var primeItr= this + 1L
    while(!primeItr.isPrime())
        primeItr++
    return primeItr
}

infix fun Long.isFactorOf(other: Long): Boolean = other % this == 0L

fun Long.primeFactors(): List<Long>{
    val factors= ArrayList<Long>()
    var factorProgression= this
    var primeItr= 2L
    while(factorProgression > 1){
        if(primeItr isFactorOf factorProgression){
            factorProgression /= primeItr
            factors += primeItr
        } else
            primeItr= primeItr.nextPrime()
    }
    return factors
}

infix fun Long.kpk(other: Long): Long = (primeFactors() duplicatUnion other.primeFactors()).reduce { acc, i -> acc * i }
infix fun Long.fpb(other: Long): Long
        = try{ (primeFactors() duplicatIntersect other.primeFactors()).reduce { acc, i -> acc * i } }
        catch(e: UnsupportedOperationException){ 1 }

fun kpk(vararg numbers: Long): Long
    = numbers.map{ it.primeFactors() }
    .reduce { acc, list -> acc duplicatUnion list }
    .reduce { acc, i -> acc * i }

fun fpb(vararg numbers: Long): Long
    = try{
        numbers.map{ it.primeFactors() }
            .reduce { acc, list -> (acc intersect list).toList() }
            .reduce { acc, i -> acc * i }
    } catch (e: UnsupportedOperationException){ 1 }

fun factorial(n: Long): Long= if(n <= 1) 1 else n * factorial(n-1)



/*
=============================
WholeNumber
=============================
 */
fun WholeNumber.isPrime(): Boolean{
    for(i in 2 until longValue)
        if(this % i == 0L)
            return false
    return true
}
fun WholeNumber.nextPrime(): WholeNumber{
    var primeItr= this + 1L
    while(!primeItr.isPrime())
        primeItr++
    return primeItr.asWholeNumber()
}

infix fun WholeNumber.isFactorOf(other: WholeNumber): Boolean = (other % this).compareTo(0) == 0

fun WholeNumber.primeFactors(): List<WholeNumber>{
    val factors= ArrayList<WholeNumber>()
    var factorProgression= this.longValue
    var primeItr= 2L //wholeNumber(2)
    while(factorProgression > 1){
        if(primeItr isFactorOf factorProgression){
            factorProgression /= primeItr
            factors += wholeNumber(primeItr)
        } else
            primeItr= primeItr.nextPrime()
    }
    return factors
}

infix fun WholeNumber.kpk(other: WholeNumber): WholeNumber = (primeFactors() duplicatUnion other.primeFactors()).reduce { acc, i -> acc * i }
infix fun WholeNumber.fpb(other: WholeNumber): WholeNumber
        = try{ (primeFactors() duplicatIntersect other.primeFactors()).reduce { acc, i -> acc * i } }
        catch(e: UnsupportedOperationException){ wholeNumber(1) }

fun kpk(vararg numbers: WholeNumber): WholeNumber
    = numbers.map{ it.primeFactors() }
    .reduce { acc, list -> acc duplicatUnion list }
    .reduce { acc, i -> acc * i }

fun fpb(vararg numbers: WholeNumber): WholeNumber
    = try{
        numbers.map{ it.primeFactors() }
            .reduce { acc, list -> (acc intersect list).toList() }
            .reduce { acc, i -> acc * i }
    } catch (e: UnsupportedOperationException){ wholeNumber(1) }

fun factorial(n: WholeNumber): WholeNumber= if(n <= 1) wholeNumber(1) else n * factorial(wholeNumber(n-1))