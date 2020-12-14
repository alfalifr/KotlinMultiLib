package sidev.lib.collection
/*
import sidev.lib.console.prine

internal actual class StackImpl<T> actual constructor(initCapacity: Int): VectorImpl<T>(initCapacity), Stack<T>{
    constructor(): this(10)

    override fun pushIndex(cursorIndex: Int, currentSize: Int, addedIndex: Int, addedCount: Int): Int = currentSize +addedCount -1
//            .also { prine("curr= $cursorIndex, size= $currentSize, added= $addedIndex, count= $addedCount, it= $it sizeReal= $elementCount") }
    override fun popIndex(cursorIndex: Int, currentSize: Int, removedIndex: Int, removedCount: Int): Int = currentSize -removedCount
    override fun subList(fromIndex: Int, toIndex: Int): Stack<T> =
        StackImpl<T>(elementCount +capacityIncrement /2).apply {
            addAll(this@StackImpl.array as Array<out T>)
        }
}

 */