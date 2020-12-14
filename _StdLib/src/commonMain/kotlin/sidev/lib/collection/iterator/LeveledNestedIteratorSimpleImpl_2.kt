package sidev.lib.collection.iterator
/*
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.annotation.Note
import sidev.lib.collection.sequence.withLevel
import sidev.lib.structure.data.value.LeveledValue


/*TODO*/ @Note("Senin, 2 Nov 2020", "Kelas LeveledNestedIteratorSimpleImpl_2 msh blum punya interface")
abstract class LeveledNestedIteratorSimpleImpl_2<T>(/*private val */startIterator: Iterator<T>?)
    : LeveledNestedIteratorImpl_2<T, T>(startIterator) {
    constructor(startInputIterable: Iterable<T>): this(startInputIterable.iterator())
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    constructor(start: T?): this((start as? Iterable<T>)?.iterator()){
        this.start= start
    }

    final override val activeInputLines: ArrayList<Iterator<T>>
        get() = super.activeOutputLines
    final override var activeInputIterator: Iterator<T>?
        get() = super.activeOutputIterator
        set(value) { super.activeOutputIterator= value }
/*
    final override val activeInputLineLevels: ArrayList<Int>
        get() = super.activeOutputLineLevels
    final override var activeInputIteratorLevel: Int
        get() = super.activeOutputIteratorLevel
        set(value) { super.activeOutputIteratorLevel= value }
 */
    /*
    override val activeOutputLines: ArrayList<Iterator<T>>
        get() = super.activeInputLines
    override var activeOutputIterator: Iterator<T>?
        get() = super.activeInputIterator
        set(value) {
            super.activeInputIterator= value
        }
    override fun addOutputIterator(outItr: Iterator<LeveledValue<T>>) {
        super.addOutputIterator(outItr)
        activeOutputLineLevels += ++activeOutputIteratorLevel
    }

    override fun changeLastActiveOutputIterator(currentActiveOutputIterator: Iterator<LeveledValue<T>>) {
        super.changeLastActiveOutputIterator(currentActiveOutputIterator)
        activeOutputLineLevels -= activeOutputIteratorLevel--
    }
 */
//    final override fun getOutputIterator(nowInput: T): Iterator<T>? = getInputIterator(nowInput) //Yg di-override adalah output karena getInputIterator menambah nilai level.
    final override fun getInputIterator(nowOutput: T): Iterator<T>? = getOutputIterator(nowOutput) //Yg di-override adalah output karena getInputIterator menambah nilai level.
    final override fun addInputIterator(inItr: Iterator<T>) = addOutputIterator(inItr)
    final override fun changeLastActiveInputIterator(currentActiveInputIterator: Iterator<T>)
            = changeLastActiveOutputIterator(currentActiveInputIterator)

//    internal open val activeOutputLineLevels= ArrayList<Int>()
//    internal open val activeInputLineLevels= ArrayList<Int>()
//    internal open var activeOutputIteratorLevel: Int= -1
//    internal open var activeInputIteratorLevel: Int= -1

    /*
    override fun getOutputIterator(nowInput: I): Iterator<LeveledValue<O>>? {
        return getOutputValueIterator(nowInput).notNullTo { outItr ->
            val outItrLevel= getOutputIteratorLevel(outItr, nowInput, activeOutputIteratorLevel +1) //activeOutputIteratorLevel +1 karena penambahan level dilakukan setelah pengambilan outputIterator.
            outItr.toOtherIterator { LeveledValue(outItrLevel, it) }
        }
    }
 */

    //    override fun getOutputIteratorLevel(outputIterator: Iterator<O>, fromInput: I, level: Int): Int = level
//    override fun getInputIteratorLevel(inputIterator: Iterator<I>, fromOutput: O, level: Int): Int = level
/*
    override fun addInputIterator(inItr: Iterator<I>) {
        super.addInputIterator(inItr)
        activeInputLineLevels += ++activeInputIteratorLevel
    }

    override fun addOutputIterator(outItr: Iterator<LeveledValue<O>>) {
        super.addOutputIterator(outItr)
        activeOutputLineLevels += ++activeOutputIteratorLevel
    }
 */
/*
    override fun addOutputIterator(outItr: Iterator<O>) {
        super.addOutputIterator(outItr)
        activeOutputLineLevels += ++activeOutputIteratorLevel
    }
 */
/*
    override fun changeLastActiveInputIterator(currentActiveInputIterator: Iterator<I>) {
        super.changeLastActiveInputIterator(currentActiveInputIterator)
        activeInputLineLevels -= activeInputIteratorLevel--
    }

    override fun changeLastActiveOutputIterator(currentActiveOutputIterator: Iterator<LeveledValue<O>>) {
        super.changeLastActiveOutputIterator(currentActiveOutputIterator)
        activeOutputLineLevels -= activeOutputIteratorLevel--
    }
 */
/*
    override fun changeLastActiveOutputIterator(currentActiveOutputIterator: Iterator<O>) {
        super.changeLastActiveOutputIterator(currentActiveOutputIterator)
        activeOutputLineLevels -= activeOutputIteratorLevel--
    }
 */
}

 */