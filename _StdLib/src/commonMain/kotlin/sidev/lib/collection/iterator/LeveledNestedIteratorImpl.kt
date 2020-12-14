package sidev.lib.collection.iterator

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.check.notNullTo
import sidev.lib.structure.data.value.LeveledValue
import sidev.lib.structure.data.value.asLeveled


abstract class LeveledNestedIteratorImpl<I, O>(/*private val */startInputIterator: Iterator<I>?)
    : NestedIteratorImpl<LeveledValue<I>, LeveledValue<O>>(
        startInputIterator?.toOtherIterator { LeveledValue(0, it) }
), LeveledNestedIterator<I, O> {
    constructor(startInputIterable: Iterable<I>): this(startInputIterable.iterator())
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    constructor(start: I?): this((start as? Iterable<I>)?.iterator()){
        this.start= start?.asLeveled()
    }

    override var currentLevel: Int= 0
        protected set

    override fun onNext(currentNext: LeveledValue<O>) {
        currentLevel= currentNext.level
    }

    /*
    internal open val activeOutputLineLevels= ArrayList<Int>()
    internal open val activeInputLineLevels= ArrayList<Int>()
    internal open var activeOutputIteratorLevel: Int= -1
    internal open var activeInputIteratorLevel: Int= -1
 */

    final override fun getOutputIterator(nowInput: LeveledValue<I>): Iterator<LeveledValue<O>>? {
        return getOutputValueIterator(nowInput.value).notNullTo { outItr ->
            val outItrLevel= getOutputIteratorLevel(outItr, nowInput.value, nowInput.level)
            outItr.toOtherIterator { LeveledValue(outItrLevel, it) }
        }
    }

    final override fun getInputIterator(nowOutput: LeveledValue<O>): Iterator<LeveledValue<I>>? {
        return getInputValueIterator(nowOutput.value).notNullTo { inItr ->
            val inItrLevel= getInputIteratorLevel(inItr, nowOutput.value, nowOutput.level)
            inItr.toOtherIterator { LeveledValue(inItrLevel, it) }
        }
    }

    override fun getOutputIteratorLevel(outputIterator: Iterator<O>, fromInput: I, inputLevel: Int): Int = inputLevel
    override fun getInputIteratorLevel(inputIterator: Iterator<I>, fromOutput: O, outputLevel: Int): Int = outputLevel +1
/*
    override fun addInputIterator(inItr: Iterator<I>) {
        super.addInputIterator(inItr)
        activeInputLineLevels += ++activeInputIteratorLevel
    }
    override fun getOutputIterator(nowInput: I): Iterator<LeveledValue<O>>? {
        return getOutputValueIterator(nowInput).notNullTo { outItr ->
            val outItrLevel= getOutputIteratorLevel(outItr, nowInput, activeOutputIteratorLevel +1) //activeOutputIteratorLevel +1 karena penambahan level dilakukan setelah pengambilan outputIterator.
            outItr.toOtherIterator { LeveledValue(outItrLevel, it) }
        }
    }
 */

/*
    override fun addOutputIterator(outItr: Iterator<LeveledValue<O>>) {
        super.addOutputIterator(outItr)
        activeOutputLineLevels += ++activeOutputIteratorLevel
    }
    override fun addOutputIterator(outItr: Iterator<O>) {
        super.addOutputIterator(outItr)
        activeOutputLineLevels += ++activeOutputIteratorLevel
    }
    override fun changeLastActiveInputIterator(currentActiveInputIterator: Iterator<I>) {
        super.changeLastActiveInputIterator(currentActiveInputIterator)
        activeInputLineLevels.removeLast(activeInputIteratorLevel) //--
//        activeInputIteratorLevel= activeInputLineLevels.last()
    }
    override fun changeLastActiveOutputIterator(currentActiveOutputIterator: Iterator<LeveledValue<O>>) {
        super.changeLastActiveOutputIterator(currentActiveOutputIterator)
        activeOutputLineLevels.removeLast(activeOutputIteratorLevel) //--
//        activeOutputIteratorLevel= activeOutputLineLevels.last()
    }
 */


/*
    override fun changeLastActiveOutputIterator(currentActiveOutputIterator: Iterator<O>) {
        super.changeLastActiveOutputIterator(currentActiveOutputIterator)
        activeOutputLineLevels -= activeOutputIteratorLevel--
    }
 */
}