package sidev.lib.collection.sequence

import sidev.lib.check.asNotNullTo
import sidev.lib.collection.iterator.*
import sidev.lib.structure.data.value.LeveledValue

abstract class SkippingLeveledNestedSequenceImpl<T>(iteratorBuilder: () -> LeveledNestedIterator<*, T>)
    : SkippingNestedSequenceImpl<LeveledValue<T>>(iteratorBuilder), SkippingLeveledNestedSequence<T>{

    override var currentLevel: Int= 0
        protected set

    override fun iterator(): LeveledNestedIterator<*, T> = iteratorBuilder().asNotNullTo { itr: LeveledNestedIterator<Any?, T> ->
        object : LeveledNestedIterator<Any?, T>, NestedIterator<LeveledValue<Any?>, LeveledValue<T>> by itr, SkippableIterator<LeveledValue<T>> {
            override var currentLevel: Int
                get()= this@SkippingLeveledNestedSequenceImpl.currentLevel
                set(v){ this@SkippingLeveledNestedSequenceImpl.currentLevel= v }

            init {
                currentLevel= 0
            }

            private var status= -1
            private var next: LeveledValue<T>?= null

            private fun calcNext(){
                if(status >= 0)
                    return
                status= 0
                if(itr.hasNext()){
                    do { next= itr.next() }
                    while (itr.hasNext() && skip(next!!))
                    status= 1
                }
            }
            override fun hasNext(): Boolean {
                if(status < 0)
                    calcNext()
                return status == 1
            }
            override fun next(): LeveledValue<T> {
                if(status < 0)
                    calcNext()
                if(status != 1)
                    throw NoSuchElementException()
                currentLevel= itr.currentLevel
                status= -1
                return next!!
            }

            override fun skip(now: LeveledValue<T>): Boolean = this@SkippingLeveledNestedSequenceImpl.skip(now)

            //            override fun skip(now: T): Boolean = this@SkippingLeveledNestedSequenceImpl.skip(now)
            override fun getOutputValueIterator(nowInput: Any?): Iterator<T>? = itr.getOutputValueIterator(nowInput)
            override fun getInputValueIterator(nowOutput: T): Iterator<Any?>? = itr.getInputValueIterator(nowOutput)

/*
            override fun getOutputIterator(nowInput: Any?): Iterator<T>? = itr.getOutputIterator(nowInput)?.also {
                currentLevel= getOutputIteratorLevel(it, nowInput, currentLevel)
            }

            override fun getInputIterator(nowOutput: T): Iterator<Any?>? = itr.getInputIterator(nowOutput)?.also {
                currentLevel= getInputIteratorLevel(it, nowOutput, currentLevel)
            }
 */

            override fun getOutputIteratorLevel(outputIterator: Iterator<T>, fromInput: Any?, inputLevel: Int): Int =
                itr.getOutputIteratorLevel(outputIterator, fromInput, inputLevel)
            override fun getInputIteratorLevel(inputIterator: Iterator<Any?>, fromOutput: T, outputLevel: Int): Int =
                itr.getInputIteratorLevel(inputIterator, fromOutput, outputLevel)
        }
    }!!
/*
        iteratorBuilder().let { nestedItr ->
        val startInputItr= if(nestedItr is NestedIteratorImpl<*, *>) {
            nestedItr.startInputIterator ?: nestedItr.start.notNullTo { iteratorSimple(it) }
        } else null
        nestedItr as NestedIterator<Any?, T>

//        prine("SkippingLeveledNestedSequenceImpl nestedItr.hasNext()= ${nestedItr.hasNext()}")

        object : LeveledNestedIteratorImpl_2<Any?, T>(startInputItr){  //, NestedIterator<Any?, T> by nestedItr as NestedIterator<Any?, T>{
            init {
                currItr?.onLevelChangeListener= null
                currItr= this
                onLevelChangeListener= { this@SkippingLeveledNestedSequenceImpl.currentLevel= currentLevel }
            }
            override fun getOutputIterator(nowInput: Any?): Iterator<T>? = nestedItr.getOutputIterator(nowInput)
            override fun getInputIterator(nowOutput: T): Iterator<Any?>? = nestedItr.getInputIterator(nowOutput)
            override fun skip(now: T): Boolean = this@SkippingLeveledNestedSequenceImpl.skip(now)
        }
    }
 */
}