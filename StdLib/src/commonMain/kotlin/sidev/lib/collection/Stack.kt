package sidev.lib.collection

import sidev.lib.`val`.QueueMode
import sidev.lib.`val`.SuppressLiteral

/**
 * Vector yang memiliki arah / [queueMode] == LIFO, yaitu yg masuk trahir akan keluar pertama.
 */
interface Stack<T>: Vector<T>{
    override val queueMode: QueueMode
        get() = QueueMode.LIFO

    override fun pop(): T
    override fun peek(): T
    override fun push(item: T): T
}


internal class StackImpl<T>(initCapacity: Int): VectorImpl<T>(initCapacity), Stack<T>{
    constructor(): this(10)

    override fun pushIndex(cursorIndex: Int, currentSize: Int, addedIndex: Int, addedCount: Int): Int = currentSize +addedCount -1
    override fun popIndex(cursorIndex: Int, currentSize: Int, removedIndex: Int, removedCount: Int): Int = currentSize -removedCount
    override fun subList(fromIndex: Int, toIndex: Int): Stack<T> =
        StackImpl<T>(elementCount +capacityIncrement /2).apply {
            @Suppress(SuppressLiteral.UNCHECKED_CAST)
            addAll(this@StackImpl.array as Array<out T>)
        }
}