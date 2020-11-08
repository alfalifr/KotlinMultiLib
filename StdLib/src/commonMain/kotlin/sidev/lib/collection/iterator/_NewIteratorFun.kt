package sidev.lib.collection.iterator

import sidev.lib.`val`.SuppressLiteral


fun <I, O> iterator(vararg element: I, mapping: ((I) -> O)?= null): Iterator<O>{
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    return if(mapping != null) element.iterator().toOtherIterator(mapping)
    else element.iterator() as Iterator<O>
} //= element.iterator()


fun <T> iteratorSimple(vararg element: T): Iterator<T> = element.iterator()
fun <T> iteratorSimpleNotNull(vararg element: T): Iterator<T> = element.filterNotNull().iterator()


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

fun <I, O> leveledNestedIterator(start: I, getInputBlock: ((output: O) -> Iterator<I>?)?= null, getOutputBlock: (input: I) -> Iterator<O>?)
: LeveledNestedIterator<I, O> = object : LeveledNestedIteratorImpl<I, O>(start){
    override fun getOutputValueIterator(nowInput: I): Iterator<O>? = getOutputBlock(nowInput)
    override fun getInputValueIterator(nowOutput: O): Iterator<I>? = getInputBlock?.invoke(nowOutput)
}
fun <I, O> leveledNestedIterator(startIterator: Iterator<I>, getInputBlock: ((output: O) -> Iterator<I>?)?= null, getOutputBlock: (input: I) -> Iterator<O>?)
: LeveledNestedIterator<I, O> = object : LeveledNestedIteratorImpl<I, O>(startIterator){
    override fun getOutputValueIterator(nowInput: I): Iterator<O>? = getOutputBlock(nowInput)
    override fun getInputValueIterator(nowOutput: O): Iterator<I>? = getInputBlock?.invoke(nowOutput)
}


fun <I1, I2, O1, O2> transitiveNestedIterator(
    start: I2,
    outArgBlock: (output: O2) -> O1,
    inArgBlock: ((input: I2) -> I1)?= null,
    getInputBlock: ((output: O2, outArg: O1?) -> Iterator<I2>?)?= null,
    getOutputBlock: (input: I2, inArg: I1?) -> Iterator<O2>?
): TransitiveNestedIterator<I1?, I2, O1, O2> = object : TransitiveNestedIteratorImpl<I1?, I2, O1, O2>(start){
    override fun getOutputIterator(nowInput: I2, inputArg: I1?): Iterator<O2>? = getOutputBlock(nowInput, inputArg)
    override fun getInputIterator(nowOutput: O2, outputArg: O1): Iterator<I2>? = getInputBlock?.invoke(nowOutput, outputArg)

    override fun getInputArg(nowInput: I2): I1? = inArgBlock?.invoke(nowInput)
    override fun getOutputArg(nowOutput: O2): O1 = outArgBlock(nowOutput)
}

fun <I1, I2, O1, O2> transitiveNestedIterator(
    startIterator: Iterator<I2>,
    outArgBlock: (output: O2) -> O1,
    inArgBlock: ((input: I2) -> I1)?= null,
    getInputBlock: ((output: O2, outArg: O1?) -> Iterator<I2>?)?= null,
    getOutputBlock: (input: I2, inArg: I1?) -> Iterator<O2>?
): TransitiveNestedIterator<I1?, I2, O1, O2> = object : TransitiveNestedIteratorImpl<I1?, I2, O1, O2>(startIterator){
    override fun getOutputIterator(nowInput: I2, inputArg: I1?): Iterator<O2>? = getOutputBlock(nowInput, inputArg)
    override fun getInputIterator(nowOutput: O2, outputArg: O1): Iterator<I2>? = getInputBlock?.invoke(nowOutput, outputArg)

    override fun getInputArg(nowInput: I2): I1? = inArgBlock?.invoke(nowInput)
    override fun getOutputArg(nowOutput: O2): O1 = outArgBlock(nowOutput)
}



fun <O> nestedIteratorSimple(start: O, getOutputBlock: (input: O) -> Iterator<O>?)
: NestedIteratorSimple<O> = object : NestedIteratorSimpleImpl<O>(start){
    override fun getOutputIterator(nowInput: O): Iterator<O>? = getOutputBlock(nowInput)
}
fun <O> nestedIteratorSimple(startIterator: Iterator<O>, getOutputBlock: (input: O) -> Iterator<O>?)
: NestedIteratorSimple<O> = object : NestedIteratorSimpleImpl<O>(startIterator){
    override fun getOutputIterator(nowInput: O): Iterator<O>? = getOutputBlock(nowInput)
}


fun <O> leveledNestedIteratorSimple(start: O, getOutputBlock: (input: O) -> Iterator<O>?)
: LeveledNestedIteratorSimple<O> = object : LeveledNestedIteratorSimpleImpl<O>(start){
    override fun getOutputValueIterator(nowInput: O): Iterator<O>? = getOutputBlock(nowInput)
}
fun <O> leveledNestedIteratorSimple(startIterator: Iterator<O>, getOutputBlock: (input: O) -> Iterator<O>?)
: LeveledNestedIteratorSimple<O> = object : LeveledNestedIteratorSimpleImpl<O>(startIterator){
    override fun getOutputValueIterator(nowInput: O): Iterator<O>? = getOutputBlock(nowInput)
}


fun <O1, O2> transitiveNestedIteratorSimple(
    start: O2,
    outArgBlock: (output: O2) -> O1,
    getOutputBlock: (input: O2, inArg: O1) -> Iterator<O2>?
): TransitiveNestedIteratorSimple<O1, O2> = object : TransitiveNestedIteratorSimpleImpl<O1, O2>(start){
    override fun getOutputIterator(nowInput: O2, inputArg: O1): Iterator<O2>? = getOutputBlock(nowInput, inputArg)
    override fun getOutputArg(nowOutput: O2): O1 = outArgBlock(nowOutput)
}

fun <O1, O2> transitiveNestedIteratorSimple(
    startIterator: Iterator<O2>,
    outArgBlock: (output: O2) -> O1,
    getOutputBlock: (input: O2, inArg: O1) -> Iterator<O2>?
): TransitiveNestedIteratorSimple<O1, O2> = object : TransitiveNestedIteratorSimpleImpl<O1, O2>(startIterator){
    override fun getOutputIterator(nowInput: O2, inputArg: O1): Iterator<O2>? = getOutputBlock(nowInput, inputArg)
    override fun getOutputArg(nowOutput: O2): O1 = outArgBlock(nowOutput)
}


