package sidev.lib.structure.data.value

interface Val<T> {
    val value: T
}

internal open class ValImpl<T>(override val value: T): Val<T> {
    override fun equals(other: Any?): Boolean = when(other){
        is Val<*> -> value == other.value
        else -> super.equals(other)
    }
    override fun hashCode(): Int = value.hashCode()
    override fun toString(): String = "Val($value)"
}