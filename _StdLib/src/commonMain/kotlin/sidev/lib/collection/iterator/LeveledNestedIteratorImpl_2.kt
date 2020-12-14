package sidev.lib.collection.iterator
/*
import sidev.lib.`val`.SuppressLiteral

/**
 * [LeveledNestedIteratorImpl] dengan mekanisme lain untuk menentukan levelnya.
 * Kelas ini lebih efisien karena menggunakan sedikit pemanggilan fungsi.
 * Namun, kelas ini belum mengimplementasikan [LeveledNestedIterator].
 */
abstract class LeveledNestedIteratorImpl_2<I, O>(/*private val */startInputIterator: Iterator<I>?)
    : NestedIteratorImpl<I, O>(startInputIterator), LeveledIterator_2<O> {
    constructor(startInputIterable: Iterable<I>): this(startInputIterable.iterator())
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    constructor(start: I?): this((start as? Iterable<I>)?.iterator()){
        this.start= start
    }

    override var currentLevel: Int= 0
        protected set(v){
            field= v
            onLevelChangeListener?.invoke(v)
        }

    var onLevelChangeListener: ((level: Int) -> Unit)?= null
        set(v){
            field= v
            v?.invoke(currentLevel)
        }

    override fun addInputIterator(inItr: Iterator<I>) {
        super.addInputIterator(inItr)
        currentLevel++
    }

    /*
        internal open val activeOutputLineLevels= ArrayList<Int>()
        internal open val activeInputLineLevels= ArrayList<Int>()
        internal open var activeOutputIteratorLevel: Int= -1
        internal open var activeInputIteratorLevel: Int= -1
    // */
/*
    override fun getNextInputIterator(out: O): Boolean {
        val inItr= getInputIterator(out)
        return if(inItr != null && inItr.hasNext()){
            addInputIterator(inItr)
            currentLevel++
//            activeInputLines.add(inItr)
//            activeInputIterator= inItr
            true
        } else false
    }
// */

    override fun changeLastActiveInputIterator(currentActiveInputIterator: Iterator<I>) {
        super.changeLastActiveInputIterator(currentActiveInputIterator)
        currentLevel--
    }

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
 */