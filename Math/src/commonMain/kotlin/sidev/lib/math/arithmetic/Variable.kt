package sidev.lib.math.arithmetic

import sidev.lib.number.compareTo
import sidev.lib.number.times

/**
 *
 */
interface Variable<T: Number>: Calculable {
    val name: String
    val coeficient: T
    override val nInput: Int
        get() = 1

    override fun calculate(vararg namedNums: Pair<String, Number>): Number = namedNums.first().second * coeficient
    fun calculate(n: Number): Number = n * coeficient
    operator fun invoke(n: Number): Number = calculate(n)
}

data class VariableImpl<T: Number>(override val name: String, override val coeficient: T): Variable<T> {
    override fun toString(): String = if(coeficient > 1) "$coeficient$name" else name
}