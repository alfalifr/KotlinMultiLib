package sidev.lib.collection.array

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.annotation.Unsafe

/**
 * Fungsi array builder yg dapat diakses dari Java.
 * Fungsi ini tidak mengembalikan array dg tipe yg bkn reified, karena array yg dikembalikan adalah generic.
 * Fungsi ini bertujuan untuk kenyamanan saat membuat array sehingga tidak perlu
 * menggunakan syntax `new Class[]{}`.
 */
@Unsafe("Tipe array yg dikembalikan adalah generic, bkn reified")
@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> arrayOf(vararg elements: T): Array<T> = elements as Array<T>

/*
Tidak Jadi karena dapat menyebabkan CCE pada Java.
/**
 * Fungsi yg sama dg fungsi bawaan dari dari Kotlin ([arrayOfNulls]).
 * TUjuan dari fungsi ini adalah agar dapat diakses dari Java.
 */
@Unsafe("Tipe array yg dikembalikan adalah generic, bkn reified")
@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> arrayOfNulls(size: Int): Array<T?> = Array<Any?>(size){null} as Array<T?>
 */


/*
=============================
Primitive Array -
Bagian ini berisi salinan fungsi yg ada pada library standar Kotlin.
Tujuan dari bagian ini adalah agar dapat diakses dari Java.
=============================
 */

/**
 * Returns an array containing the specified [Double] numbers.
 */
fun doubleArrayOf(vararg elements: Double): DoubleArray = elements

/**
 * Returns an array containing the specified [Float] numbers.
 */
fun floatArrayOf(vararg elements: Float): FloatArray = elements

/**
 * Returns an array containing the specified [Long] numbers.
 */
fun longArrayOf(vararg elements: Long): LongArray = elements

/**
 * Returns an array containing the specified [Int] numbers.
 */
fun intArrayOf(vararg elements: Int): IntArray = elements

/**
 * Returns an array containing the specified characters.
 */
fun charArrayOf(vararg elements: Char): CharArray = elements

/**
 * Returns an array containing the specified [Short] numbers.
 */
fun shortArrayOf(vararg elements: Short): ShortArray = elements

/**
 * Returns an array containing the specified [Byte] numbers.
 */
fun byteArrayOf(vararg elements: Byte): ByteArray = elements

/**
 * Returns an array containing the specified boolean values.
 */
fun booleanArrayOf(vararg elements: Boolean): BooleanArray = elements