package sidev.lib.math.number

import sidev.lib.math.fpb
import sidev.lib.math.kpk

/**
 * [Fraction] dg [numerator] dan [denominator] bertipe [Int].
 */
interface WholeFraction: Fraction<WholeNumber>{
    override fun makeCommonFractions(other: Fraction<WholeNumber>): Pair<Fraction<WholeNumber>, Fraction<WholeNumber>> {
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
            WholeFractionImpl(thisNumerator, otherDenonimator),
            WholeFractionImpl(otherNumerator, otherDenonimator)
        )
    }

    override fun simply(): WholeFraction = numerator.fpb(denominator).let {
        WholeFractionImpl(numerator / it, denominator / it)
    }

    override fun plus(other: Fraction<WholeNumber>): WholeFraction = makeCommonFractions(other)
        .let { (thisFrac, other) ->
            WholeFractionImpl(thisFrac.numerator + other.numerator, other.denominator)
        }

    override fun minus(other: Fraction<WholeNumber>): WholeFraction = makeCommonFractions(other)
        .let { (thisFrac, other) ->
            WholeFractionImpl(thisFrac.numerator + other.numerator, other.denominator)
        }

    override fun times(other: Fraction<WholeNumber>): WholeFraction = WholeFractionImpl(
        numerator * other.numerator,
        denominator * other.denominator
    )

    override fun div(other: Fraction<WholeNumber>): WholeFraction = WholeFractionImpl(
        numerator * other.denominator,
        denominator * other.numerator
    )



    override operator fun plus(other: Byte): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator + denominator * other),
        denominator
    )
    override operator fun plus(other: Short): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator + denominator * other),
        denominator
    )
    override operator fun plus(other: Int): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator + denominator * other),
        denominator
    )
    override operator fun plus(other: Long): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator + denominator * other),
        denominator
    )
    override operator fun plus(other: Float): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator + denominator * other),
        denominator
    )
    override operator fun plus(other: Double): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator + denominator * other),
        denominator
    )


    override operator fun minus(other: Byte): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator - denominator * other),
        denominator
    )
    override operator fun minus(other: Short): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator - denominator * other),
        denominator
    )
    override operator fun minus(other: Int): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator - denominator * other),
        denominator
    )
    override operator fun minus(other: Long): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator - denominator * other),
        denominator
    )
    override operator fun minus(other: Float): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator - denominator * other),
        denominator
    )
    override operator fun minus(other: Double): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator - denominator * other),
        denominator
    )


    override operator fun times(other: Byte): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator * other),
        denominator
    )
    override operator fun times(other: Short): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator * other),
        denominator
    )
    override operator fun times(other: Int): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator * other),
        denominator
    )
    override operator fun times(other: Long): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator * other),
        denominator
    )
    override operator fun times(other: Float): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator * other),
        denominator
    )
    override operator fun times(other: Double): WholeFraction = WholeFractionImpl(
        wholeNumber(numerator * other),
        denominator
    )


    override operator fun div(other: Byte): WholeFraction = WholeFractionImpl(
        numerator,
        wholeNumber(denominator * other)
    )
    override operator fun div(other: Short): WholeFraction = WholeFractionImpl(
        numerator,
        wholeNumber(denominator * other)
    )
    override operator fun div(other: Int): WholeFraction = WholeFractionImpl(
        numerator,
        wholeNumber(denominator * other)
    )
    override operator fun div(other: Long): WholeFraction = WholeFractionImpl(
        numerator,
        wholeNumber(denominator * other)
    )
    override operator fun div(other: Float): WholeFraction = WholeFractionImpl(
        numerator,
        wholeNumber(denominator * other)
    )
    override operator fun div(other: Double): WholeFraction = WholeFractionImpl(
        numerator,
        wholeNumber(denominator * other)
    )
}