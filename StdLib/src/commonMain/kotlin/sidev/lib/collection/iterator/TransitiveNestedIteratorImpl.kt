package sidev.lib.collection.iterator

import sidev.lib.`val`.SuppressLiteral

abstract class TransitiveNestedIteratorImpl<I1, I2, O1, O2>(startInputIterator: Iterator<I2>?)
    : NestedIteratorImpl<I2, O2>(startInputIterator), TransitiveNestedIterator<I1, I2, O1, O2> {
    constructor(startIterable: Iterable<I2>): this(startIterable.iterator())
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    constructor(start: I2?): this((start as? Iterable<I2>)?.iterator()){
        this.start= start
    }
}