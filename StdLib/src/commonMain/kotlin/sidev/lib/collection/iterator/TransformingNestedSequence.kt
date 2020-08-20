package sidev.lib.collection.iterator

import sidev.lib.universal.structure.collection.iterator.NestedIterator

interface TransformingNestedSequence<I, O, O2> : NestedIterator<I, O2>{
    val transformer: (oldOutput: O) -> O2
}