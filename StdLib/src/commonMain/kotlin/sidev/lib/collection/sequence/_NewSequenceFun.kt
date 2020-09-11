package sidev.lib.collection.sequence

import sidev.lib.collection.iterator.nestedIterator
import sidev.lib.collection.iterator.nestedIteratorSimple
import sidev.lib.collection.iterator.NestedIterator


/*
========================
New NestedSequence Fun
========================
 */

fun <I, O> nestedSequence(start: I, getInputBlock: ((output: O) -> Iterator<I>?)?= null, getOutputBlock: (input: I) -> Iterator<O>?)
        : NestedSequence<O> = object : NestedSequence<O> {
    override fun iterator(): NestedIterator<I, O> = nestedIterator(start, getInputBlock, getOutputBlock)
}
fun <I, O> nestedSequence(startSequence: Sequence<I>, getInputBlock: ((output: O) -> Iterator<I>?)?= null, getOutputBlock: (input: I) -> Iterator<O>?)
        : NestedSequence<O> = object : NestedSequence<O> {
    override fun iterator(): NestedIterator<I, O> = nestedIterator(startSequence.iterator(), getInputBlock, getOutputBlock)
}



fun <O> nestedSequenceSimple(start: O, getOutputBlock: (input: O) -> Iterator<O>?)
        : NestedSequence<O> = object : NestedSequence<O> {
    override fun iterator(): NestedIterator<*, O> =
        nestedIteratorSimple(start, getOutputBlock)
}
fun <O> nestedSequenceSimple(startSequence: Sequence<O>, getOutputBlock: (input: O) -> Iterator<O>?)
        : NestedSequence<O> = object : NestedSequence<O> {
    override fun iterator(): NestedIterator<*, O> =
        nestedIteratorSimple(startSequence.iterator(), getOutputBlock)
}