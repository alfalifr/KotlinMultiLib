package sidev.lib.collection.common

import sidev.lib.`val`.SuppressLiteral


fun <T> commonIterableOf(vararg element: T): CommonIterable<T>
        = object : CommonIterable<T> {
    override fun iterator(): Iterator<T> = element.iterator()
}

fun <T> Sequence<T>.toCommonIterable(): CommonIterable<T>
        = object :
    CommonIterable<T> {
    override fun iterator(): Iterator<T> = this@toCommonIterable.iterator()
}

fun <T> Iterable<T>.toCommonIterable(): CommonIterable<T>
        = object : CommonIterable<T> {
    override fun iterator(): Iterator<T> = this@toCommonIterable.iterator()
}

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Any.toCommonIterable(): CommonIterable<T>?{
    return when(this){
        is Iterable<*> -> CommonIterableImpl_Iterable(this as Iterable<T>)
        is Sequence<*> -> CommonIterableImpl_Sequence(this as Sequence<T>)
        else -> null
    }
}


/**
 * Merubah [CommonIterable] menjadi [CommonIndexedList].
 * Knp kok gak bisa jadi [CommonList]? Karena [CommonIterable] sudah pasti key-nya Int.
 */
fun <V> CommonIterable<V>.toCommonIndexedList(): CommonIndexedList<V> = when(this){
    is CommonIndexedList<*> -> this as CommonIndexedList<V>
    else -> (this as Iterable<V>).toList().toCommonIndexedList()!!
}
/**
 * Merubah [CommonIterable] menjadi [CommonIndexedMutableList].
 * Knp kok gak bisa jadi [CommonMutableList]? Karena [CommonIterable] sudah pasti key-nya Int.
 */
fun <V> CommonIterable<V>.toCommonIndexedMutableList(): CommonIndexedMutableList<V> = when(this){
    is CommonIndexedMutableList<*> -> this as CommonIndexedMutableList<V>
    else -> (this as Iterable<V>).toMutableList().toCommonIndexedMutableList()!!
}


operator fun <V> CommonIterable<V>.plus(other: CommonIterable<V>): CommonIterable<V>
        = ((this as Iterable<V>) + (other as Iterable<V>)).toCommonIterable()

fun <V> CommonIterable<V>.asSequence(): Sequence<V> = this