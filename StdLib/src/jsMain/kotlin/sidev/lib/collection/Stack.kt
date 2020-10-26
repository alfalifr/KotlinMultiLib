package sidev.lib.collection

import sidev.lib.console.prine

internal actual class StackImpl<T> actual constructor(initCapacity: Int): VectorImpl<T>(initCapacity), Stack<T>{
    constructor(): this(10)

    override fun pushIndex(currentIndex: Int, currentSize: Int, addedIndex: Int, addedCount: Int): Int =
        (currentSize +addedCount -1).also {
            prine("curr= $currentIndex, size= $currentSize, added= $addedIndex, count= $addedCount, it= $it sizeReal= $elementCount")
        }
    override fun popIndex(currentIndex: Int, currentSize: Int, removedIndex: Int, removedCount: Int): Int = currentSize -removedCount -1
    override fun subList(fromIndex: Int, toIndex: Int): Stack<T> =
        StackImpl<T>(elementCount +capacityIncrement /2).apply {
            addAll(this@StackImpl.array as Array<out T>)
        }
}