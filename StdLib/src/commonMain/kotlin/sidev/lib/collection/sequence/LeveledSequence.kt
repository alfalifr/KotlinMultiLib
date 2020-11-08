package sidev.lib.collection.sequence

import sidev.lib.collection.iterator.LeveledIterator
import sidev.lib.structure.data.value.LeveledValue

interface LeveledSequence<T>: Sequence<LeveledValue<T>>{
    override fun iterator(): LeveledIterator<T>
}