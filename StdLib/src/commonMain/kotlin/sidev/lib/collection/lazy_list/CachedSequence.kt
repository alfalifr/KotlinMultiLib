package sidev.lib.collection.lazy_list

import sidev.lib.collection.iterator.emptyIterator
import sidev.lib.collection.iterator.withKeyIndexed
import sidev.lib.number.isNegative

/**
 * Mirip dg[ArrayList], [CachedSequence] memiliki sumber data yg berasal dari sequence sehingga
 * data yg disimpan tidak besar di awal dan sesuai kebutuhan.
 */
open class CachedSequence<T>(private val arrayList: ArrayList<T>): MutableList<T> by arrayList,
    MutableIndexedCachedLazyList<T>, MutableLazyList<Pair<Int, T>> { //by MutableLazyListImpl_Internal<Pair<Int, T>>() {
    constructor(): this(ArrayList())
    constructor(iterator: Iterator<T>): this(ArrayList()){
        builderIterator= iterator.withKeyIndexed { index, value -> index }
    }
    constructor(iterable: Iterable<T>): this(ArrayList()){
        builderIterator= iterable.iterator().withKeyIndexed { index, value -> index }
    }
    constructor(inSequence: Sequence<T>): this(ArrayList()){
        builderIterator= inSequence.iterator().withKeyIndexed { index, value -> index }
    }

    /**
     * Digunakan untuk menyocokan apakah hasil dari [findNext] sesuai dg index yg direquest.
     * Variabel ini digunakan pada [isNextMatched].
     */
    private var requestedGetIndex: Int= -1

    private val delegate = MutableLazyListImpl_Internal<Pair<Int, T>>()
    final override val iteratorList: List<Iterator<Pair<Int, T>>>
        get() = delegate.iteratorList
    final override var builderIterator: Iterator<Pair<Int, T>>
        private set(v){ delegate.builderIterator= v }
        get()= delegate.builderIterator
    final override fun addIterator(itr: Iterator<Pair<Int, T>>): Boolean = delegate.addIterator(itr)
    final override fun iteratorHasNext(): Boolean = delegate.iteratorHasNext()

    override fun getExisting(key: Int): T? = if(key in indices) arrayList[key] else null
    override fun getExistingKey(value: T): Int?{
        val index= arrayList.indexOf(value)
        return if(index >= 0) index else null
    }
    override fun containsExistingValue(value: T): Boolean = arrayList.indexOf(value) >= 0
    override fun containsExistingKey(key: Int): Boolean = key in indices
    override fun isNextMatched(key: Int, addedNext: T): Boolean = key == requestedGetIndex //key in indices

    override fun addNext(key: Int, value: T): Boolean = add(value)
    override fun addValues(itr: Iterator<T>): Boolean = addIterator(itr.withKeyIndexed{ index, _ -> index })
//    override fun addValues(seq: Sequence<T>): Boolean = addValues(seq.iterator())
    //    override fun addLazily(sequence: Sequence<T>): Boolean = addValueIterator(sequence.iterator())

    //    /** @return -1 karena `key` dalam konteks ArrayList tidak penting. */
//    override fun extractKeyFrom(addedNext: T): Int = -1
    override fun get(index: Int): T {
        if(index.isNegative())
            throw IndexOutOfBoundsException("get() -> index tidak boleh negatif: \"$index\"")
        requestedGetIndex= index
        val el= findNext(index)
        requestedGetIndex= -1
        return el
            ?: throw IndexOutOfBoundsException("CachedSequence: ${this::class.simpleName} hanya memiliki element sebanyak $size tapi index= $index")
/*
        while(builderIterator.hasNext() && index >= size){
            add(builderIterator.next())
        }
        if(index >= size)
            throw ArrayIndexOutOfBoundsException("CachedSequence: ${this::class.simpleName} hanya memiliki element sebanyak $size tapi index= $index")
        return super.get(index)
 */
    }

    override fun contains(element: T): Boolean = containsNextValue(element)

    override fun indexOf(element: T): Int = findNextKey(element) ?: -1
    override fun lastIndexOf(element: T): Int {
        val index= arrayList.lastIndexOf(element)
        return if(index >= 0) index
        else findNextKey(element) ?: -1
    }

    override fun iterator(): MutableIterator<T>
        = object : MutableIterator<T>{
        var index= 0
        val initialIndices= 0 until size

        override fun hasNext(): Boolean = index in initialIndices || iteratorHasNext()
        override fun next(): T = get(index++) //if(index in initialIndices) get(index++) else getNext()!!.second
        override fun remove(){ removeAt(--index) }
    }

    override fun isEmpty(): Boolean = arrayList.isEmpty() && !iteratorHasNext()

    override fun toString(): String {
        val containedStr= arrayList.toString()
        return if(iteratorHasNext()){
            var cachedStr= "...(lazyVal)]"
            if(arrayList.isNotEmpty())
                cachedStr= ", $cachedStr"
            containedStr.substring(0, containedStr.length-1) +cachedStr
        } else containedStr
    }
}