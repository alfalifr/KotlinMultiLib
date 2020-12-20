package sidev.lib.collection

import kotlin.jvm.JvmOverloads

class ReadOnlyMap<K, V> @JvmOverloads constructor(origin: Map<K, V>, copyFirst: Boolean = true): Map<K, V> {
    @JvmOverloads
    constructor(vararg array: Pair<K, V>): this(array.toMap(), false)

    private val origin: Map<K, V> = if(copyFirst) origin.toMap() else origin

    override val entries: ReadOnlySet<Map.Entry<K, V>>
        get()= origin.entries.asReadOnly(false)
    override val keys: ReadOnlySet<K>
        get()= origin.keys.asReadOnly(false)
    override val size: Int
        get()= origin.size
    override val values: ReadOnlyCollection<V>
        get()= origin.values.asReadOnly(false)

    override fun containsKey(key: K): Boolean = origin.containsKey(key)
    override fun containsValue(value: V): Boolean = origin.containsValue(value)
    override fun get(key: K): V? = origin[key]
    override fun isEmpty(): Boolean = origin.isEmpty()
    override fun equals(other: Any?): Boolean = origin == other
    override fun hashCode(): Int = origin.hashCode()
    override fun toString(): String = origin.toString()
}