package sidev.lib.structure.data.value

import sidev.lib.structure.prop.TagProp

interface NullableTaggedVar<T, V>: NullableVar<V>, TagProp<T>

internal open class NullableTaggedVarImpl<T, V>(tag: T, override var value: V?)
    : TaggedValImpl<T, V?>(tag, value), NullableTaggedVar<T, V> {
    override fun toString(): String = "TaggedVar?(tag=$tag, value=$value)"
}