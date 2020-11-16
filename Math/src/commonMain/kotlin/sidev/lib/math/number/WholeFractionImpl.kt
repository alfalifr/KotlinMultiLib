package sidev.lib.math.number

data class WholeFractionImpl(override val numerator: WholeNumber, override val denominator: WholeNumber): WholeFraction {
    override fun equals(other: Any?): Boolean = when(other){
        is Fraction<*> -> other.realNumber == realNumber
        else -> super.equals(other)
    }
    override fun hashCode(): Int = realNumber.hashCode()
    override fun toString(): String = "$numerator / $denominator"
}