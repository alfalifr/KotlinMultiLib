package sidev.lib.structure.data

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.collection.asList
import sidev.lib.collection.asMutableList
import sidev.lib.collection.common.ArrayWrapper
import sidev.lib.collection.common.MutableArrayWrapper

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Array<T>.asArrangeable(): ArrayArrangeable<T> = ArrayArrangeableImpl(this as Array<Any?>)
fun ByteArray.asArrangeable(): ArrayArrangeable<Byte> = ByteArrayArrangeable(this)
fun ShortArray.asArrangeable(): ArrayArrangeable<Short> = ShortArrayArrangeable(this)
fun IntArray.asArrangeable(): ArrayArrangeable<Int> = IntArrayArrangeable(this)
fun LongArray.asArrangeable(): ArrayArrangeable<Long> = LongArrayArrangeable(this)
fun FloatArray.asArrangeable(): ArrayArrangeable<Float> = FloatArrayArrangeable(this)
fun DoubleArray.asArrangeable(): ArrayArrangeable<Double> = DoubleArrayArrangeable(this)
fun CharArray.asArrangeable(): ArrayArrangeable<Char> = CharArrayArrangeable(this)
fun BooleanArray.asArrangeable(): ArrayArrangeable<Boolean> = BooleanArrayArrangeable(this)
@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Collection<T>.asArrangeable(): Arrangeable<T> =
    if(this is MutableArrayWrapper<*>) this as MutableArrayWrapper<T>
    else ListArrangeable(asMutableList())

fun <T> Array<T>.asIndexable(): FiniteIndexable<T> = asArrangeable() //ArrayIndexable(this)
@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Collection<T>.asIndexable(): FiniteIndexable<T> =
    if(this is ArrayWrapper<*>) this as ArrayWrapper<T>
    else ListIndexable(asList())