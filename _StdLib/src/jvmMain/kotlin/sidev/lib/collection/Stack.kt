package sidev.lib.collection

/*
import java.util.Stack as JvmStack

internal actual class StackImpl<T> actual constructor(initCapacity: Int): Stack<T>{
    constructor(): this(10)

    private val delegate: JvmStack<T> = JvmStack()

    override val size: Int
        get() = delegate.size

    override fun popIndex(cursorIndex: Int, currentSize: Int, removedIndex: Int, removedCount: Int): Int = currentSize -removedCount -1
    override fun pushIndex(cursorIndex: Int, currentSize: Int, addedIndex: Int, addedCount: Int): Int = currentSize +addedCount -1

    override fun trimToSize() = delegate.trimToSize()
    override fun trimNulls() {
        delegate.trimNulls()
    }

    override fun pop(): T = delegate.pop()
    override fun peek(): T = delegate.peek()
    override fun push(item: T): T = delegate.push(item)
    override fun contains(element: T): Boolean = delegate.contains(element)
    override fun containsAll(elements: Collection<T>): Boolean = delegate.containsAll(elements)
    override fun get(index: Int): T = delegate[index]
    override fun indexOf(element: T): Int = delegate.indexOf(element)
    override fun isEmpty(): Boolean = delegate.isEmpty()
    override fun iterator(): MutableIterator<T> = delegate.iterator()
    override fun lastIndexOf(element: T): Int = delegate.lastIndexOf(element)
    override fun add(element: T): Boolean = delegate.add(element)
    override fun add(index: Int, element: T) = delegate.add(index, element)
    override fun addAll(index: Int, elements: Collection<T>): Boolean = delegate.addAll(index, elements)
    override fun addAll(elements: Collection<T>): Boolean = delegate.addAll(elements)
    override fun clear() = delegate.clear()
    override fun listIterator(): MutableListIterator<T> = delegate.listIterator()
    override fun listIterator(index: Int): MutableListIterator<T> = delegate.listIterator(index)
    override fun remove(element: T): Boolean = delegate.remove(element)
    override fun removeAll(elements: Collection<T>): Boolean = delegate.removeAll(elements)
    override fun removeAt(index: Int): T = delegate.removeAt(index)
    override fun retainAll(elements: Collection<T>): Boolean = delegate.retainAll(elements)
    override fun set(index: Int, element: T): T = delegate.set(index, element)
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = delegate.subList(fromIndex, toIndex)
}
 */