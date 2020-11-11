package sidev.lib.collection.sequence

import sidev.lib.collection.iterator.*
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.check.asNotNullTo
import sidev.lib.structure.data.value.LeveledValue
import sidev.lib.structure.data.value.withLevel


fun <O> NestedSequence<O>.withLevel(): LeveledNestedSequence<O> = object : LeveledNestedSequence<O>{
    override var currentLevel: Int= 0
    fun setCurrLevel(level: Int){ currentLevel= level }

    override fun iterator(): LeveledNestedIterator<*, O> = this@withLevel.iterator().withLevel().asNotNullTo { itr: LeveledNestedIterator<Any?, O> ->
        object : LeveledNestedIterator<Any?, O> by itr {
            override fun next(): LeveledValue<O> = itr.next().also { setCurrLevel(currentLevel) }
        }
    }!!
}
fun <O> LeveledNestedSequence<O>.asValueSequence(): NestedSequence<O> = object : NestedSequence<O>{
    override fun iterator(): NestedIterator<*, O> = this@asValueSequence.iterator().asValueIterator()
}

fun <O> NestedSequence<O>.skip(func: (now: O) -> Boolean): Sequence<O>
        = object : Sequence<O>{
    override fun iterator(): Iterator<O>
            = this@skip.iterator().skip(func)
}


@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <I, O> NestedIterator<I, O>.skip(func: (now: O) -> Boolean): SkippableIterator<O> {
    val startInputItr= if(this is NestedIteratorImpl) startInputIterator else null
    val start= if(this is NestedIteratorImpl) start else null

    return if(this is NestedIteratorSimple<*>){
        object : NestedIteratorSimpleImpl<O>(startInputItr as? Iterator<O>){
            init{ this.start= start as? O }
            override fun getOutputIterator(nowInput: O): Iterator<O>? = this@skip.getOutputIterator(nowInput as I)
            override fun skip(now: O): Boolean = func(now)
        }
    } else object : NestedIteratorImpl<I, O>(startInputItr){
        init{ this.start= start }
        override fun getOutputIterator(nowInput: I): Iterator<O>? = this@skip.getOutputIterator(nowInput)
        override fun getInputIterator(nowOutput: O): Iterator<I>? = this@skip.getInputIterator(nowOutput)
        override fun skip(now: O): Boolean = func(now)
    }
}

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <I, O> NestedIterator<I, O>.withLevel(): LeveledNestedIterator<I, O> {
    if(this is NestedValueIterator<*, *>)
        return leveledIterator as LeveledNestedIterator<I, O>

    val start: I?
    val startInputItr= if(this is NestedIteratorImpl){
        start= this.start
        startInputIterator
    } else {
        start= null
        null
    }

    return if(this is NestedIteratorSimple<*>){
        object : LeveledNestedIteratorSimpleImpl<O>(startInputItr as? Iterator<O>){
            init{ this.start= start?.withLevel() as? LeveledValue<O> }

            override fun getOutputValueIterator(nowInput: O): Iterator<O>? =
                this@withLevel.getOutputIterator(nowInput as I)

//            override fun getInputValueIterator(nowOutput: O): Iterator<O>? = getOutputValueIterator(nowOutput)
        } as LeveledNestedIterator<I, O>
    } else object : LeveledNestedIteratorImpl<I, O>(startInputItr){
        init{ this.start= start?.withLevel() }

        override fun getInputValueIterator(nowOutput: O): Iterator<I>? =
            this@withLevel.getInputIterator(nowOutput)

        override fun getOutputValueIterator(nowInput: I): Iterator<O>? =
            this@withLevel.getOutputIterator(nowInput)
    }
}
/**
 * Mengubah [LeveledNestedIterator] menjadi [NestedIterator] biasa.
 * [NestedIterator] yg dihasilkan bkn merupakan [NestedIteratorImpl] atau [NestedIteratorSimpleImpl]
 * untuk mengakomodasi level pada [LeveledNestedIterator]
 */
fun <I, O> LeveledNestedIterator<I, O>.asValueIterator(): NestedIterator<I, O> = NestedValueIteratorImpl(this)
/*
{
/*
    val start: LeveledValue<I>?
    val startInputItr= if(this is LeveledNestedIteratorImpl){
        start= this.start
        startInputIterator
    } else {
        start= null
        null
    }
 */

    return NestedValueIteratorImpl(this)
/*
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    return if(this is LeveledNestedIteratorSimple<*>){
        NestedValueIteratorImpl(this as LeveledNestedIterator<O, O>) as NestedIterator<I, O>
    } else NestedValueIteratorImpl(this)
 */
}
 */