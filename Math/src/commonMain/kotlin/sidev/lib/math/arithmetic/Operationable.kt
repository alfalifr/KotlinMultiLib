package sidev.lib.math.arithmetic

interface Operationable<R> {
    fun doOperation(n1: Number, n2: Number): R
    operator fun invoke(n1: Number, n2: Number): R = doOperation(n1, n2)
}