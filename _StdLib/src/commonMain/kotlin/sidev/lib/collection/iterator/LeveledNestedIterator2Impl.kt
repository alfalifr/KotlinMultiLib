package sidev.lib.collection.iterator
/*
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.annotation.Unused
import sidev.lib.console.prine

//TODO LeveledNestedIteratorImpl [currentLevel] msh tidak sesuai harapan.
abstract class LeveledNestedIterator2Impl<I, O>(startInputIterator: Iterator<I>?)
    : NestedIteratorImpl<I, O>(startInputIterator), LeveledNestedIterator_2<I, O>{
    constructor(startInputIterable: Iterable<I>): this(startInputIterable.iterator())
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    constructor(start: I?): this((start as? Iterable<I>)?.iterator()){
        this.start= start
    }

    /**
     * Field ini dihitung berdasarkan ukuran [activeInputLines] terkini.
     */
    override var currentLevel: Int= -1
//        get()= activeInputLines.size
//        protected set
///*
        protected set(v){
            field= v
            prine("LeveledNestedIteratorImpl currentLevel= $v")
        }
// */

    override fun addInputIterator(inItr: Iterator<I>){
        super.addInputIterator(inItr)
        currentLevel++
    }

    override fun onInputIteratorExpired() {
        currentLevel--
    }

    override fun changeLastActiveInputIterator(currentActiveInputIterator: Iterator<I>) {
        super.changeLastActiveInputIterator(currentActiveInputIterator)
        currentLevel--
    }

/*
    override fun getNextInputIterator(out: O): Boolean {
        prine("LeveledNestedIteratorImpl getNextInputIterator() currentLevel= $currentLevel")
        val inItr= getInputIterator(out)
        prine("inItr != null && inItr.hasNext()= ${inItr != null && inItr.hasNext()}")
        return if(inItr != null && inItr.hasNext()){
            addInputIterator(inItr)
            currentLevel++
//            activeInputLines.add(inItr)
//            activeInputIterator= inItr
            true
        } else false
    }

// */

    @Unused("Hanya sbg formalitas, namun memiliki fungsi yg semestinya")
    override fun getOutputIteratorLevel(outputIterator: Iterator<O>, fromInput: I, inputLevel: Int): Int = inputLevel
    @Unused("Hanya sbg formalitas, namun memiliki fungsi yg semestinya")
    override fun getInputIteratorLevel(inputIterator: Iterator<I>, fromOutput: O, outputLevel: Int): Int = outputLevel +1
}
 */