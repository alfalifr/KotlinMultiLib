package sidev.lib.math.arithmetic

fun <T: Number> variableOf(name: String, coeficient: T): Variable<T> = VariableImpl(name, coeficient)
fun <T: Number> constantOf(number: T): Constant<T> = ConstantImpl(number)

fun blockOf(firstElement: Calculable = NullCalculable, /*operationLevel: Int = 1,*/ parentBlock: Block? = null): Block =
    BlockImpl(firstElement, parentBlock)

fun Calculable.numberComponent(factorizeBlockFirst: Boolean = true): Number? = when(this){
    is Constant<*> -> number
    is Variable<*> -> coeficient
/*
    is Block -> {
        (if(factorizeBlockFirst) Solver.factorize(this).also {
            if(it !is Block) return it.numberComponent(false)
        } else this).run { this as Block
            elements.find { it is Constant<*> }?.numberComponent(false)
                ?: 1
        }
    }
 */
    else -> null
}


fun Char.isMathOperator(): Boolean = when(this) {
    '+' -> true
    '-' -> true
    '*' -> true
    '/' -> true
    '%' -> true
    '^' -> true
    '~' -> true
    else -> false
}

fun String.isMathEquitySign(): Boolean = when(this){
    "=" -> true
    ">" -> true
    "<" -> true
    ">=" -> true
    "<=" -> true
    else -> false
}

fun String.isMathSign() = first().isMathOperator() || isMathEquitySign() || when(this) {
    "(" -> true
    ")" -> true
    else -> false
}