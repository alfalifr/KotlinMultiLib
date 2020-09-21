package sidev.lib.collection

import sidev.lib.check.notNull
import sidev.lib.structure.data.MapEntry
import kotlin.collections.toList as ktToList

/*
===============
Convert
===============
 */
fun <T> Array<T>.toArrayList(): ArrayList<T> =  this.toMutableList() as ArrayList<T>


inline fun <I, reified O> Array<I>.toArrayOfNotNul(func: (I) -> O?): Array<O> {
    val newList= mutableListOf<O>()
    for(inn in this){
        func(inn).notNull { newList.add(it) }
    }
    return newList.toTypedArray()
}
inline fun <I, reified O> Iterable<I>.toArrayOfNotNul(func: (I) -> O?): Array<O> {
    val newList= mutableListOf<O>()
    for(inn in this){
        func(inn).notNull { newList.add(it) }
    }
    return newList.toTypedArray()
}

inline fun <I, reified O> Array<I>.toArrayOf(func: (I) -> O): Array<O> = map(func).toTypedArray()
inline fun <I, reified O> Iterable<I>.toArrayOf(func: (I) -> O): Array<O> = map(func).toTypedArray()
inline fun <K, V, reified O> Map<K, V>.toArrayOf(func: (K, V) -> O): Array<O> = map{ func(it.key, it.value) }.toTypedArray()

fun <I, O> Iterable<I>.toListOf(func: (I) -> O): List<O> = map(func)
fun <I, O> Array<I>.toListOf(func: (I) -> O): List<O> = map(func)
fun <K, V, O> Map<K, V>.toListOf(func: (K, V) -> O): List<O> = map { func(it.key, it.value) }

fun <T> Iterable<T>.toList(): List<T> = ktToList()
fun <T> Array<T>.toList(): List<T> = ktToList()
fun <K, V> Map<K, V>.toList(): List<Map.Entry<K, V>> = map { it }