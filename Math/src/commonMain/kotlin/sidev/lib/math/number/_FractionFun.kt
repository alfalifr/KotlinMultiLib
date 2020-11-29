package sidev.lib.math.number

import sidev.lib.math.kpk
import sidev.lib.number.getDigitBehindDecimal
import sidev.lib.number.powCast

fun fractionOf(numerator: Int, denominator: Int): IntFraction = IntFractionImpl(numerator, denominator)
fun fractionOf(numerator: WholeNumber, denominator: WholeNumber): WholeFraction = WholeFractionImpl(numerator, denominator)
fun fractionOf(numerator: WholeNumber, denominator: Number): WholeFraction = WholeFractionImpl(numerator, wholeNumber(denominator))

infix fun Int.per(denominator: Int): IntFraction = fractionOf(this, denominator)
infix fun WholeNumber.per(denominator: WholeNumber): WholeFraction = fractionOf(this, denominator)
infix fun WholeNumber.per(denominator: Number): WholeFraction = fractionOf(this, wholeNumber(denominator))


fun commonFractionsOf(vararg fractions: IntFraction): List<IntFraction> {
    val kpk= kpk(*IntArray(fractions.size){ fractions[it].denominator })
    return fractions.map { IntFractionImpl(it.numerator * (kpk / it.denominator), kpk) }
}
fun commonFractionsOf(vararg fractions: WholeFraction): List<WholeFraction> {
    val kpk= kpk(*Array(fractions.size){ fractions[it].denominator })
    return fractions.map { WholeFractionImpl(it.numerator * (kpk / it.denominator), kpk) }
}

fun WholeFraction.toIntFraction(): IntFraction = fractionOf(numerator.toInt(), denominator.toInt())
fun IntFraction.toWholeFraction(): WholeFraction = fractionOf(wholeNumber(numerator), wholeNumber(denominator))

fun Number.toFraction(): WholeFraction = when(this){
    is FloatingNumber -> toFraction()
    is Float -> toFraction()
    is Double -> toFraction()
    is BroaderNumber -> fractionOf(wholeNumber(primitiveValue), 1)
    else -> fractionOf(wholeNumber(this), 1)
}

fun FloatingNumber.toFraction(): WholeFraction = getDigitBehindDecimal().let {
    WholeFractionImpl(wholeNumber(this * it), wholeNumber(10 powCast it)).simply()
}

fun Float.toFraction(): WholeFraction = getDigitBehindDecimal().let {
    WholeFractionImpl(wholeNumber(this * (10 powCast it)), wholeNumber(10 powCast it)).simply()
}
fun Double.toFraction(): WholeFraction = getDigitBehindDecimal().let {
    WholeFractionImpl(wholeNumber(this * (10 powCast it)), wholeNumber(10 powCast it)).simply()
}
//fun fractionOf(numerator: Long, denominator: Long): IntFraction = IntFractionImpl(numerator, denominator)