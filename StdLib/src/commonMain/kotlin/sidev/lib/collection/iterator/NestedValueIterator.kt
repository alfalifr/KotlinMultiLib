package sidev.lib.collection.iterator

import sidev.lib.structure.data.value.LeveledValue

/** [Iterator] yang mengubah [LeveledIterator] menjadi [Iterator] biasa tanpa level. */
interface NestedValueIterator<I, O>: NestedIterator<I, O>, ValueIterator<O> {
    override val leveledIterator: LeveledNestedIterator<I, O>
    /**
     * engubah [nowInput] yang dihasilkan dari [LeveledIterator]
     * menjadi `value` biasa tanpa level.
     */
    fun inputValue(nowInput: LeveledValue<I>): I = nowInput.value

    override fun getOutputIterator(nowInput: I): Iterator<O>? = leveledIterator.getOutputValueIterator(nowInput)
    override fun getInputIterator(nowOutput: O): Iterator<I>? = leveledIterator.getInputValueIterator(nowOutput)
}