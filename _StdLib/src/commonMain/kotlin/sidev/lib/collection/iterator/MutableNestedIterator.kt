package sidev.lib.collection.iterator

import sidev.lib.annotation.Interface


@Interface
interface MutableNestedIterator<I, O>: NestedIterator<I, O>, MutableIterator<O>