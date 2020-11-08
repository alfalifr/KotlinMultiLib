package sidev.lib.collection.iterator

import sidev.lib.`val`.SuppressLiteral

/*
abstract class LeveledNestedIterator2SimpleImpl<T>(startInputIterator: Iterator<T>?)
    : LeveledNestedIterator2Impl<T, T>(startInputIterator), LeveledNestedIterator2Simple<T> {
    constructor(startInputIterable: Iterable<T>): this(startInputIterable.iterator())
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    constructor(start: T?): this((start as? Iterable<T>)?.iterator()){
        this.start= start
    }

    final override val activeInputLines: ArrayList<Iterator<T>>
        get() = super.activeOutputLines
    final override var activeInputIterator: Iterator<T>?
        get() = super.activeOutputIterator
        set(value) { super.activeOutputIterator= value }

    //Yg di-override adalah output karena getInputIterator menambah nilai level.
    final override fun getInputIterator(nowOutput: T): Iterator<T>? = getOutputIterator(nowOutput)
    final override fun addInputIterator(inItr: Iterator<T>) = addOutputIterator(inItr).also {
        currentLevel++
    }
    final override fun changeLastActiveInputIterator(currentActiveInputIterator: Iterator<T>) = changeLastActiveOutputIterator(currentActiveInputIterator).also {
        currentLevel--
    }

    override fun changeLastActiveOutputIterator(currentActiveOutputIterator: Iterator<T>) {
        super.changeLastActiveOutputIterator(currentActiveOutputIterator)
        currentLevel--
    }
}
 */