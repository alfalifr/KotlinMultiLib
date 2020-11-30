package sidev.lib.math.arithmetic

import sidev.lib.exception.NotYetSupportedExc
import sidev.lib.number.div
import sidev.lib.number.minus
import sidev.lib.number.plus
import sidev.lib.number.times

interface Constant<T: Number>: SingleElement<T> {
    override val numberComponent: T
        get() = number
    val number: T
    override val nInput: Int
        get() = 0

    override fun calculate(vararg namedNums: Pair<String, Number>): Number = number
    fun calculate(): Number = number
    operator fun invoke()= number


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
        if(element is Constant<*>)
            return constantOf(number + element.number)
        return blockOf(this).apply {
            addOperation(element, Operation.PLUS)
        }
    }

    override fun minus(element: Calculable): Calculable {
        if(element is Constant<*>)
            return constantOf(number - element.number)
        return blockOf(this).apply {
            addOperation(element, Operation.MINUS)
        }
    }

    override fun times(element: Calculable): Calculable {
        if(element is Constant<*>)
            return constantOf(number * element.number)
        else if(element is Variable<*>)
            return variableOf(element.name, number * element.coeficient)
        return blockOf(this).apply {
            addOperation(element, Operation.TIMES)
        }
    }

    override fun div(element: Calculable): Calculable {
        if(element is Constant<*>)
            return constantOf(number / element.number)
        else if(element is Variable<*>)
            return variableOf(element.name, number / element.coeficient)
        return blockOf(this).apply {
            addOperation(element, Operation.DIVIDES)
        }
    }

    override fun rem(element: Calculable): Calculable = NotYetSupportedExc.stillOnResearch
// */
}

data class ConstantImpl<T: Number>(override val number: T): Constant<T> {
    override fun toString(): String = number.toString()
    override fun hashCode(): Int = number.toInt()
    override fun equals(other: Any?): Boolean = when(other){
        is Constant<*> -> number == other.number
        else -> super.equals(other)
    }
}