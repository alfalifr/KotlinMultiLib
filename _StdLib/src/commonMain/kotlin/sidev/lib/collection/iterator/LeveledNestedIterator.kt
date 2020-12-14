package sidev.lib.collection.iterator

import sidev.lib.structure.data.value.LeveledValue

/** [NestedIterator] yg disertai dg level hirarki untuk setiap next. */
interface LeveledNestedIterator<I, O>: NestedIterator<LeveledValue<I>, LeveledValue<O>>, LeveledIterator<O> {
    /** Mengambil level untuk [outputIterator] yg dihasilkan dari [fromInput] dg nilai default level [inputLevel]. */
    fun getOutputIteratorLevel(outputIterator: Iterator<O>, fromInput: I, inputLevel: Int): Int
    fun getInputIteratorLevel(inputIterator: Iterator<I>, fromOutput: O, outputLevel: Int): Int

    /** Mengambil iterator yg berisi nilai [O] tanpa level. */
    fun getOutputValueIterator(nowInput: I): Iterator<O>?
    fun getInputValueIterator(nowOutput: O): Iterator<I>?
}