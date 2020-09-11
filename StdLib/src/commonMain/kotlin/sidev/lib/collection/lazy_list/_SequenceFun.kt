package sidev.lib.collection.lazy_list

import sidev.lib.collection.iterator.NestedIterator
import sidev.lib.collection.iterator.NestedIteratorImpl
import sidev.lib.collection.iterator.SkippableIteratorImpl
import sidev.lib.collection.sequence.NestedSequence


fun <T> Sequence<T>.isEmpty(): Boolean = !iterator().hasNext()
fun <T> Sequence<T>.isNotEmpty(): Boolean = !isEmpty()


fun <I, O> Sequence<I>.toOtherSequence(mapping: (I) -> O): Sequence<O>
        = object: Sequence<O>{
    override fun iterator(): Iterator<O>
            = object: Iterator<O>{
        private val originItr= this@toOtherSequence.iterator()
        override fun hasNext(): Boolean = originItr.hasNext()
        override fun next(): O = mapping(originItr.next())
    }
}

operator fun <T> Sequence<T>.minus(other: Sequence<T>): Sequence<T> = cut(other)
operator fun <T> Sequence<T>.minus(other: Iterator<T>): Sequence<T> = cut(other)
operator fun <T> Sequence<T>.minus(other: Iterable<T>): Sequence<T> = cut(other)

fun <T> Sequence<T>.cut(other: Sequence<T>): Sequence<T>
        = object : Sequence<T>{
    override fun iterator(): Iterator<T>
            = object : SkippableIteratorImpl<T>(this@cut.iterator()){
        val otherAsCached= other.asCached()
        override fun skip(now: T): Boolean = now in otherAsCached
    }
}

fun <T> Sequence<T>.cut(other: Iterator<T>): Sequence<T>
        = object : Sequence<T>{
    override fun iterator(): Iterator<T>
            = object : SkippableIteratorImpl<T>(this@cut.iterator()){
        val otherAsCached= other.asCached()
        override fun skip(now: T): Boolean = now in otherAsCached
    }
}

fun <T> Sequence<T>.cut(other: Iterable<T>): Sequence<T>
        = object : Sequence<T>{
    override fun iterator(): Iterator<T>
            = object : SkippableIteratorImpl<T>(this@cut.iterator()){
        //        val otherAsCached= other
        override fun skip(now: T): Boolean = now in other
    }
}

fun <T> Sequence<Sequence<T>>.flattenToNested(): NestedSequence<T>
        = object : NestedSequence<T> {
    override fun iterator(): NestedIterator<Sequence<T>, T>
            = object : NestedIteratorImpl<Sequence<T>, T>(this@flattenToNested.iterator()){
        override fun getOutputIterator(nowInput: Sequence<T>): Iterator<T>? = nowInput.iterator()
        override fun getInputIterator(nowOutput: T): Iterator<Sequence<T>>? = null
    }
}