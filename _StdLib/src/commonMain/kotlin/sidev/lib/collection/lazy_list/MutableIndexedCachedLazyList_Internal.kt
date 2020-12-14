package sidev.lib.collection.lazy_list


/** [MutableCachedLazyList] yg key-nya berupa [Int]. */
internal interface MutableIndexedCachedLazyList_Internal<T>
    : MutableIndexedCachedLazyList<T>, MutableCachedLazyList_Internal<Int, T>