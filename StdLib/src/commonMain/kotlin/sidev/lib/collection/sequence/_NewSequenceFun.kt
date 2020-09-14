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


fun <I, O> skippingNestedSequence(
    start: I,
    getInputBlock: ((output: O) -> Iterator<I>?)?= null,
    skipBlock: ((output: O) -> Boolean)?= null,
    getOutputBlock: (input: I) -> Iterator<O>?
): SkippingNestedSequence<O> = object : SkippingNestedSequenceImpl<O>({
    nestedIterator(start, getInputBlock, getOutputBlock)
}) {
    override fun skip(now: O): Boolean = skipBlock?.invoke(now) ?: false
}
fun <I, O> skippingNestedSequence(
    startSequence: Sequence<I>,
    getInputBlock: ((output: O) -> Iterator<I>?)?= null,
    skipBlock: ((output: O) -> Boolean)?= null,
    getOutputBlock: (input: I) -> Iterator<O>?
): SkippingNestedSequence<O> = object : SkippingNestedSequenceImpl<O>({
    nestedIterator(startSequence.iterator(), getInputBlock, getOutputBlock)
}) {
    override fun skip(now: O): Boolean = skipBlock?.invoke(now) ?: false
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


fun <O> skippingNestedSequenceSimple(
    start: O,
    skipBlock: ((output: O) -> Boolean)?= null,
    getOutputBlock: (input: O) -> Iterator<O>?
): SkippingNestedSequence<O> = object : SkippingNestedSequenceImpl<O>({
    nestedIteratorSimple(start, getOutputBlock)
}) {
    override fun skip(now: O): Boolean = skipBlock?.invoke(now) ?: false
}

fun <O> skippingNestedSequenceSimple(
    startSequence: Sequence<O>,
    skipBlock: ((output: O) -> Boolean)?= null,
    getOutputBlock: (input: O) -> Iterator<O>?
): SkippingNestedSequence<O> = object : SkippingNestedSequenceImpl<O>({
    nestedIteratorSimple(startSequence.iterator(), getOutputBlock)
}) {
    override fun skip(now: O): Boolean = skipBlock?.invoke(now) ?: false
}