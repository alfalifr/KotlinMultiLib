package sidev.lib.collection

import sidev.lib.`val`.QueueMode

/**
 * Vector yang memiliki arah / [queueMode] == FIFO, yaitu yg masuk pertama akan keluar pertama.
 */
interface Queue<T>: Vector<T> {
    override val queueMode: QueueMode
        get() = QueueMode.FIFO

    override fun pop(): T
    override fun peek(): T
    override fun push(item: T): T
}

internal class QueueImpl<T>(initCapacity: Int): VectorImpl<T>(initCapacity), Queue<T> {
    constructor(): this(10)

    override fun pushIndex(cursorIndex: Int, currentSize: Int, addedIndex: Int, addedCount: Int): Int = 0
    override fun popIndex(cursorIndex: Int, currentSize: Int, removedIndex: Int, removedCount: Int): Int = 0
    override fun subList(fromIndex: Int, toIndex: Int): Queue<T> =
        QueueImpl<T>(elementCount +capacityIncrement /2).apply {
            addAll(this@QueueImpl.array as Array<out T>)
        }
}