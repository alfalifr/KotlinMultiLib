package sidev.lib.collection

import sidev.lib.structure.data.MapEntry
import sidev.lib.structure.data.MutableMapEntry


val Map<*, *>.indices: IntRange
    get()= 0 until size

fun <A, B> Array<out Pair<A, B>>.asMap(): Map<A, B> = mapOf(*this)
fun <A, B> List<Pair<A, B>>.asMap(): Map<A, B> = mapOf(*toTypedArray())

fun <A, B> Array<out Pair<A, B>>.asMutableMap(): MutableMap<A, B> = mutableMapOf(*this)
fun <A, B> List<Pair<A, B>>.asMutableMap(): MutableMap<A, B> = mutableMapOf(*toTypedArray())

fun <K, V> Pair<K, V>.toMutableMapEntry(): MutableMap.MutableEntry<K, V> = MutableMapEntry(first, second)
fun <K, V> Pair<K, V>.toMapEntry(): Map.Entry<K, V> = MapEntry(first, second)


fun <K, V> MutableMap<K, V>.add(pair: Pair<K, V>){
    this[pair.first]= pair.second
}

fun <K, V> Collection<V>.asMapWithKeys(keys: List<K>): Map<K, V>{
    if(keys.size < size)
        throw IllegalArgumentException("""Ukuran keys: ${keys.size} krg dari ukuran `this` Collection: $size.""")

    val map= mutableMapOf<K, V>()
    for((i, value) in this.withIndex()){
        map += keys[i] to value
    }
    return map
}

fun <K, V> Collection<K>.asMapWithValues(values: List<V>): Map<K, V>{
    if(values.size < size)
        throw IllegalArgumentException("""Ukuran values: ${values.size} krg dari ukuran `this` Collection: $size.""")

    val map= mutableMapOf<K, V>()
    for((i, key) in this.withIndex()){
        map += key to values[i]
    }
    return map
}


/**
 * Menghapus first occurrence
 */
fun <K, V> MutableMap<K, V>.removeValue(value: V): Boolean {
    for(entry in this.entries)
        if(entry.value == value){
            this.remove(entry.key)
            return true
        }
    return false
}

/**
 * Menghapus semua occurrence
 */
fun <K, V> MutableMap<K, V>.removeAllValue(value: V): Boolean {
    var res= false
    for(entry in this.entries)
        if(entry.value == value){
            this.remove(entry.key)
            res= true
        }
    return res
}


fun <K, V> Map<K, V>.find(predicate: (Map.Entry<K, V>) -> Boolean): Map.Entry<K, V>? {
    for(e in this)
        if(predicate(e))
            return e
    return null
}

fun <K, V> Map<K, V>.findIndexed(predicate: (index: Int, Map.Entry<K, V>) -> Boolean): Map.Entry<K, V>? {
    for((i, e) in this.iterator().withIndex())
        if(predicate(i, e))
            return e
    return null
}


fun <K, V> Map<K, V>.findLast(predicate: (Map.Entry<K, V>) -> Boolean): Map.Entry<K, V>? {
    var res: Map.Entry<K, V>?= null
    for(e in this)
        if(predicate(e))
            res= e
    return res
}

fun <K, V> Map<K, V>.findLastIndexed(predicate: (index: Int, Map.Entry<K, V>) -> Boolean): Map.Entry<K, V>? {
    var res: Map.Entry<K, V>?= null
    for((i, e) in this.iterator().withIndex())
        if(predicate(i, e))
            res= e
    return res
}


fun <K, V> Map<K, V>.joinToString(
    separator: CharSequence = ", ", prefix: CharSequence = "", postfix: CharSequence = "",
    limit: Int = -1, truncated: CharSequence = "...",
    transform: ((Map.Entry<K, V>) -> CharSequence)? = null
): String = entries.joinToString(separator, prefix, postfix, limit, truncated, transform)

fun <K, V, A : Appendable> Map<K, V>.joinTo(
    buffer: A, separator: CharSequence = ", ", prefix: CharSequence = "", postfix: CharSequence = "",
    limit: Int = -1, truncated: CharSequence = "...",
    transform: ((Map.Entry<K, V>) -> CharSequence)? = null
): A = entries.joinTo(buffer, separator, prefix, postfix, limit, truncated, transform)