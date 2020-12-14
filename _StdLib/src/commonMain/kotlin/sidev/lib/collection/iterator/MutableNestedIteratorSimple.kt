package sidev.lib.collection.iterator

import sidev.lib.annotation.Interface


@Interface
interface MutableNestedIteratorSimple<I>: MutableNestedIterator<I, I>, NestedIteratorSimple<I>