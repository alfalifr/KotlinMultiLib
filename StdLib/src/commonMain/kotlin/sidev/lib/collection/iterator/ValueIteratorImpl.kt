package sidev.lib.collection.iterator

open class ValueIteratorImpl<T>(override val leveledIterator: LeveledIterator<T>): ValueIterator<T>