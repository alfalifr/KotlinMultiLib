package sidev.lib.collection

import sidev.lib.structure.data.MapEntry
import sidev.lib.structure.data.MutableMapEntry


val Map<*, *>.indices: IntRange
    get()= 0 until size

fun <A, B> Array<out Pair<A, B>>.asMap(): Map<A, B> = mapOf(*this)
fun <A, B> List<Pair<A, B>>.asMap(): Map<A, B> = mapOf(*toTypedArray())

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