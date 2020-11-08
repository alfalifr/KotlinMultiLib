package sidev.lib.collection.iterator

/*
/** [NestedIterator] yg disertai dg level hirarki untuk setiap next. */
interface LeveledNestedIterator_2<I, O>: NestedIterator<I, O>, LeveledIterator_2<O> {
    /** Mengambil level untuk [outputIterator] yg dihasilkan dari [fromInput] dg nilai default level [level]. */
    fun getOutputIteratorLevel(outputIterator: Iterator<O>, fromInput: I, inputLevel: Int): Int
    fun getInputIteratorLevel(inputIterator: Iterator<I>, fromOutput: O, outputLevel: Int): Int

    override fun getOutputIterator(nowInput: I): Iterator<O>?
    override fun getInputIterator(nowOutput: O): Iterator<I>?
}
 */