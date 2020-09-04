package sidev.lib.collection.lazy_list

/** [MutableCachedLazyList] yg key-nya berupa [Int], sehingga [addValues] dapat dilakukan tanpa key. */
interface MutableIndexedCachedLazyList<T> : MutableCachedLazyList<Int, T> {
    fun addValues(itr: Iterator<T>): Boolean
//    fun addValues(seq: Sequence<T>): Boolean
    //fun addLazily(sequence: Sequence<T>): Boolean
    //operator fun plusAssign(sequence: Sequence<T>){ addLazily(sequence) }
}