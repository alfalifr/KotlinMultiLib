package sidev.lib.collection.lazy_list


fun <T> cachedSequenceOf(vararg elements: T): CachedSequence<T> = CachedSequence(elements.iterator())
fun <K, V> lazaMapOf(vararg elements: Pair<K, V>): LazyHashMap<K, V> = LazyHashMap(elements.iterator())

fun <T> Sequence<T>.asCached(): CachedSequence<T> = CachedSequence(this)
fun <T> Iterator<T>.asCached(): CachedSequence<T> = CachedSequence(this)


/*
==============================
Operator Overriding
==============================
 */
operator fun <K, V> LazyHashMap<K, V>.plus(other: Sequence<Pair<K, V>>): LazyHashMap<K, V> {
    this.addIterator(other.iterator())
    return this
}
operator fun <K, V> LazyHashMap<K, V>.plus(other: Iterator<Pair<K, V>>): LazyHashMap<K, V> {
    this.addIterator(other)
    return this
}
operator fun <K, V> LazyHashMap<K, V>.plus(other: Iterable<Pair<K, V>>): LazyHashMap<K, V> {
    this.addIterator(other.iterator())
    return this
}


operator fun <T> CachedSequence<T>.plus(other: Sequence<T>): CachedSequence<T> {
    this.addValueIterator(other.iterator())
    return this
}
operator fun <T> CachedSequence<T>.plus(other: Iterator<T>): CachedSequence<T> {
    this.addValueIterator(other)
    return this
}
operator fun <T> CachedSequence<T>.plus(other: Iterable<T>): CachedSequence<T> {
    this.addValueIterator(other.iterator())
    return this
}