package sidev.lib.collection.iterator


fun <I, O> Iterator<I>.toOtherIterator(mapping: (I) -> O): Iterator<O>
        = object: Iterator<O>{
    override fun hasNext(): Boolean = this@toOtherIterator.hasNext()
    override fun next(): O = mapping(this@toOtherIterator.next())
}

fun <T> Iterator<IndexedValue<T>>.toPairIterator(): Iterator<Pair<Int, T>>
        = object : Iterator<Pair<Int, T>>{
    override fun hasNext(): Boolean = this@toPairIterator.hasNext()
    override fun next(): Pair<Int, T>{
        val next= this@toPairIterator.next()
        return Pair(next.index, next.value)
    }
}

/** Menjadikan nilai pada `this.extension` [Iterator] sbg `value` dg `key` yg dipetakan oleh [func]. */
fun <K, V> Iterator<V>.withKey(func: (value: V) -> K): Iterator<Pair<K, V>>
        = object : Iterator<Pair<K, V>>{
    override fun hasNext(): Boolean = this@withKey.hasNext()

    override fun next(): Pair<K, V>{
        val next= this@withKey.next()
        val key= func(next)
        return Pair(key, next)
    }
}
/** Menjadikan nilai pada `this.extension` [Iterator] sbg `key` dg `value` yg dipetakan oleh [func]. */
fun <K, V> Iterator<K>.withValue(func: (key: K) -> V): Iterator<Pair<K, V>>
        = object : Iterator<Pair<K, V>>{
    override fun hasNext(): Boolean = this@withValue.hasNext()

    override fun next(): Pair<K, V>{
        val next= this@withValue.next()
        val value= func(next)
        return Pair(next, value)
    }
}

/** Menjadikan nilai pada `this.extension` [Iterator] sbg `value` dg `key` yg dipetakan oleh [func]. */
fun <K, V> Iterator<V>.withKeyIndexed(func: (index: Int, value: V) -> K): Iterator<Pair<K, V>>
        = object : Iterator<Pair<K, V>>{
    private var index= 0
    override fun hasNext(): Boolean = this@withKeyIndexed.hasNext()

    override fun next(): Pair<K, V>{
        val next= this@withKeyIndexed.next()
        val key= func(index++, next)
        return Pair(key, next)
    }
}
/** Menjadikan nilai pada `this.extension` [Iterator] sbg `key` dg `value` yg dipetakan oleh [func]. */
fun <K, V> Iterator<K>.withValueIndexed(func: (index: Int, key: K) -> V): Iterator<Pair<K, V>>
        = object : Iterator<Pair<K, V>>{
    private var index= 0
    override fun hasNext(): Boolean = this@withValueIndexed.hasNext()

    override fun next(): Pair<K, V>{
        val next= this@withValueIndexed.next()
        val value= func(index++, next)
        return Pair(next, value)
    }
}