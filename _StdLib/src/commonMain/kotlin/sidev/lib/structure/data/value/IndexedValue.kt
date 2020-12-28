package sidev.lib.structure.data.value

import sidev.lib.reflex.getHashCode

interface IndexedValue<out T>: Val<T> {
    val index: Int
    override val value: T
}

internal open class IndexedValueImpl<T>(override val index: Int, override val value: T): IndexedValue<T> {
    override fun equals(other: Any?): Boolean = when(other){
        is IndexedValue<*> -> index == other.index && value == other.value
        else -> super.equals(other)
    }
    override fun hashCode(): Int = getHashCode(index, value, calculateOrder = false)
    override fun toString(): String = "IndexedValue(index=$index, value=$value)"
}