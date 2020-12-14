package sidev.lib.collection.iterator

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.structure.data.value.LeveledValue
import sidev.lib.structure.data.value.asLeveled

abstract class LeveledNestedIteratorSimpleImpl<T>(/*private val */startIterator: Iterator<T>?)
    : LeveledNestedIteratorImpl<T, T>(startIterator), LeveledNestedIteratorSimple<T> {
    constructor(startInputIterable: Iterable<T>): this(startInputIterable.iterator())
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    constructor(start: T?): this((start as? Iterable<T>)?.iterator()){
        this.start= start?.asLeveled()
    }

    final override val activeInputLines: ArrayList<Iterator<LeveledValue<T>>>
        get() = super.activeOutputLines
    final override var activeInputIterator: Iterator<LeveledValue<T>>?
        get() = super.activeOutputIterator
        set(value) { super.activeOutputIterator= value }


    final override fun getInputValueIterator(nowOutput: T): Iterator<T>? = getOutputValueIterator(nowOutput)
//    final override fun getOutputIterator(nowInput: LeveledValue<T>): Iterator<LeveledValue<T>>? = getInputIterator(nowInput) //Yg di-override adalah output karena getInputIterator menambah nilai level.
    final override fun addInputIterator(inItr: Iterator<LeveledValue<T>>) = addOutputIterator(inItr)
    final override fun changeLastActiveInputIterator(currentActiveInputIterator: Iterator<LeveledValue<T>>)
            = changeLastActiveOutputIterator(currentActiveInputIterator)

}