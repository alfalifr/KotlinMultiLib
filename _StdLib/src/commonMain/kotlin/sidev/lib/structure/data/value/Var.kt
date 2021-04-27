package sidev.lib.structure.data.value

import kotlin.jvm.JvmName

interface Var<T>: Val<T>, WriteVal<T> {
    override var value: T
    override fun setValue_(v: T) {
        value= v
    }
}

internal open class VarImpl<T>(override var value: T): ValImpl<T>(value), Var<T> {
    override fun toString(): String = "Var($value)"
}