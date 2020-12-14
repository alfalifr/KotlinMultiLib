@file:JvmName("_ConvertFunJvm")
package sidev.lib.collection

import java.util.stream.Stream
import kotlin.streams.toList as ktToList

/*
===============
Convert
===============
 */
fun <I, O> Stream<I>.toListOf(func: (I) -> O): List<O> = map { func(it) }.ktToList()
fun <T> Stream<T>.toList(): List<T> = ktToList()