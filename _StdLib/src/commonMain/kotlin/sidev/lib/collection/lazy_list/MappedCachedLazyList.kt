package sidev.lib.collection.lazy_list

import sidev.lib.console.prine


/**
 * [LazyList] yg menyimpan data dari [Iterator.next].
 */
interface MappedCachedLazyList<K, V> : CachedLazyList<K, V>, Map<K, V>