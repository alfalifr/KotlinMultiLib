package sidev.lib.collection.lazy_list

import sidev.lib.collection.iterator.emptyIterator
import sidev.lib.collection.iterator.emptyNestedIterator

internal interface MutableCachedLazyList_Internal<K, V>
    : MutableCachedLazyList<K, V>, MutableLazyList_Internal<Pair<K, V>>