package sidev.lib.collection.iterator

import sidev.lib.universal.structure.collection.iterator.NestedIterator
import sidev.lib.universal.structure.collection.sequence.NestedSequence


fun <I, O> iterator(vararg element: I, mapping: ((I) -> O)?= null): Iterator<O>{
    return if(mapping != null) element.iterator().toOtherIterator(mapping)
    else element.iterator() as Iterator<O>
} //= element.iterator()


fun <T> iteratorSimple(vararg element: T): Iterator<T> = element.iterator()


fun <I, O> nestedIterator(start: I, getInputBlock: ((output: O) -> Iterator<I>?)?= null, getOutputBlock: (input: I) -> Iterator<O>?)
: NestedIterator<I, O> = object : NestedIteratorImpl<I, O>(start){
    override fun getOutputIterator(nowInput: I): Iterator<O>? = getOutputBlock(nowInput)
    override fun getInputIterator(nowOutput: O): Iterator<I>? = getInputBlock?.invoke(nowOutput)
}
fun <I, O> nestedIterator(startIterator: Iterator<I>, getInputBlock: ((output: O) -> Iterator<I>?)?= null, getOutputBlock: (input: I) -> Iterator<O>?)
: NestedIterator<I, O> = object : NestedIteratorImpl<I, O>(startIterator){
    override fun getOutputIterator(nowInput: I): Iterator<O>? = getOutputBlock(nowInput)
    override fun getInputIterator(nowOutput: O): Iterator<I>? = getInputBlock?.invoke(nowOutput)
}



fun <O> nestedIteratorSimple(start: O, getOutputBlock: (input: O) -> Iterator<O>?)
: NestedIteratorSimple<O> = object : NestedIteratorSimpleImpl<O>(start){
    override fun getOutputIterator(nowInput: O): Iterator<O>? = getOutputBlock(nowInput)
}
fun <O> nestedIteratorSimple(startIterator: Iterator<O>, getOutputBlock: (input: O) -> Iterator<O>?)
: NestedIteratorSimple<O> = object : NestedIteratorSimpleImpl<O>(startIterator){
    override fun getOutputIterator(nowInput: O): Iterator<O>? = getOutputBlock(nowInput)
}



fun <I, O> nestedSequence(start: I, getInputBlock: ((output: O) -> Iterator<I>?)?= null, getOutputBlock: (input: I) -> Iterator<O>?)
: NestedSequence<O> = object : NestedSequence<O>{
    override fun iterator(): NestedIterator<*, O> = nestedIterator(start, getInputBlock, getOutputBlock)
}
fun <I, O> nestedSequence(startIterator: Iterator<I>, getInputBlock: ((output: O) -> Iterator<I>?)?= null, getOutputBlock: (input: I) -> Iterator<O>?)
: NestedSequence<O> = object : NestedSequence<O>{
    override fun iterator(): NestedIterator<*, O> = nestedIterator(startIterator, getInputBlock, getOutputBlock)
}



fun <O> nestedSequenceSimple(start: O, getOutputBlock: (input: O) -> Iterator<O>?)
: NestedSequence<O> = object : NestedSequence<O>{
    override fun iterator(): NestedIterator<*, O> =
        nestedIteratorSimple(start, getOutputBlock)
}
fun <O> nestedSequenceSimple(startIterator: Iterator<O>, getOutputBlock: (input: O) -> Iterator<O>?)
: NestedSequence<O> = object : NestedSequence<O>{
    override fun iterator(): NestedIterator<*, O> =
        nestedIteratorSimple(startIterator, getOutputBlock)
}