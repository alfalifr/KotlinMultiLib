package sidev.lib.collection

import kotlin.jvm.JvmOverloads


sealed class ReadOnlyCollection<E> @JvmOverloads constructor(origin: Collection<E>, copyFirst: Boolean = true): Collection<E> {
    @JvmOverloads
    constructor(array: Array<E>, copyFirst: Boolean = true): this(array.asList(), copyFirst)

    protected open val origin: Collection<E> = if(copyFirst) origin.copy() else origin
    override val size: Int
        get() = origin.size

    override fun contains(element: E): Boolean = origin.contains(element)
    override fun containsAll(elements: Collection<E>): Boolean = origin.containsAll(elements)
    override fun isEmpty(): Boolean = origin.isEmpty()
    override fun iterator(): Iterator<E> = origin.iterator()
    override fun equals(other: Any?): Boolean = origin == other
    override fun hashCode(): Int = origin.hashCode()
    override fun toString(): String = origin.toString()
}

class ReadOnlyList<E> @JvmOverloads constructor(origin: List<E>, copyFirst: Boolean = true)
    : ReadOnlyCollection<E>(origin, copyFirst), List<E> {
    //    constructor(origin: List<E>): this(origin, true)
    @JvmOverloads
    constructor(array: Array<E>, copyFirst: Boolean = true): this(array.asList(), copyFirst)

    override val origin: List<E> = if(copyFirst) origin.toList() else origin

    override fun get(index: Int): E = origin[index]
    override fun indexOf(element: E): Int = origin.indexOf(element)
    override fun lastIndexOf(element: E): Int = origin.lastIndexOf(element)
    override fun listIterator(): ListIterator<E> = origin.listIterator()
    override fun listIterator(index: Int): ListIterator<E> = origin.listIterator(index)
    override fun subList(fromIndex: Int, toIndex: Int): ReadOnlyList<E> = ReadOnlyList(origin.copy(fromIndex, toIndex), false)
}

class ReadOnlySet<E> @JvmOverloads constructor(origin: Set<E>, copyFirst: Boolean = true)
    : ReadOnlyCollection<E>(origin, copyFirst), Set<E> {
    @JvmOverloads
    constructor(array: Array<E>, copyFirst: Boolean = true): this(array.toSet(), copyFirst)
    override val origin: Set<E> = if(copyFirst) origin.toSet() else origin
}