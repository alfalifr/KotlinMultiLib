package sidev.lib.collection.sequence

import sidev.lib.collection.iterator.LeveledNestedIterator
import sidev.lib.structure.data.value.LeveledValue

//import sidev.lib.collection.iterator.LeveledNestedIterator_2

interface LeveledNestedSequence<T>: NestedSequence<LeveledValue<T>>, LeveledSequence<T> {
    val currentLevel: Int // Tidak masuk akal jika currentLevel dimasukan ke sequence, bkn ke iterator.
    override fun iterator(): LeveledNestedIterator<*, T>
}