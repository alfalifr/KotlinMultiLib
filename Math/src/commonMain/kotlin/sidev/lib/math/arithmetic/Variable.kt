package sidev.lib.math.arithmetic

import sidev.lib.exception.IllegalStateExc
import sidev.lib.exception.NotYetSupportedExc
import sidev.lib.number.*

/**
 *
 */
interface Variable<T: Number>: SingleElement<T> {
    override val numberComponent: T
        get() = coeficient
    val name: String
    val coeficient: T
    override val nInput: Int
        get() = 1

    override fun calculate(vararg namedNums: Pair<String, Number>): Number = namedNums.first().second * coeficient
    fun calculate(n: Number): Number = n * coeficient
    operator fun invoke(n: Number): Number = calculate(n)


    override fun plus(element: Calculable): Calculable = when(element){
        is Constant<*> -> Solver.plus(this, element)
        is Variable<*> -> Solver.plus(this, element)
        else -> Solver.plus(this, element)
    }

    override fun minus(element: Calculable): Calculable = when(element){
        is Constant<*> -> Solver.minus(this, element)
        is Variable<*> -> Solver.minus(this, element)
        else -> Solver.minus(this, element)
    }

    override fun times(element: Calculable): Calculable = when(element){
        is Constant<*> -> Solver.times(this, element)
        is Variable<*> -> Solver.times(this, element)
        else -> Solver.times(this, element)
    }

    override fun div(element: Calculable): Calculable = when(element){
        is Constant<*> -> Solver.div(this, element)
        is Variable<*> -> Solver.div(this, element)
        else -> Solver.div(this, element)
    }

    override fun rem(element: Calculable): Calculable = when(element){
        is Constant<*> -> Solver.rem(this, element)
        is Variable<*> -> Solver.rem(this, element)
        else -> Solver.rem(this, element)
    }

/*
    override fun plus(element: Calculable): Calculable {
        if(element is Variable<*> && name == element.name){
            return (coeficient + element.coeficient).let {
                if(it != 0) variableOf(name, it) else constantOf(0)
            }
        }
        return blockOf(this).apply {
            addOperation(element, Operation.PLUS)
        }
    }

    override fun minus(element: Calculable): Calculable {
        if(element is Variable<*> && name == element.name){
            return (coeficient - element.coeficient).let {
                if(it != 0) variableOf(name, it) else constantOf(0)
            }
        }
        return blockOf(this).apply {
            addOperation(element, Operation.MINUS)
        }
    }

    override fun times(element: Calculable): Calculable {
        if(element is Constant<*>)
            return variableOf(name, coeficient * element.number)

        val otherNum= if(element is Variable<*>) element.coeficient else 1

        return blockOf(variableOf(name, coeficient * otherNum)).apply {
            addOperation(
                when(element){
                    is Variable<*> -> variableOf(element.name, 1)
                    is Block -> element
                    else -> throw IllegalStateExc(detMsg = "Format `Calculable` tidak diketahui.")
                }
                , Operation.TIMES
            )
        }
    }

    override fun div(element: Calculable): Calculable {
        if(element is Constant<*>)
            return variableOf(name, coeficient / element.number)
        else if(element is Variable<*> && name == element.name)
            return constantOf(coeficient / element.coeficient)

        val otherNum= if(element is Variable<*>) element.coeficient else 1

        return blockOf(variableOf(name, coeficient / otherNum)).apply {
            if(element !is Constant<*>){
                addOperation(
                    when(element){
                        is Variable<*> -> variableOf(element.name, 1)
                        is Block -> element
                        else -> throw IllegalStateExc(detMsg = "Format `Calculable` tidak diketahui.")
                    }
                    , Operation.DIVIDES
                )
            }
        }
    }

    override fun rem(element: Calculable): Calculable = NotYetSupportedExc.stillOnResearch
// */
}

data class VariableImpl<T: Number>(override val name: String, override val coeficient: T): Variable<T> {
    override fun toString(): String = when(coeficient){
        1 -> name
        -1 -> "-$name"
        else -> "$coeficient$name"
    }
    override fun hashCode(): Int = name.hashCode() + coeficient.toInt()
    override fun equals(other: Any?): Boolean = when(other){
        is Variable<*> -> name == other.name && coeficient == other.coeficient
        else -> super.equals(other)
    }
}