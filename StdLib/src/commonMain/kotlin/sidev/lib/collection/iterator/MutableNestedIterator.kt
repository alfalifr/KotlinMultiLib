package sidev.lib.collection.iterator

import sidev.lib.annotation.Interface
import sidev.lib.universal.structure.collection.iterator.NestedIterator


@Interface
interface MutableNestedIterator<I, O>: NestedIterator<I, O>, MutableIterator<O>