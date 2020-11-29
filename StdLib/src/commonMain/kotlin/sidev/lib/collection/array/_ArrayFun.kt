package sidev.lib.collection.array

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.annotation.Unsafe
import sidev.lib.collection.toList
import sidev.lib.progression.asEndExclusive
import kotlin.jvm.JvmOverloads

/**
 * Fungsi yang melakukan copy array scr native.
 *
 * [length] adalah banyaknya elemen yg akan di-copy dari [src] yang dihitung dari index [srcStart]
 * menuju [dest] pada index yg dimulai dari [destStart].
 */
expect fun <T> arrayCopy(
    src: Array<T>, srcStart: Int,
    dest: Array<T>, destStart: Int,
    length: Int
)


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


/**
 * Merapatkan elemen `this.extension` Array dari index [start] hingga [end]
 * dg cara menghapus elemen null yg berada di tengah-tengahnya.
 * Fungsi ini tidak merubah panjang dari array.
 * Fungsi ini mengembalikan `true` jika terjadi perpindahan tempat pada elemen yg dikarenakan adanya `null`.
 */
@JvmOverloads
fun <T> Array<T>.trimNulls(start: Int= 0, end: Int= size): Boolean{
    var diff= 0
    var bool= false
    var i= start
    while(i < end){
        if(this[i] == null){
            for(u in i+1 until end){
                diff++
                if(this[u] != null){
                    i= u -1
                    break
                }
            }
        } else if(diff > 0){
            this[i -diff]= this[i]
            bool= true
        }
        i++
    }
    return bool
}

/**
 * Menghasilkan array baru yg isinya sama dg `this.extension` Array dg panjang [size].
 */
inline fun <reified T> Array<T>.trimToSize(size: Int): Array<T> = Array(size){ this[it] }

/**
 * Mengambil sub-array dari `this.extension` Array dimulai dari index [range.first] (inklusif) sampai [range.last] (eksklusif).
 */
operator fun <T> Array<T>.get(range: IntRange): Array<T> = sliceArray(range.asEndExclusive())
/*
{
    if(range.step < 0)
        throw IllegalArgumentException("Progression dari range harus positif, progression skrg= ${range.step}")
    return Array(range.last -range.first +1){ this[it +range.first] }
}
 */


fun <T> Array<T>.indexOfWhere(start: Int = 0, predicate: (T) -> Boolean): Int {
    for(i in start until size)
        if(predicate(this[i]))
            return i
    return -1
}


fun <T> Array<T>.forEach(start: Int= 0, end: Int= size, block: (T) -> Unit) {
    for(i in start until end)
        block(this[i])
}

fun <T> Array<T>.forEachIndexed(start: Int= 0, end: Int= size, block: (i: Int, T) -> Unit) {
    for(i in start until end)
        block(i, this[i])
}