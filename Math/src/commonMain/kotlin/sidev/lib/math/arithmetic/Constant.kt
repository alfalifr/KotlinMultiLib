package sidev.lib.math.arithmetic

interface Constant<T: Number>: Calculable {
    val number: T
    override val nInput: Int
        get() = 0

    override fun calculate(vararg namedNums: Pair<String, Number>): Number = number
    fun calculate(): Number = number
    operator fun invoke()= number
}

data class ConstantImpl<T: Number>(override val number: T): Constant<T> {
    override fun toString(): String = number.toString()
}