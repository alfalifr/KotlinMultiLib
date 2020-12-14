package sidev.lib.collection.iterator

import sidev.lib.`val`.SuppressLiteral

abstract class TransitiveNestedIteratorSimpleImpl<I1, I2>(startInputIterator: Iterator<I2>?)
    : NestedIteratorSimpleImpl<I2>(startInputIterator), TransitiveNestedIteratorSimple<I1, I2> {
    constructor(startIterable: Iterable<I2>): this(startIterable.iterator())
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    constructor(start: I2?): this((start as? Iterable<I2>)?.iterator()){
        this.start= start
    }

    override fun getInputIterator(nowOutput: I2): Iterator<I2>? = super<TransitiveNestedIteratorSimple>.getInputIterator(nowOutput)
}