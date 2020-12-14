package sidev.lib.collection.iterator

import sidev.lib.structure.data.value.LeveledValue

/** [Iterator] yang mengubah [LeveledIterator] menjadi [Iterator] biasa tanpa level. */
interface ValueIterator<T>: Iterator<T> {
    val leveledIterator: LeveledIterator<T>
    /**
     * Fungsi yang mengubah [nowOutput] yang dihasilkan dari [next] dari [LeveledIterator]
     * menjadi `value` biasa tanpa level.
     */
    fun outputValue(nowOutput: LeveledValue<T>): T = nowOutput.value

    override fun hasNext(): Boolean = leveledIterator.hasNext()
    override fun next(): T = outputValue(leveledIterator.next())
}