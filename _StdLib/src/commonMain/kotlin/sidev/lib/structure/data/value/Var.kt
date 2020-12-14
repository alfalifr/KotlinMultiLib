package sidev.lib.structure.data.value

interface Var<T>: Val<T> {
    override var value: T
}

internal open class VarImpl<T>(override var value: T): ValImpl<T>(value), Var<T> {
    override fun toString(): String = "Var($value)"
}