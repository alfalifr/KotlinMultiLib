package sidev.lib.structure.data

import sidev.lib.structure.prop.SizeProp


interface FiniteIndexable<out T>: Indexable<T>, SizeProp

internal class ArrayIndexable<T>(val array: Array<T>): FiniteIndexable<T> {
    constructor(size: Int, init: (Int) -> T): this(Array<Any?>(size, init) as Array<T>)

    override val size: Int
        get() = array.size
    override fun get(index: Int): T = array[index]

    override fun equals(other: Any?): Boolean = array.equals(other)
    override fun hashCode(): Int = array.hashCode()
    override fun toString(): String = array.joinToString(prefix = "Array[", postfix = "]")
}

internal class ListIndexable<T>(val list: List<T>): FiniteIndexable<T> {
    constructor(origin: Array<T>): this(origin.asList())
    constructor(initSize: Int): this(ArrayList(initSize))
    constructor(): this(mutableListOf())

    override val size: Int
        get() = list.size
    override fun get(index: Int): T = list[index]

    override fun equals(other: Any?): Boolean = list.equals(other)
    override fun hashCode(): Int = list.hashCode()
    override fun toString(): String = list.toString()
}

