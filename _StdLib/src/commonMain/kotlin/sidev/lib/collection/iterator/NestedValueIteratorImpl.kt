package sidev.lib.collection.iterator

open class NestedValueIteratorImpl<I, O>(override val leveledIterator: LeveledNestedIterator<I, O>)
    : ValueIteratorImpl<O>(leveledIterator), NestedValueIterator<I, O>