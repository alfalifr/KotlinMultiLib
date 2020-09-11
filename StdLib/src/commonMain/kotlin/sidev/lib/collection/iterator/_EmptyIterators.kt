package sidev.lib.collection.iterator

import sidev.lib.`val`.SuppressLiteral


object EmptyIterator : ListIterator<Nothing> {
    override fun hasNext(): Boolean = false
    override fun hasPrevious(): Boolean = false
    override fun nextIndex(): Int = 0
    override fun previousIndex(): Int = -1
    override fun next(): Nothing = throw NoSuchElementException("Iterator kosong.")
    override fun previous(): Nothing = throw NoSuchElementException("Iterator kosong.")
}

object EmptyNestedIterator: NestedIterator<Nothing, Nothing>, NestedIteratorSimple<Nothing>{
    override fun hasNext(): Boolean = false
    override fun next(): Nothing = throw NoSuchElementException("Iterator kosong.")
    override fun getOutputIterator(nowInput: Nothing): Iterator<Nothing>? = null
    override fun getInputIterator(nowOutput: Nothing): Iterator<Nothing>? = null
}

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <I, O> emptyNestedIterator(): NestedIterator<I, O> = EmptyNestedIterator as NestedIterator<I, O>

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> emptyIterator(): Iterator<T> = EmptyNestedIterator as Iterator<T>

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <O> emptyNestedIteratorSimple(): NestedIteratorSimple<O> = EmptyNestedIterator as NestedIteratorSimple<O>