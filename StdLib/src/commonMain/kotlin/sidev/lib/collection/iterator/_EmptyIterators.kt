package sidev.lib.collection.iterator

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.universal.structure.collection.iterator.NestedIterator

object EmptyNestedIterator: NestedIterator<Any?, Any?>, NestedIteratorSimple<Any?>{
    override fun hasNext(): Boolean = false
    override fun next(): Any? = throw NoSuchElementException("Iterator kosong.")
    override fun getOutputIterator(nowInput: Any?): Iterator<Any?>? = null
    override fun getInputIterator(nowOutput: Any?): Iterator<Any?>? = null
}

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <I, O> emptyNestedIterator(): NestedIterator<I, O> = EmptyNestedIterator as NestedIterator<I, O>

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <O> emptyNestedIteratorSimple(): NestedIteratorSimple<O> = EmptyNestedIterator as NestedIteratorSimple<O>