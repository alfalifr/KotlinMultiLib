package sidev.lib.collection

import sidev.lib.`val`.QueueMode

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

internal expect class StackImpl<T> constructor(initCapacity: Int): Stack<T>