package sidev.lib.math.number


data class IntFractionImpl(override val numerator: Int, override val denominator: Int): //Number(),
    IntFraction {
    override fun equals(other: Any?): Boolean = when(other){
        is Fraction<*> -> other.realNumber == realNumber
        else -> super.equals(other)
    }
    override fun hashCode(): Int = realNumber.hashCode()
    override fun toString(): String = "$numerator / $denominator"
}