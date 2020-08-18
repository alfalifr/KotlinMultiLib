package sidev.lib.collection.lazy_list

/** [MutableCachedLazyList] yg key-nya berupa [Int], sehingga [addValueIterator] dapat dilakukan tanpa key. */
interface MutableIndexedCachedLazyList<T> : MutableCachedLazyList<Int, T> {
    fun addValueIterator(itr: Iterator<T>): Boolean
}