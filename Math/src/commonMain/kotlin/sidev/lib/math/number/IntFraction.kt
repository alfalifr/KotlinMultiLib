package sidev.lib.math.number

import sidev.lib.math.fpb
import sidev.lib.math.kpk

/**
 * [Fraction] dg [numerator] dan [denominator] bertipe [Int].
 */
interface IntFraction: Fraction<Int>{
    override fun makeCommonFractions(other: Fraction<Int>): Pair<Fraction<Int>, Fraction<Int>> {
        var thisNumerator= numerator
        var otherNumerator= other.numerator
        val otherDenonimator= if(other.denominator == denominator) denominator
        else {
            denominator.kpk(other.denominator).also {
                otherNumerator *= it / other.denominator
                thisNumerator *= it / denominator
            }
        }
        return Pair(
            IntFractionImpl(thisNumerator, otherDenonimator),
            IntFractionImpl(otherNumerator, otherDenonimator)
        )
    }

    override fun simply(): IntFraction = numerator.fpb(denominator).let {
        IntFractionImpl(numerator / it, denominator / it)
    }

    override fun plus(other: Fraction<Int>): Fraction<Int> = makeCommonFractions(other)
        .let { (thisFrac, other) ->
            IntFractionImpl(thisFrac.numerator + other.numerator, other.denominator)
        }

    override fun minus(other: Fraction<Int>): Fraction<Int> = makeCommonFractions(other)
        .let { (thisFrac, other) ->
            IntFractionImpl(thisFrac.numerator + other.numerator, other.denominator)
        }

    override fun times(other: Fraction<Int>): Fraction<Int> = IntFractionImpl(
        numerator * other.numerator,
        denominator * other.denominator
    )

    override fun div(other: Fraction<Int>): Fraction<Int> = IntFractionImpl(
        numerator * other.denominator,
        denominator * other.numerator
    )


    override operator fun plus(other: Byte): IntFraction = IntFractionImpl(
        numerator + denominator * other,
        denominator
    )
    override operator fun plus(other: Short): IntFraction = IntFractionImpl(
        numerator + denominator * other,
        denominator
    )
    override operator fun plus(other: Int): IntFraction = IntFractionImpl(
        numerator + denominator * other,
        denominator
    )
    override operator fun plus(other: Long): IntFraction = IntFractionImpl(
        (numerator + denominator * other).toInt(),
        denominator
    )
    override operator fun plus(other: Float): IntFraction = IntFractionImpl(
        (numerator + denominator * other).toInt(),
        denominator
    )
    override operator fun plus(other: Double): IntFraction = IntFractionImpl(
        (numerator + denominator * other).toInt(),
        denominator
    )


    override operator fun minus(other: Byte): IntFraction = IntFractionImpl(
        numerator - denominator * other,
        denominator
    )
    override operator fun minus(other: Short): IntFraction = IntFractionImpl(
        numerator - denominator * other,
        denominator
    )
    override operator fun minus(other: Int): IntFraction = IntFractionImpl(
        numerator - denominator * other,
        denominator
    )
    override operator fun minus(other: Long): IntFraction = IntFractionImpl(
        (numerator - denominator * other).toInt(),
        denominator
    )
    override operator fun minus(other: Float): IntFraction = IntFractionImpl(
        (numerator - denominator * other).toInt(),
        denominator
    )
    override operator fun minus(other: Double): IntFraction = IntFractionImpl(
        (numerator - denominator * other).toInt(),
        denominator
    )


    override operator fun times(other: Byte): IntFraction = IntFractionImpl(
        numerator * other,
        denominator
    )
    override operator fun times(other: Short): IntFraction = IntFractionImpl(
        numerator * other,
        denominator
    )
    override operator fun times(other: Int): IntFraction = IntFractionImpl(
        numerator * other,
        denominator
    )
    override operator fun times(other: Long): IntFraction = IntFractionImpl(
        (numerator * other).toInt(),
        denominator
    )
    override operator fun times(other: Float): IntFraction = IntFractionImpl(
        (numerator * other).toInt(),
        denominator
    )
    override operator fun times(other: Double): IntFraction = IntFractionImpl(
        (numerator * other).toInt(),
        denominator
    )


    override operator fun div(other: Byte): IntFraction = IntFractionImpl(
        numerator,
        denominator * other
    )
    override operator fun div(other: Short): IntFraction = IntFractionImpl(
        numerator,
        denominator * other
    )
    override operator fun div(other: Int): IntFraction = IntFractionImpl(
        numerator,
        denominator * other
    )
    override operator fun div(other: Long): IntFraction = IntFractionImpl(
        numerator,
        (denominator * other).toInt()
    )
    override operator fun div(other: Float): IntFraction = IntFractionImpl(
        numerator,
        (denominator * other).toInt()
    )
    override operator fun div(other: Double): IntFraction = IntFractionImpl(
        numerator,
        (denominator * other).toInt()
    )
}