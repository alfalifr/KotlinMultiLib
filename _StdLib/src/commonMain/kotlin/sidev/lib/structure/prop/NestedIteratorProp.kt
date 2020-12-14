package sidev.lib.structure.prop

import sidev.lib.collection.iterator.NestedIterator

interface NestedIteratorProp<I, O>: IteratorProp<O> {
    override val iterator: NestedIterator<I, O>
}