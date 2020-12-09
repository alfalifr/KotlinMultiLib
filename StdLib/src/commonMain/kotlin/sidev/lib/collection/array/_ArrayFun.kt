package sidev.lib.collection.array

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.annotation.Unsafe
import sidev.lib.progression.asEndExclusive
import sidev.lib.structure.data.value.Val
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



fun <T> Array<T>.forEach(
    start: Int= 0, end: Int= size,
    reversed: Boolean = false,
    breakRef: Val<Boolean>? = null,
    contRef: Val<Boolean>? = null,
    block: (T) -> Unit
) {
    val range= if(!reversed) start until end
    else {
        (if(start > 0) start else size-1) downTo (if(end < size) end+1 else 0)
    }
    for(i in range){
        if(breakRef?.value == true)
            break
        if(contRef?.value == true)
            continue
        block(this[i])
    }
}

fun <T> Array<T>.forEachIndexed(
    start: Int= 0, end: Int= size,
    reversed: Boolean = false,
    breakRef: Val<Boolean>? = null,
    contRef: Val<Boolean>? = null,
    block: (i: Int, T) -> Unit
) {
    val range= if(!reversed) start until end
    else {
        (if(start > 0) start else size-1) downTo (if(end < size) end+1 else 0)
    }
    for(i in range){
        if(breakRef?.value == true)
            break
        if(contRef?.value == true)
            continue
        block(i, this[i])
    }
}


fun <T> Array<T>.forEach(
    start: Int= 0, end: Int= size,
    reversed: Boolean= false,
    block: (T) -> Unit
) {
    val range= if(!reversed) start until end
    else {
        (if(start > 0) start else size-1) downTo (if(end < size) end+1 else 0)
    }
    for(i in range)
        block(this[i])
}

fun <T> Array<T>.forEachIndexed(
    start: Int= 0, end: Int= size,
    reversed: Boolean= false,
    block: (i: Int, T) -> Unit
) {
    val range= if(!reversed) start until end
    else {
        (if(start > 0) start else size-1) downTo (if(end < size) end+1 else 0)
    }
    for(i in range)
        block(i, this[i])
}


fun <T> Array<T>.findIndexed(predicate: (IndexedValue<T>) -> Boolean): IndexedValue<T>?{
    for(vals in this.withIndex()){
        if(predicate(vals))
            return vals
    }
    return null
}

fun <T> Array<T>.findLastIndexed(predicate: (IndexedValue<T>) -> Boolean): IndexedValue<T>?{
    var foundElement: IndexedValue<T>?= null
    for(vals in this.withIndex()){
        if(predicate(vals))
            foundElement= vals
    }
    return foundElement
}


fun <T> Array<T>.filterIndexed(predicate: (IndexedValue<T>) -> Boolean): List<IndexedValue<T>>{
    val res= ArrayList<IndexedValue<T>>()
    for(vals in this.withIndex()){
        if(predicate(vals)){
            res += vals
        }
    }
    return res
}


fun <T> Array<T>.filterContainedIn(array: Array<T>): List<T> {
    val out= ArrayList<T>()
    for(e in this)
        if(e in array)
            out.add(e)
    return out
}


//operator fun <T> Array<T>.get(range: IntRange): Array<T> = stdSubArray(range)


inline fun <reified T> Array<T>.copy(): Array<T> = copyOf()
inline fun <reified T> Array<T>.copy(reversed: Boolean= false): Array<T> {
    val arr= copyOf()
    if(reversed)
        arr.reverse()
    return arr
}
inline fun <reified T> Array<T>.copy(from: Int= 0, until: Int= size): Array<T> = Array(until - from){ this[it +from] }
inline fun <reified T> Array<T>.copy(from: Int= 0, until: Int= size, reversed: Boolean= false): Array<T> = when {
    from == 0 && until == size -> copy(reversed)
    !reversed -> Array(until - from){ this[it +from] }
    else -> {
        val lastIndex= until -1
        Array(until - from){ this[lastIndex -it] }
    }
}



fun ByteArray.copy(): ByteArray = copyOf()
fun ByteArray.copy(from: Int= 0, until: Int= size, reversed: Boolean= false): ByteArray = when {
    from == 0 && until == size -> copyOf()
    !reversed -> ByteArray(until - from){ this[it +from] }
    else -> {
        val lastIndex= until -1
        ByteArray(until - from){ this[lastIndex -it] }
    }
}

fun ShortArray.copy(): ShortArray = copyOf()
fun ShortArray.copy(from: Int= 0, until: Int= size, reversed: Boolean= false): ShortArray = when {
    from == 0 && until == size -> copyOf()
    !reversed -> ShortArray(until - from){ this[it +from] }
    else -> {
        val lastIndex= until -1
        ShortArray(until - from){ this[lastIndex -it] }
    }
}

fun IntArray.copy(): IntArray = copyOf()
fun IntArray.copy(from: Int= 0, until: Int= size, reversed: Boolean= false): IntArray = when {
    from == 0 && until == size -> copyOf()
    !reversed -> IntArray(until - from){ this[it +from] }
    else -> {
        val lastIndex= until -1
        IntArray(until - from){ this[lastIndex -it] }
    }
}

fun LongArray.copy(): LongArray = copyOf()
fun LongArray.copy(from: Int= 0, until: Int= size, reversed: Boolean= false): LongArray = when {
    from == 0 && until == size -> copyOf()
    !reversed -> LongArray(until - from){ this[it +from] }
    else -> {
        val lastIndex= until -1
        LongArray(until - from){ this[lastIndex -it] }
    }
}

fun FloatArray.copy(): FloatArray = copyOf()
fun FloatArray.copy(from: Int= 0, until: Int= size, reversed: Boolean= false): FloatArray = when {
    from == 0 && until == size -> copyOf()
    !reversed -> FloatArray(until - from){ this[it +from] }
    else -> {
        val lastIndex= until -1
        FloatArray(until - from){ this[lastIndex -it] }
    }
}

fun DoubleArray.copy(): DoubleArray = copyOf()
fun DoubleArray.copy(from: Int= 0, until: Int= size, reversed: Boolean= false): DoubleArray = when {
    from == 0 && until == size -> copyOf()
    !reversed -> DoubleArray(until - from){ this[it +from] }
    else -> {
        val lastIndex= until -1
        DoubleArray(until - from){ this[lastIndex -it] }
    }
}

fun CharArray.copy(): CharArray = copyOf()
fun CharArray.copy(from: Int= 0, until: Int= size, reversed: Boolean= false): CharArray = when {
    from == 0 && until == size -> copyOf()
    !reversed -> CharArray(until - from){ this[it +from] }
    else -> {
        val lastIndex= until -1
        CharArray(until - from){ this[lastIndex -it] }
    }
}

fun BooleanArray.copy(): BooleanArray = copyOf()
fun BooleanArray.copy(from: Int= 0, until: Int= size, reversed: Boolean= false): BooleanArray = when {
    from == 0 && until == size -> copyOf()
    !reversed -> BooleanArray(until - from){ this[it +from] }
    else -> {
        val lastIndex= until -1
        BooleanArray(until - from){ this[lastIndex -it] }
    }
}

fun BooleanArray.toIntArray(): IntArray = IntArray(size){ if(this[it]) 1 else 0 }
fun IntArray.toBooleanArray(): BooleanArray = BooleanArray(size){ this[it] > 0 }