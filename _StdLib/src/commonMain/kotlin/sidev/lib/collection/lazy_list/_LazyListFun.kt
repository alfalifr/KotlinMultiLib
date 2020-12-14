package sidev.lib.collection.lazy_list

import sidev.lib.collection.toMapEntry
import sidev.lib.collection.toMutableMapEntry


fun <T> cachedSequenceOf(vararg elements: T): CachedSequence<T> = CachedSequence(elements.iterator())
fun <K, V> lazyMapOf(vararg elements: Pair<K, V>): LazyHashMap<K, V> = LazyHashMap(elements.iterator())

fun <T> Sequence<T>.asCached(): CachedSequence<T> = CachedSequence(this)
fun <T> Iterator<T>.asCached(): CachedSequence<T> = CachedSequence(this)


fun <T> IndexedCachedLazyList<T>.first(): T{
    return if(!isEmpty()) this[0]
    else throw IndexOutOfBoundsException("IndexedCachedLazyList: ${this::class.simpleName} kosong.")
}
fun <T> IndexedCachedLazyList<T>.last(): T{
    return if(!isEmpty()) {
        if(iteratorHasNext()){
            var res: T?= null
            var i= lastIndex +1
            while(iteratorHasNext()){
                res= this[i++]
            }
            res!!
        } else{
            this[lastIndex]
        }
    }
    else throw IndexOutOfBoundsException("IndexedCachedLazyList: ${this::class.simpleName} kosong.")
}

fun <K, V> MappedCachedLazyList<K, V>.iterator(): Iterator<Map.Entry<K, V>> = entries.iterator()
fun <K, V> MutableMappedCachedLazyList<K, V>.iterator(): MutableIterator<MutableMap.MutableEntry<K, V>> = entries.iterator()

fun <K, V> MappedCachedLazyList<K, V>.first(): Map.Entry<K, V>{
    return if(!isEmpty()) {
        if(size > 0) entries.elementAt(0)
        else getNext()!!.toMapEntry()
    }
    else throw IndexOutOfBoundsException("MappedCachedLazyList: ${this::class.simpleName} kosong.")
}
fun <K, V> MappedCachedLazyList<K, V>.last(): Map.Entry<K, V>{
    return if(!isEmpty()) {
        if(iteratorHasNext()){
            var res: Pair<K, V>?= null
            while(iteratorHasNext()){
                res= getNext()!!
            }
            res!!.toMapEntry()
        } else{
            entries.last()
        }
    }
    else throw IndexOutOfBoundsException("CachedSequence: ${this::class.simpleName} kosong.")
}

/*
==============================
Operator Overriding
==============================
 */

operator fun <K, V> LazyHashMap<K, V>.rangeTo(other: Sequence<Pair<K, V>>): LazyHashMap<K, V> {
    addIterator(other.iterator())
    return this
}
operator fun <K, V> LazyHashMap<K, V>.rangeTo(other: Iterator<Pair<K, V>>): LazyHashMap<K, V> {
    this.addIterator(other)
    return this
}
operator fun <K, V> LazyHashMap<K, V>.rangeTo(other: Iterable<Pair<K, V>>): LazyHashMap<K, V> {
    this.addIterator(other.iterator())
    return this
}


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




operator fun <T> CachedSequence<T>.rangeTo(other: Sequence<T>): CachedSequence<T> {
    this.addValues(other.iterator())
    return this
}
operator fun <T> CachedSequence<T>.rangeTo(other: Iterator<T>): CachedSequence<T> {
    this.addValues(other)
    return this
}
operator fun <T> CachedSequence<T>.rangeTo(other: Iterable<T>): CachedSequence<T> {
    this.addValues(other.iterator())
    return this
}



operator fun <T> CachedSequence<T>.plus(other: Sequence<T>): CachedSequence<T> {
    this.addValues(other.iterator())
    return this
}
operator fun <T> CachedSequence<T>.plus(other: Iterator<T>): CachedSequence<T> {
    this.addValues(other)
    return this
}
operator fun <T> CachedSequence<T>.plus(other: Iterable<T>): CachedSequence<T> {
    this.addValues(other.iterator())
    return this
}