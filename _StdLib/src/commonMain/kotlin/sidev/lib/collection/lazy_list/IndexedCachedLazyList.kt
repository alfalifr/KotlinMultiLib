package sidev.lib.collection.lazy_list

/** [CachedLazyList] yg key-nya berupa [Int], sehingga [addValues] dapat dilakukan tanpa key. */
interface IndexedCachedLazyList<T> : CachedLazyList<Int, T>, List<T> {
//    fun addValues(seq: Sequence<T>): Boolean
    //fun addLazily(sequence: Sequence<T>): Boolean
    //operator fun plusAssign(sequence: Sequence<T>){ addLazily(sequence) }
}