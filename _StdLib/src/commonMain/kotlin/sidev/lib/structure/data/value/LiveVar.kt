package sidev.lib.structure.data.value

import sidev.lib.`val`.Assignment

interface LiveVar<T>: LiveVal<T>, Var<T> {
    /** Cara cepat meng-assign [value]. */
    operator fun invoke(v: T)
}

internal open class LiveVarImpl<T>(v: T): LiveValImpl<T>(v), LiveVar<T> {
    override var value: T = v
        set (v){
            field= v
            observer?.invoke(v, Assignment.ASSIGN)
        }

    override fun invoke(v: T) {
        value= v
    }
}