package sidev.lib.math.arithmetic

import sidev.lib.number.powCast
import sidev.lib.number.rootCast


infix fun Calculable.pow(element: Calculable): Calculable {
    if(this is Constant<*> && element is Constant<*>)
        return constantOf(number powCast element.number)

    return blockOf(this).apply {
        addOperation(element, Operation.POWER)
    }
}

infix fun Calculable.root(element: Calculable): Calculable {
    if(this is Constant<*> && element is Constant<*>)
        return constantOf(number rootCast element.number)

    return blockOf(this).apply {
        addOperation(element, Operation.ROOT)
    }
}