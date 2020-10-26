package sidev.lib.collection

internal actual class StackImpl<T> actual constructor(initCapacity: Int): VectorImpl<T>(initCapacity), Stack<T>{
    constructor(): this(10)

    override fun pushIndex(currentIndex: Int, currentSize: Int, addedIndex: Int, addedCount: Int): Int = currentIndex +addedCount
    override fun popIndex(currentIndex: Int, currentSize: Int, removedIndex: Int, removedCount: Int): Int = currentIndex -removedCount
    override fun subList(fromIndex: Int, toIndex: Int): Stack<T> =
        StackImpl<T>(elementCount +capacityIncrement /2).apply {
            addAll(this@StackImpl.array as Array<out T>)
        }
}