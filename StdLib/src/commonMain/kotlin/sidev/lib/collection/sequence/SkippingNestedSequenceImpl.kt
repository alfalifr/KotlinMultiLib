package sidev.lib.collection.sequence

import sidev.lib.check.notNullTo
import sidev.lib.collection.iterator.NestedIterator
import sidev.lib.collection.iterator.NestedIteratorImpl

internal abstract class SkippingNestedSequenceImpl<T>(val iteratorBuilder: () -> NestedIterator<*, T>): SkippingNestedSequence<T>{
    override fun iterator(): NestedIterator<*, T> = iteratorBuilder().let { nestedItr ->
        val startInput= if(nestedItr is NestedIteratorImpl<*, *>) nestedItr.start else null
        nestedItr as NestedIterator<Any?, T>

        object : NestedIteratorImpl<Any?, T>(startInput){  //, NestedIterator<Any?, T> by nestedItr as NestedIterator<Any?, T>{
            override fun getOutputIterator(nowInput: Any?): Iterator<T>? = nowInput.notNullTo { nestedItr.getOutputIterator(it) }
            override fun getInputIterator(nowOutput: T): Iterator<Any?>? = nestedItr.getInputIterator(nowOutput)
            override fun skip(now: T): Boolean = this@SkippingNestedSequenceImpl.skip(now)
        }
    }
}