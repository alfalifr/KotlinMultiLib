package sidev.lib.structure.data.value

import sidev.lib.structure.prop.TagProp

interface TaggedVar<out T, V>: Var<V>, TagProp<T>, TaggedVal<T, V>

internal open class TaggedVarImpl<T, V>(tag: T, override var value: V): TaggedValImpl<T, V>(tag, value), TaggedVar<T, V> {
    override fun toString(): String = "TaggedVar(tag=$tag, value=$value)"
}