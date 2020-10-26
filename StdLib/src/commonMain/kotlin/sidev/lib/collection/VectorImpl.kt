package sidev.lib.collection

import sidev.lib.collection.array.arrayCopy
import sidev.lib.collection.array.trimNulls
import sidev.lib.collection.array.trimToSize

abstract class VectorImpl<T>(initCapacity: Int): Vector<T> {
    constructor(): this(10)

    var capacityIncrement= 10
    protected var array: Array<Any?> = arrayOfNulls(initCapacity)

    /**
     * Jumlah elemen yg sesungguhnya yg merupakan hasil penambahan dari [add] yg ada di [array].
     * Property ini sama dg [elementCount].
     */
    final override var size: Int
        get()= elementCount
        private set(v){
            elementCount= v
        }

    /**
     * Index ujung yg digunakan oleh fungsi [peek], [pop], dan [push].
     */
    var nextInd: Int= -1
        private set
    /**
     * Jumlah elemen yg sesungguhnya yg merupakan hasil penambahan dari [add] yg ada di [array].
     */
    var elementCount: Int= 0
        private set


    override fun remove(element: T): Boolean {
        for(i in 0 until elementCount)
            if(array[i] == element){
                removeAt(i)
                return true
            }
        return false
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        var bool= false
        for(removedE in elements){
            bool= bool || remove(removedE)
        }
        return bool
    }

    override fun clear() {
        for(i in 0 until elementCount)
            array[i]= null
        elementCount= 0
        nextInd= 0
    }

    override fun removeAt(index: Int): T {
        val item= array[index]
        val len= size - index - 1

        if(len > 0){
            arrayCopy(array, index +1, array, index, len)
        }

        nextInd= popIndex(nextInd, elementCount, index)
        array[--elementCount]= null
        return item as T
    }

    override fun add(index: Int, element: T) {
        if(index > elementCount)
            throw IndexOutOfBoundsException("size= $elementCount < index= $index")

        if(elementCount == array.size)
            grow()

        if(index < elementCount)
            arrayCopy(array, index, array, index +1, elementCount -index)

        nextInd= pushIndex(nextInd, elementCount, index)
        array[index]= element
        elementCount++
    }

    override fun add(element: T): Boolean {
        if(elementCount == array.size)
            grow()

        val currInd= nextInd
        nextInd= pushIndex(currInd, elementCount, currInd)
        array[currInd]= element
        elementCount++
        return true
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        if(index > elementCount)
            throw IndexOutOfBoundsException("size= $elementCount < index= $index")

        val totalLen= elementCount +elements.size
        if(totalLen > array.size)
            grow(totalLen)

        val addedCount= elements.size
        nextInd= pushIndex(nextInd, elementCount, index, addedCount)

        val len= elementCount -index
        arrayCopy(array, index, array, len, len)
        elementCount += addedCount
        return true
    }
    override fun addAll(elements: Collection<T>): Boolean = addAll(elementCount, elements)


    override fun trimNulls() { array.trimNulls(end = elementCount) }
    override fun trimToSize() {
        if(array.size > elementCount)
            array= array.trimToSize(elementCount)
    }

    override fun contains(element: T): Boolean = array.contains(element)
    override fun containsAll(elements: Collection<T>): Boolean = (array as Array<T>).all { contains(it) }
    override fun get(index: Int): T = array[index] as T
    override fun set(index: Int, element: T): T {
        val prev= array[index]
        array[index]= element
        return prev as T
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        val retainedSet= mutableSetOf<T>()

        for(retained in elements)
            if(retained in this)
                retainedSet += retained

        array= arrayOfNulls(retainedSet.size)

        for((i, retained) in retainedSet.withIndex())
            array[i]= retained

        nextInd= pushIndex(0, 0, retainedSet.size)
        elementCount= retainedSet.size
        return elementCount != 0
    }

    override fun indexOf(element: T): Int = array.indexOf(element)
    override fun lastIndexOf(element: T): Int = array.lastIndexOf(element)

    override fun isEmpty(): Boolean = elementCount == 0

    override fun peek(): T = array[nextInd] as T
    override fun pop(): T = removeAt(nextInd)
    override fun push(item: T): T{
        nextInd= pushIndex(nextInd, size, nextInd)
        add(nextInd, item)
        return item
    }

    override fun iterator(): MutableIterator<T> = object : MutableIterator<T>{
        var i= 0
        var poppedCount= 0
        override fun hasNext(): Boolean = poppedCount < elementCount
        override fun next(): T {
            poppedCount++
            return array[(popIndex(i, elementCount -poppedCount, i) +2).also { i= it }] as T
        }
        override fun remove() {
            removeAt(i)
        }
    }
    override fun listIterator(): MutableListIterator<T> = listIterator(0)
    override fun listIterator(index: Int): MutableListIterator<T> = object : MutableListIterator<T>{
        var i= index
        var popInd= index
        var pushInd= index
        var poppedCount= 0
        override fun hasNext(): Boolean = poppedCount < elementCount
        override fun hasPrevious(): Boolean = poppedCount > 0

        override fun nextIndex(): Int = popIndex(i, elementCount -poppedCount, i).also { popInd= it }
        override fun previousIndex(): Int = pushIndex(i, elementCount -poppedCount, i).also { pushInd= it }

        override fun next(): T {
            poppedCount++
            i= popInd
            return array[popInd] as T
        }
        override fun previous(): T {
            poppedCount--
            i= pushInd
            return array[pushInd] as T
        }
        override fun add(element: T) {
            this@VectorImpl.add(i++, element)
        }
        override fun remove() {
            removeAt(--i)
        }
        override fun set(element: T) {
            this@VectorImpl[i]= element
        }
    }

    private fun grow(minCapacity: Int= 1){
        val increment=
            if(capacityIncrement > minCapacity) capacityIncrement
            else minCapacity + capacityIncrement / 2 //ditambah 50% dari `capacityIncrement` agar proses add selanjutnya gak grow() lagi.

        val newLen= array.size +increment
        val newArray= arrayOfNulls<Any>(newLen) //as Array<T?>

        arrayCopy(array, 0, newArray, 0, newLen)
        array= newArray
    }
}