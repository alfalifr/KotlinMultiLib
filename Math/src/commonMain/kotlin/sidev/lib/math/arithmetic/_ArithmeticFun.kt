package sidev.lib.math.arithmetic

import sidev.lib.check.isNull
import sidev.lib.check.notNull

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

val Calculable.varNames: List<String> get()= when(this){
    is Variable<*> -> listOf(name)
    is Block -> varNames
    else -> emptyList()
}

val Calculable.varNameCounts: Map<String, Int> get()= when(this){
    is Variable<*> -> mapOf(name to 1)
    is Block -> {
        val resCounts= mutableMapOf<String, Int>()
        elements.forEach {  e ->
            e.varNameCounts.forEach { entry ->
                resCounts[entry.key]
                    .notNull { resCounts[entry.key]= it + 1 }
                    .isNull { resCounts[entry.key]= 1 }
            }
        }
        resCounts
    }
    else -> emptyMap()
}

fun Calculable.hasVarName(varName: String): Boolean = when(this){
    is Variable<*> -> name == varName
    is Block -> hasVarName(varName)
    else -> false
}


fun simpleEquationOf(
    calc1: Calculable, calc2: Calculable,
    sign: Equation.Sign = Equation.Sign.EQUAL
): SimpleEquation =
    SimpleEquationImpl(calc1, calc2, sign)



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