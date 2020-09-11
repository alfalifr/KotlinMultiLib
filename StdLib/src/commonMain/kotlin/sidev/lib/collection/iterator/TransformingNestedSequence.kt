package sidev.lib.collection.iterator

interface TransformingNestedSequence<I, O, O2> : NestedIterator<I, O2> {
    val transformer: (oldOutput: O) -> O2
}