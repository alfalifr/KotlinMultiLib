package sidev.lib.collection.sequence

import sidev.lib.collection.iterator.*
import sidev.lib.structure.data.value.LeveledValue


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

fun <I, O> leveledNestedSequence(start: I, getInputBlock: ((output: O) -> Iterator<I>?)?= null, getOutputBlock: (input: I) -> Iterator<O>?)
        : LeveledNestedSequence<O> = object : LeveledNestedSequence<O> {
    override var currentLevel: Int= 0
        private set
    private var currLevel: Int
        get()= currentLevel
        set(v){ currentLevel= v }

    override fun iterator(): LeveledNestedIterator<I, O> = leveledNestedIterator(start, getInputBlock, getOutputBlock).let { itr ->
        object : LeveledNestedIterator<I, O> by itr {
            init{ currLevel= 0 }
            override fun next(): LeveledValue<O> = itr.next().also { currLevel= currentLevel }
        }
    }
}
fun <I, O> leveledNestedSequence(startSequence: Sequence<I>, getInputBlock: ((output: O) -> Iterator<I>?)?= null, getOutputBlock: (input: I) -> Iterator<O>?)
        : LeveledNestedSequence<O> = object : LeveledNestedSequence<O> {
    override var currentLevel: Int= 0
        private set
    private var currLevel: Int
        get()= currentLevel
        set(v){ currentLevel= v }

    override fun iterator(): LeveledNestedIterator<I, O> = leveledNestedIterator(startSequence.iterator(), getInputBlock, getOutputBlock).let { itr ->
        object : LeveledNestedIterator<I, O> by itr {
            init{ currLevel= 0 }
            override fun next(): LeveledValue<O> = itr.next().also { currLevel= currentLevel }
        }
    }
}



fun <I1, I2, O1, O2> transitiveNestedSequence(
    start: I2,
    outArgBlock: (output: O2) -> O1,
    inArgBlock: ((input: I2) -> I1)?= null,
    getInputBlock: ((output: O2, outArg: O1?) -> Iterator<I2>?)?= null,
    getOutputBlock: (input: I2, inArg: I1?) -> Iterator<O2>?
): NestedSequence<O2> = object : NestedSequence<O2> {
    override fun iterator(): NestedIterator<I2, O2> =
        transitiveNestedIterator(start, outArgBlock, inArgBlock, getInputBlock, getOutputBlock)
}
fun <I1, I2, O1, O2> transitiveNestedSequence(
    startSequence: Sequence<I2>,
    outArgBlock: (output: O2) -> O1,
    inArgBlock: ((input: I2) -> I1)?= null,
    getInputBlock: ((output: O2, outArg: O1?) -> Iterator<I2>?)?= null,
    getOutputBlock: (input: I2, inArg: I1?) -> Iterator<O2>?
): NestedSequence<O2> = object : NestedSequence<O2> {
    override fun iterator(): NestedIterator<I2, O2> =
        transitiveNestedIterator(startSequence.iterator(), outArgBlock, inArgBlock, getInputBlock, getOutputBlock)
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


fun <I, O> skippingLeveledNestedSequence(
    start: I,
    getInputBlock: ((output: O) -> Iterator<I>?)?= null,
    skipBlock: ((output: LeveledValue<O>) -> Boolean)?= null,
    getOutputBlock: (input: I) -> Iterator<O>?
): SkippingLeveledNestedSequence<O> = object : SkippingLeveledNestedSequenceImpl<O>({
    leveledNestedIterator(start, getInputBlock, getOutputBlock)
}) {
    override fun skip(now: LeveledValue<O>): Boolean = skipBlock?.invoke(now) ?: false
}
fun <I, O> skippingLeveledNestedSequence(
    startSequence: Sequence<I>,
    getInputBlock: ((output: O) -> Iterator<I>?)?= null,
    skipBlock: ((output: LeveledValue<O>) -> Boolean)?= null,
    getOutputBlock: (input: I) -> Iterator<O>?
): SkippingLeveledNestedSequence<O> = object : SkippingLeveledNestedSequenceImpl<O>({
    leveledNestedIterator(startSequence.iterator(), getInputBlock, getOutputBlock)
}) {
    override fun skip(now: LeveledValue<O>): Boolean = skipBlock?.invoke(now) ?: false
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

fun <O> leveledNestedSequenceSimple(start: O, getOutputBlock: (input: O) -> Iterator<O>?)
        : LeveledNestedSequence<O> = object : LeveledNestedSequence<O> {
    override var currentLevel: Int= 0
        private set
    private var currLevel: Int
        get()= currentLevel
        set(v){ currentLevel= v }

    override fun iterator(): LeveledNestedIterator<*, O> = leveledNestedIteratorSimple(start, getOutputBlock).let { itr ->
        object : LeveledNestedIteratorSimple<O> by itr {
            init{ currLevel= 0 }
            override fun next(): LeveledValue<O> = itr.next().also { currLevel= currentLevel }
        }
    }
}
fun <O> leveledNestedSequenceSimple(startSequence: Sequence<O>, getOutputBlock: (input: O) -> Iterator<O>?)
        : LeveledNestedSequence<O> = object : LeveledNestedSequence<O> {
    override var currentLevel: Int= 0
        private set
    private var currLevel: Int
        get()= currentLevel
        set(v){ currentLevel= v }

    override fun iterator(): LeveledNestedIterator<*, O> = leveledNestedIteratorSimple(startSequence.iterator(), getOutputBlock).let { itr ->
        object : LeveledNestedIteratorSimple<O> by itr {
            init{ currLevel= 0 }
            override fun next(): LeveledValue<O> = itr.next().also { currLevel= currentLevel }
        }
    }
}



fun <O1, O2> transitiveNestedSequenceSimple(
    start: O2,
    outArgBlock: (output: O2) -> O1,
    getOutputBlock: (input: O2, inArg: O1?) -> Iterator<O2>?
): NestedSequence<O2> = object : NestedSequence<O2> {
    override fun iterator(): NestedIterator<O2, O2> =
        transitiveNestedIteratorSimple(start, outArgBlock, getOutputBlock)
}
fun <O1, O2> transitiveNestedSequenceSimple(
    startSequence: Sequence<O2>,
    outArgBlock: (output: O2) -> O1,
    getOutputBlock: (input: O2, inArg: O1?) -> Iterator<O2>?
): NestedSequence<O2> = object : NestedSequence<O2> {
    override fun iterator(): NestedIterator<O2, O2> =
        transitiveNestedIteratorSimple(startSequence.iterator(), outArgBlock, getOutputBlock)
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

fun <O> skippingLeveledNestedSequenceSimple(
    start: O,
    skipBlock: ((output: LeveledValue<O>) -> Boolean)?= null,
    getOutputBlock: (input: O) -> Iterator<O>?
): SkippingLeveledNestedSequence<O> = object : SkippingLeveledNestedSequenceImpl<O>({
    leveledNestedIteratorSimple(start, getOutputBlock)
}) {
    override fun skip(now: LeveledValue<O>): Boolean = skipBlock?.invoke(now) ?: false
}

fun <O> skippingLeveledNestedSequenceSimple(
    startSequence: Sequence<O>,
    skipBlock: ((output: LeveledValue<O>) -> Boolean)?= null,
    getOutputBlock: (input: O) -> Iterator<O>?
): SkippingLeveledNestedSequence<O> = object : SkippingLeveledNestedSequenceImpl<O>({
    leveledNestedIteratorSimple(startSequence.iterator(), getOutputBlock)
}) {
    override fun skip(now: LeveledValue<O>): Boolean = skipBlock?.invoke(now) ?: false
}