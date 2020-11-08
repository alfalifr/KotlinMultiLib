package sidev.lib.collection.sequence

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.collection.iterator.*

abstract class SkippingNestedSequenceImpl<T>(val iteratorBuilder: () -> NestedIterator<*, T>): SkippingNestedSequence<T>{
    override fun iterator(): NestedIterator<*, T> = iteratorBuilder().let {

        @Suppress(SuppressLiteral.UNCHECKED_CAST)
        object : NestedIterator<Any?, T> by it as NestedIterator<Any?, T>, SkippableIterator<T> {
            private var status= -1
            private var next: T?= null

            private fun calcNext(){
                if(status >= 0)
                    return
                status= 0
                if(it.hasNext()){
                    do { next= it.next() }
                    while (it.hasNext() && skip(next!!))
                    status= 1
                }
            }
            override fun hasNext(): Boolean {
                if(status < 0)
                    calcNext()
                return status == 1
            }
            override fun next(): T {
                if(status < 0)
                    calcNext()
                if(status != 1)
                    throw NoSuchElementException()
                status= -1
                return next!!
            }
            override fun skip(now: T): Boolean = this@SkippingNestedSequenceImpl.skip(now)
        }
    }
/*
        .let { nestedItr ->
        nestedItr as NestedIterator<Any?, T>

//        prind("SkippingNestedSequenceImpl nestedItr is NestedIteratorImpl<*, *> = ${nestedItr is NestedIteratorImpl<*, *>}")
//        NestedIteratorSimpleImpl

//        prine("SkippingNestedSequenceImpl nestedItr.hasNext()= ${nestedItr.hasNext()}")
//        prine("SkippingNestedSequenceImpl nestedItr.hasNext()= ${nestedItr.hasNext()}")

        object : NestedIteratorImpl<Any?, T>(null){  //, NestedIterator<Any?, T> by nestedItr as NestedIterator<Any?, T>{
            override fun getOutputIterator(nowInput: Any?): Iterator<T>? = nestedItr.getOutputIterator(nowInput)
            override fun getInputIterator(nowOutput: T): Iterator<Any?>? = nestedItr.getInputIterator(nowOutput)
            override fun skip(now: T): Boolean = this@SkippingNestedSequenceImpl.skip(now)
        }
    }
 */
/*
        .asNotNull { itr: NestedIterator<*, T> ->
            prine("SkippingNestedSequenceImpl.iterator().hasNext()= ${itr.hasNext()}")
        }!!
// */
}