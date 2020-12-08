package sidev.lib.structure.data

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.collection.asList
import sidev.lib.collection.asMutableList
import sidev.lib.collection.common.ArrayWrapper
import sidev.lib.collection.common.MutableArrayWrapper


fun <T> Array<T>.asArrangeable(): Arrangeable<T> = ArrayArrangeable(this)
@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Collection<T>.asArrangeable(): Arrangeable<T> =
    if(this is MutableArrayWrapper<*>) this as MutableArrayWrapper<T>
    else ListArrangeable(asMutableList())

fun <T> Array<T>.asIndexable(): FiniteIndexable<T> = ArrayIndexable(this)
@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Collection<T>.asIndexable(): FiniteIndexable<T> =
    if(this is ArrayWrapper<*>) this as ArrayWrapper<T>
    else ListIndexable(asList())