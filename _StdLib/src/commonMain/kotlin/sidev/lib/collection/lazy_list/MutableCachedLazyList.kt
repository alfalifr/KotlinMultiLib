package sidev.lib.collection.lazy_list

interface MutableCachedLazyList<K, V>: CachedLazyList<K, V>, MutableLazyList<Pair<K, V>> {
    //fun addLazily(sequence: Sequence<Pair<K, V>>): Boolean = addIterator(sequence.iterator())
    //operator fun plusAssign(sequence: Sequence<Pair<K, V>>){ addIterator(sequence.iterator()) }
//    fun addKeyIterator(itr: Iterator<K>): Boolean => Jika dipikir-pikir lagi, gak mungkin programmer cuma menambahkan key tanpa value.
}