package sidev.lib.structure.data.value

import sidev.lib.reflex.getHashCode
import sidev.lib.structure.prop.TagProp

interface TaggedVal<T, V>: NullableVal<V>, TagProp<T>

internal open class TaggedValImpl<T, V>(override val tag: T?, override val value: V?): TaggedVal<T, V> {
    override fun equals(other: Any?): Boolean = when(other){
        is TaggedVal<*, *> -> tag == other.tag && value == other.value
        else -> super.equals(other)
    }
    override fun hashCode(): Int = getHashCode(tag, value, calculateOrder = false)
    override fun toString(): String = "TaggedVal(tag=$tag, value=$value)"
}

/*
/*
    constructor(): this(true)
    constructor(): this(isNullable){
        this.tag= tag
        this.value= value
    }
 */
//    override var value: V?= null
    init {
        this.value= value
    }
    override var tag: T?= tag

    override fun equals(other: Any?): Boolean {
        return when(other){
            is TaggedVal<*, *> -> value == other.value && tag == other.tag
            is VarImpl<*> -> value == other.value
            else -> this === other
        }
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + (tag?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String
        = "${this::class.simpleName}(value=$value, tag=$tag)"
}

 */
