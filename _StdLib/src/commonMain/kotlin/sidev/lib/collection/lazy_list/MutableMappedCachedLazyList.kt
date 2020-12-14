package sidev.lib.collection.lazy_list

import sidev.lib.console.prine


/**
 * [LazyList] yg menyimpan data dari [Iterator.next].
 */
interface MutableMappedCachedLazyList<K, V> : MappedCachedLazyList<K, V>, MutableMap<K, V>, MutableCachedLazyList<K, V>