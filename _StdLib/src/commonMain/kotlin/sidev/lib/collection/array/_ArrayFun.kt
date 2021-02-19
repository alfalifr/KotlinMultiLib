@file:OptIn(ExperimentalStdlibApi::class)
package sidev.lib.collection.array

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.annotation.Unsafe
import sidev.lib.collection.common.ArrayWrapper
import sidev.lib.collection.shallowUnorderedContentEquals
import sidev.lib.exception.ClassCastExc
import sidev.lib.exception.IllegalAccessExc
import sidev.lib.progression.asEndExclusive
import sidev.lib.structure.data.value.Var
import sidev.lib.structure.prop.SizeProp
import kotlin.collections.contentEquals as ktContentEquals_
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
 * Fungsi yang melakukan copy array scr native.
 *
 * [length] adalah banyaknya elemen yg akan di-copy dari [src] yang dihitung dari index [srcStart]
 * menuju [dest] pada index yg dimulai dari [destStart].
 */
expect fun arrayCopyAll(
    src: Any, srcStart: Int,
    dest: Any, destStart: Int,
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
    breakRef: Var<Boolean>? = null,
    contRef: Var<Boolean>? = null,
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
    breakRef: Var<Boolean>? = null,
    contRef: Var<Boolean>? = null,
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



/**
 * Menghitung jumlah duplikat. Jml yang dikembalikan adalah jml duplikat,
 * tidak termasuk jml elemen unik yng pertama. Jadi jika elemen e berjumlah hanya 1 di
 * `this.extension` `Iterable`, maka jml duplikasinya aalah 0.
 */
fun <T, R> Array<T>.countDuplicationBy(selector: (T) -> R): Map<R, Int> {
    val map= mutableMapOf<R, Int>()
    for(e in this){
        val key= selector(e)
        map[key]= map[key]?.plus(1) ?: 0
    }
    return map
}
fun <T> Array<T>.countDuplication(): Map<T, Int> = countDuplicationBy { it }

/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik berdasarkan [selector]
 */
fun <T, R> Array<T>.isUniqueBy(selector: (T) -> R): Boolean {
    if(size < 2) return true
    val set= HashSet<R>()
    for(e in this){
        if(!set.add(selector(e)))
            return false
    }
    return true
}
/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik.
 */
fun <T> Array<T>.isUnique(): Boolean = isUniqueBy { it }



/**
 * Menghitung jumlah duplikat. Jml yang dikembalikan adalah jml duplikat,
 * tidak termasuk jml elemen unik yng pertama. Jadi jika elemen e berjumlah hanya 1 di
 * `this.extension` `Iterable`, maka jml duplikasinya aalah 0.
 */
fun <R> ByteArray.countDuplicationBy(selector: (Byte) -> R): Map<R, Int> {
    val map= mutableMapOf<R, Int>()
    for(e in this){
        val key= selector(e)
        map[key]= map[key]?.plus(1) ?: 0
    }
    return map
}
fun ByteArray.countDuplication(): Map<Byte, Int> = countDuplicationBy { it }

/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik berdasarkan [selector]
 */
fun <R> ByteArray.isUniqueBy(selector: (Byte) -> R): Boolean {
    if(size < 2) return true
    val set= HashSet<R>()
    for(e in this){
        if(!set.add(selector(e)))
            return false
    }
    return true
}
/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik.
 */
fun ByteArray.isUnique(): Boolean = isUniqueBy { it }

/**
 * Menghitung jumlah duplikat. Jml yang dikembalikan adalah jml duplikat,
 * tidak termasuk jml elemen unik yng pertama. Jadi jika elemen e berjumlah hanya 1 di
 * `this.extension` `Iterable`, maka jml duplikasinya aalah 0.
 */
fun <R> ShortArray.countDuplicationBy(selector: (Short) -> R): Map<R, Int> {
    val map= mutableMapOf<R, Int>()
    for(e in this){
        val key= selector(e)
        map[key]= map[key]?.plus(1) ?: 0
    }
    return map
}
fun ShortArray.countDuplication(): Map<Short, Int> = countDuplicationBy { it }

/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik berdasarkan [selector]
 */
fun <R> ShortArray.isUniqueBy(selector: (Short) -> R): Boolean {
    if(size < 2) return true
    val set= HashSet<R>()
    for(e in this){
        if(!set.add(selector(e)))
            return false
    }
    return true
}
/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik.
 */
fun ShortArray.isUnique(): Boolean = isUniqueBy { it }

/**
 * Menghitung jumlah duplikat. Jml yang dikembalikan adalah jml duplikat,
 * tidak termasuk jml elemen unik yng pertama. Jadi jika elemen e berjumlah hanya 1 di
 * `this.extension` `Iterable`, maka jml duplikasinya aalah 0.
 */
fun <R> IntArray.countDuplicationBy(selector: (Int) -> R): Map<R, Int> {
    val map= mutableMapOf<R, Int>()
    for(e in this){
        val key= selector(e)
        map[key]= map[key]?.plus(1) ?: 0
    }
    return map
}
fun IntArray.countDuplication(): Map<Int, Int> = countDuplicationBy { it }

/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik berdasarkan [selector]
 */
fun <R> IntArray.isUniqueBy(selector: (Int) -> R): Boolean {
    if(size < 2) return true
    val set= HashSet<R>()
    for(e in this){
        if(!set.add(selector(e)))
            return false
    }
    return true
}
/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik.
 */
fun IntArray.isUnique(): Boolean = isUniqueBy { it }

/**
 * Menghitung jumlah duplikat. Jml yang dikembalikan adalah jml duplikat,
 * tidak termasuk jml elemen unik yng pertama. Jadi jika elemen e berjumlah hanya 1 di
 * `this.extension` `Iterable`, maka jml duplikasinya aalah 0.
 */
fun <R> LongArray.countDuplicationBy(selector: (Long) -> R): Map<R, Int> {
    val map= mutableMapOf<R, Int>()
    for(e in this){
        val key= selector(e)
        map[key]= map[key]?.plus(1) ?: 0
    }
    return map
}
fun LongArray.countDuplication(): Map<Long, Int> = countDuplicationBy { it }

/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik berdasarkan [selector]
 */
fun <R> LongArray.isUniqueBy(selector: (Long) -> R): Boolean {
    if(size < 2) return true
    val set= HashSet<R>()
    for(e in this){
        if(!set.add(selector(e)))
            return false
    }
    return true
}
/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik.
 */
fun LongArray.isUnique(): Boolean = isUniqueBy { it }

/**
 * Menghitung jumlah duplikat. Jml yang dikembalikan adalah jml duplikat,
 * tidak termasuk jml elemen unik yng pertama. Jadi jika elemen e berjumlah hanya 1 di
 * `this.extension` `Iterable`, maka jml duplikasinya aalah 0.
 */
fun <R> FloatArray.countDuplicationBy(selector: (Float) -> R): Map<R, Int> {
    val map= mutableMapOf<R, Int>()
    for(e in this){
        val key= selector(e)
        map[key]= map[key]?.plus(1) ?: 0
    }
    return map
}
fun FloatArray.countDuplication(): Map<Float, Int> = countDuplicationBy { it }

/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik berdasarkan [selector]
 */
fun <R> FloatArray.isUniqueBy(selector: (Float) -> R): Boolean {
    if(size < 2) return true
    val set= HashSet<R>()
    for(e in this){
        if(!set.add(selector(e)))
            return false
    }
    return true
}
/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik.
 */
fun FloatArray.isUnique(): Boolean = isUniqueBy { it }

/**
 * Menghitung jumlah duplikat. Jml yang dikembalikan adalah jml duplikat,
 * tidak termasuk jml elemen unik yng pertama. Jadi jika elemen e berjumlah hanya 1 di
 * `this.extension` `Iterable`, maka jml duplikasinya aalah 0.
 */
fun <R> DoubleArray.countDuplicationBy(selector: (Double) -> R): Map<R, Int> {
    val map= mutableMapOf<R, Int>()
    for(e in this){
        val key= selector(e)
        map[key]= map[key]?.plus(1) ?: 0
    }
    return map
}
fun DoubleArray.countDuplication(): Map<Double, Int> = countDuplicationBy { it }

/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik berdasarkan [selector]
 */
fun <R> DoubleArray.isUniqueBy(selector: (Double) -> R): Boolean {
    if(size < 2) return true
    val set= HashSet<R>()
    for(e in this){
        if(!set.add(selector(e)))
            return false
    }
    return true
}
/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik.
 */
fun DoubleArray.isUnique(): Boolean = isUniqueBy { it }

/**
 * Menghitung jumlah duplikat. Jml yang dikembalikan adalah jml duplikat,
 * tidak termasuk jml elemen unik yng pertama. Jadi jika elemen e berjumlah hanya 1 di
 * `this.extension` `Iterable`, maka jml duplikasinya aalah 0.
 */
fun <R> CharArray.countDuplicationBy(selector: (Char) -> R): Map<R, Int> {
    val map= mutableMapOf<R, Int>()
    for(e in this){
        val key= selector(e)
        map[key]= map[key]?.plus(1) ?: 0
    }
    return map
}
fun CharArray.countDuplication(): Map<Char, Int> = countDuplicationBy { it }

/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik berdasarkan [selector]
 */
fun <R> CharArray.isUniqueBy(selector: (Char) -> R): Boolean {
    if(size < 2) return true
    val set= HashSet<R>()
    for(e in this){
        if(!set.add(selector(e)))
            return false
    }
    return true
}
/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik.
 */
fun CharArray.isUnique(): Boolean = isUniqueBy { it }

/**
 * Menghitung jumlah duplikat. Jml yang dikembalikan adalah jml duplikat,
 * tidak termasuk jml elemen unik yng pertama. Jadi jika elemen e berjumlah hanya 1 di
 * `this.extension` `Iterable`, maka jml duplikasinya aalah 0.
 */
fun <R> BooleanArray.countDuplicationBy(selector: (Boolean) -> R): Map<R, Int> {
    val map= mutableMapOf<R, Int>()
    for(e in this){
        val key= selector(e)
        map[key]= map[key]?.plus(1) ?: 0
    }
    return map
}
fun BooleanArray.countDuplication(): Map<Boolean, Int> = countDuplicationBy { it }

/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik berdasarkan [selector]
 */
fun <R> BooleanArray.isUniqueBy(selector: (Boolean) -> R): Boolean {
    if(size < 2) return true
    val set= HashSet<R>()
    for(e in this){
        if(!set.add(selector(e)))
            return false
    }
    return true
}
/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik.
 */
fun BooleanArray.isUnique(): Boolean = size < 2 || run {
    var bool= true
    val first= this[0]
    for(i in 1 until size){
        if(this[i] == first){
            bool= false
            break
        }
    }
    bool
}





/**
 * [start] inklusif dan [end] eksklusif.
 */
//fun <T: Number> Iterable<T>.gaps(start: T?= null, end: T?= null): List<Int> {}
fun Array<Int>.gaps(start: Int= -1, end: Int= -1): List<Int> {
    var start= start
    var end= end

    if(start < 0 || end < 0){
        var max= 0
        var min= Int.MAX_VALUE
        for(e in this){
            if(max < e)
                max= e
            if(min > e)
                min= e
        }

        if(start < 0)
            start= min
        if(end < 0)
            end= max //+ 1
    }

    val size= end - start +1
    if(size <= 1)
        return emptyList()

    val presents= BooleanArray(size)

    for(e in this)
        presents[e - start]= true

    val gaps= mutableListOf<Int>()
    for((i, bool) in presents.withIndex()){
        if(!bool){
            gaps += i + start
        }
    }
    return gaps
}


/**
 * [start] inklusif dan [end] eksklusif.
 */
//fun <T: Number> Iterable<T>.gaps(start: T?= null, end: T?= null): List<Int> {}
fun <T> Array<T>.gapsBy(start: Int= -1, end: Int= -1, selector: (T) -> Int): List<Int> {
    var start= start
    var end= end

    if(start < 0 || end < 0){
        var max= 0
        var min= Int.MAX_VALUE
        for(e in this){
            val int= selector(e)
            if(max < int)
                max= int
            if(min > int)
                min= int
        }

        if(start < 0)
            start= min
        if(end < 0)
            end= max //+ 1
    }

    val size= end - start +1
    if(size <= 1)
        return emptyList()

    val presents= BooleanArray(size)

    for(e in this)
        presents[selector(e) - start]= true

    val gaps= mutableListOf<Int>()
    for((i, bool) in presents.withIndex()){
        if(!bool){
            gaps += i + start
        }
    }
    return gaps
}


fun ByteArray.toGeneral(): Array<Any> = Array(size){ this[it] }
fun ShortArray.toGeneral(): Array<Any> = Array(size){ this[it] }
fun IntArray.toGeneral(): Array<Any> = Array(size){ this[it] }
fun LongArray.toGeneral(): Array<Any> = Array(size){ this[it] }
fun FloatArray.toGeneral(): Array<Any> = Array(size){ this[it] }
fun DoubleArray.toGeneral(): Array<Any> = Array(size){ this[it] }
fun BooleanArray.toGeneral(): Array<Any> = Array(size){ this[it] }
fun CharArray.toGeneral(): Array<Any> = Array(size){ this[it] }

internal fun Any.toGeneralArray(): Array<Any>? = when(this){
    is ByteArray -> toGeneral()
    is ShortArray -> toGeneral()
    is IntArray -> toGeneral()
    is LongArray -> toGeneral()
    is FloatArray -> toGeneral()
    is DoubleArray -> toGeneral()
    is BooleanArray -> toGeneral()
    is CharArray -> toGeneral()
    else -> null
}


fun ByteArray.contentEquals(other: ByteArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().shallowUnorderedContentEquals(other.asList()) //asList().containsAll(other.asList())
fun ShortArray.contentEquals(other: ShortArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().shallowUnorderedContentEquals(other.asList())
fun IntArray.contentEquals(other: IntArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().shallowUnorderedContentEquals(other.asList())
fun LongArray.contentEquals(other: LongArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().shallowUnorderedContentEquals(other.asList())
fun FloatArray.contentEquals(other: FloatArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().shallowUnorderedContentEquals(other.asList())
fun DoubleArray.contentEquals(other: DoubleArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().shallowUnorderedContentEquals(other.asList())
fun BooleanArray.contentEquals(other: BooleanArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().shallowUnorderedContentEquals(other.asList())
fun CharArray.contentEquals(other: CharArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().shallowUnorderedContentEquals(other.asList())


fun <T> Array<out T>.ktContentEquals(other: Array<out T>): Boolean = ktContentEquals_(other)
fun ByteArray.ktContentEquals(other: ByteArray): Boolean = ktContentEquals_(other)
fun IntArray.ktContentEquals(other: IntArray): Boolean = ktContentEquals_(other)
fun LongArray.ktContentEquals(other: LongArray): Boolean = ktContentEquals_(other)
fun FloatArray.ktContentEquals(other: FloatArray): Boolean = ktContentEquals_(other)
fun DoubleArray.ktContentEquals(other: DoubleArray): Boolean = ktContentEquals_(other)
fun BooleanArray.ktContentEquals(other: BooleanArray): Boolean = ktContentEquals_(other)
fun CharArray.ktContentEquals(other: CharArray): Boolean = ktContentEquals_(other)


internal val Any.contentSize: Int get()= when(this){
    is ByteArray -> size
    is ShortArray -> size
    is IntArray -> size
    is LongArray -> size
    is FloatArray -> size
    is DoubleArray -> size
    is BooleanArray -> size
    is CharArray -> size
    is Collection<*> -> size
    is SizeProp -> size
    is Array<*> -> size
    else -> {
        if(this::class.simpleName == Array::class.simpleName) try {
            @Suppress(SuppressLiteral.UNCHECKED_CAST)
            (this as Array<Any?>).size
        } catch (e: ClassCastException){
            throw ClassCastExc(
                fromClass = this::class,
                toClass = Array::class
            )
        } else throw IllegalAccessExc(
            msg = "`this` ($this) bukan merupakan Array<*>"
        )
    }
}