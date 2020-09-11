package sidev.lib.collection.common

//import sidev.lib.`val`.SuppressLiteral

/**
 * Struktur data yg menunjukan semua jenis tipe yg dapat di-iterasi dalam Kotlin, kecuali [Array].
 * Untuk kasus [Array], interface ini mengakomodasi dg penggunaan [ArrayIterable].
 */
interface CommonIterable<out T>: Iterable<T>, Sequence<T>, ArrayIterable<T> {
    override fun iterator(): Iterator<T>
}

internal class CommonIterableImpl_Iterable<out T>(private val iterable: Iterable<T>): CommonIterable<T> {
    override fun iterator(): Iterator<T> = iterable.iterator()
}
internal class CommonIterableImpl_Sequence<out T>(private val sequence: Sequence<T>): CommonIterable<T> {
    override fun iterator(): Iterator<T> = sequence.iterator()
}
