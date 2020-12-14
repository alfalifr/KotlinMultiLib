package sidev.lib.math

import sidev.lib.collection.duplicatIntersect
import sidev.lib.collection.duplicatUnion
import sidev.lib.console.prine
import sidev.lib.math.number.WholeNumber
import sidev.lib.math.number.asWholeNumber
//import sidev.lib.math.number.rem
import sidev.lib.math.number.wholeNumber
import sidev.lib.number.*
import kotlin.math.max

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


///*
/*
=============================
Number
=============================
 */
fun Number.isPrime(): Boolean {
    when(this) {
        is Long -> return isPrime()
        is Int -> return isPrime()
        is WholeNumber -> return isPrime()
    }
    val vals = noDecimalValue
    for(i in 2 until vals){
        if(vals % i == 0L)
            return false
    }
    return true
}
fun Number.nextPrime(): Number{
    val factor= 10 pow getDigitBehindDecimal()
    val vals= (this * factor).toLong()
    var primeItr= vals + 1L
    while(!primeItr.isPrime())
        primeItr++
/*
    val factor= 10 pow getDigitBehindDecimal()
    val incr= 1 / factor
    var primeItr= this + incr
    while(!primeItr.isPrime())
        primeItr += incr
*/
    return primeItr / factor
}

infix fun Number.isFactorOf(other: Number): Boolean = (other % this).compareTo(0) == 0

fun Number.primeFactors(): List<Number>{
/*
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
*/
    val factors= ArrayList<Number>()
    val factor= 10 pow getDigitBehindDecimal()
    val vals= (this * factor).toLong()

    var factorProgression= vals
    var primeItr= 2L

    while(factorProgression > 1L){
        if(primeItr isFactorOf factorProgression){
            factorProgression /= primeItr
            factors += (primeItr / factor)
        } else
            primeItr= primeItr.nextPrime()
    }
    return factors
}

infix fun Number.kpk(other: Number): Number {
    val commonScale= getFloatingCommonScale(this, other)
    val thisVal= (this * commonScale).toLong()
    val otherVal= (other * commonScale).toLong()
    return (thisVal.primeFactors() duplicatUnion otherVal.primeFactors()).reduce { acc, i -> acc * i } / commonScale
}
infix fun Number.fpb(other: Number): Number {
    val commonScale= getFloatingCommonScale(this, other)
    val thisVal= (this * commonScale).toLong()
    val otherVal= (other * commonScale).toLong()
    return try{ (thisVal.primeFactors() duplicatIntersect otherVal.primeFactors()).reduce { acc, i -> acc * i } / commonScale }
    catch(e: UnsupportedOperationException){ 1 }
}

fun kpk(vararg numbers: Number): Number {
    val commonScale= getCommonScale(*numbers)
    return numbers.map { (it * commonScale).toLong() }
        .map{ it.primeFactors() }
        .reduce { acc, list -> acc duplicatUnion list }
        .reduce { acc, i -> acc * i } / commonScale.toDouble()
}

fun fpb(vararg numbers: Number): Number {
    val commonScale= getCommonScale(*numbers)
    return try{
        numbers.map { (it * commonScale).toLong() }
            .map{ it.primeFactors() }
            .reduce { acc, list -> (acc intersect list).toList() }
            .reduce { acc, i -> acc * i } / commonScale.toDouble()
    } catch (e: UnsupportedOperationException){ 1 }
}

fun factorial(n: Number): Number= if(n <= 1) 1 else n * factorial(n-1)
// */