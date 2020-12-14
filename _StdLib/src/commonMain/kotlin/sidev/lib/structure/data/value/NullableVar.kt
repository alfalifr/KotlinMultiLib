package sidev.lib.structure.data.value

interface NullableVar<T>: NullableVal<T>, Var<T?>

internal open class NullableVarImpl<T>(override var value: T?)
    : NullableValImpl<T>(value), NullableVar<T> {
    override fun toString(): String = "Var?($value)"
}