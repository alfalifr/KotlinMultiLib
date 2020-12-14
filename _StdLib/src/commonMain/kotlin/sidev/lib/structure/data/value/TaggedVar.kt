package sidev.lib.structure.data.value

import sidev.lib.structure.prop.TagProp

interface TaggedVar<T, V>: NullableVar<V>, TagProp<T>

internal open class TaggedVarImpl<T, V>(tag: T, override var value: V?): TaggedValImpl<T, V>(tag, value), TaggedVar<T, V> {
    override fun toString(): String = "TaggedVar(tag=$tag, value=$value)"
}