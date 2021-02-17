package sidev.lib.collection.array

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.annotation.Unsafe
import sidev.lib.collection.countDuplication
import sidev.lib.progression.asEndExclusive
import sidev.lib.structure.data.value.Var
import sidev.lib.reflex.clazz
import sidev.lib.reflex.isPrimitiveArray
import kotlin.collections.contentEquals as ktContentEquals_
import kotlin.collections.contentDeepEquals as ktContentDeepEquals_
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


/*
TODO <20 Jan 2021> -> Blum fix, nti dibetulkan.
/**
 * [immediatePredicate] digunakan untuk mengecek elemen saat [shallowCheck] == `true`.
 * [nestedPredicate] digunakan untuk mengecek elemen saat [shallowCheck] == cfalse.
 *   Param ini berguna untuk diteruskan ke nested element karena tipe datanya berbeda dengan immediate element.
 */
fun <T> Collection<T>.contentEquals(
    other: Collection<T>,
    shallowCheck: Boolean = true,
    checkOrder: Boolean = true,
    nestedPredicate: ((e1: Any?, e2: Any?) -> Boolean)?= null, // Untuk pengecekan terhadap elemen trahir jika elemen bkn brupa [T]
    immediatePredicate: ((e1: T, e2: T) -> Boolean)?= null
): Boolean {
    return size == other.size && if(shallowCheck) {
        if(checkOrder) {
            if(immediatePredicate == null) this == other
            else {
                val otherItr= other.iterator()
                for(e in this)
                    if(!immediatePredicate(e, otherItr.next()))
                        return false
                true
            }
        } else {
            if(immediatePredicate == null) {
                val thisMapCount= countDuplication()
                val otherMapCount= other.countDuplication()
                val thisKeys= thisMapCount.keys
                val otherKeys= otherMapCount.keys
                if(thisKeys.size != otherKeys.size)
                    return false
                if(thisKeys != otherKeys)
                    return false
                for(k in thisKeys){
                    if(thisMapCount[k] != otherMapCount[k])
                        return false
                }
                true
            } else {
                for(e in this)
                    if(!other.any { immediatePredicate(e, it) })
                        return false
                true
            }
        }
    } else {
        if(checkOrder){
            val otherItr= other.iterator()
            for(e in this){
                val otherE= otherItr.next()
                if(!when(e){
                        is Collection<*> -> when(otherE){
                            is Collection<*> -> e.contentEquals(otherE, shallowCheck, checkOrder, nestedPredicate)
                            is Array<*> -> e.contentEquals(otherE, shallowCheck, checkOrder, nestedPredicate)
                            else -> {
                                if(otherE?.clazz?.isPrimitiveArray == true)
                                    e.contentEquals(otherE.toGeneralArray()!!, shallowCheck, checkOrder, nestedPredicate)
                                else nestedPredicate?.invoke(e, otherE) ?: (e == otherE)
                            }
                        }
                        is Array<*> -> when(otherE){
                            is Collection<*> -> e.contentEquals(otherE, shallowCheck, checkOrder, nestedPredicate)
                            is Array<*> -> e.contentEquals(otherE, shallowCheck, checkOrder, nestedPredicate)
                            else -> {
                                if(otherE?.clazz?.isPrimitiveArray == true)
                                    e.contentEquals(otherE.toGeneralArray()!!, shallowCheck, checkOrder, nestedPredicate)
                                else nestedPredicate?.invoke(e, otherE) ?: (e == otherE)
                            }
                        }
                        else -> {
                            if(e?.clazz?.isPrimitiveArray == true
                                && otherE?.clazz?.isPrimitiveArray == true)
                                e.toGeneralArray()!!.contentEquals(
                                    otherE.toGeneralArray()!!, shallowCheck, checkOrder, nestedPredicate
                                )
                            else nestedPredicate?.invoke(e, otherE) ?: (e == otherE)
                        }
                    }) return false
            }
            true
        } else {
            for(e in this){
                if(!when(e){
                        is Collection<*> -> other.any {
                            when(it){
                                is Collection<*> -> e.contentEquals(it, shallowCheck, checkOrder, nestedPredicate)
                                is Array<*> -> e.contentEquals(it, shallowCheck, checkOrder, nestedPredicate)
                                else -> {
                                    if(it?.clazz?.isPrimitiveArray == true)
                                        e.contentEquals(it.toGeneralArray()!!, shallowCheck, checkOrder, nestedPredicate)
                                    else nestedPredicate?.invoke(e, it) ?: (e == it)
                                }
                            }
                        }
                        is Array<*> -> other.any {
                            when(it){
                                is Collection<*> -> e.contentEquals(it, shallowCheck, checkOrder, nestedPredicate)
                                is Array<*> -> e.contentEquals(it, shallowCheck, checkOrder, nestedPredicate)
                                else -> {
                                    if(it?.clazz?.isPrimitiveArray == true)
                                        e.contentEquals(it.toGeneralArray()!!, shallowCheck, checkOrder, nestedPredicate)
                                    else nestedPredicate?.invoke(e, it) ?: (e == it)
                                }
                            }
                        }
                        else -> {
                            if(e?.clazz?.isPrimitiveArray == true){
                                val eArr= e.toGeneralArray()!!
                                other.any {
                                    if(it?.clazz?.isPrimitiveArray == true)
                                        eArr.contentEquals(it.toGeneralArray()!!, shallowCheck, checkOrder, nestedPredicate)
                                    else nestedPredicate?.invoke(e, it) ?: (e == it)
                                }
                            } else other.any {
                                nestedPredicate?.invoke(e, it) ?: (e == it)
                            }
                        }
                    }) return false
            }
            true
        }
    }
}
fun <T> Collection<T>.contentEquals(
    other: Array<out T>,
    shallowCheck: Boolean = true,
    checkOrder: Boolean = true,
    nestedPredicate: ((e1: Any?, e2: Any?) -> Boolean)?= null, // Untuk pengecekan terhadap elemen trahir jika elemen bkn brupa [T]
    immediatePredicate: ((e1: T, e2: T) -> Boolean)?= null
): Boolean {
    return size == other.size && if(shallowCheck) {
        if(checkOrder) {
            val otherItr= other.iterator()
            if(immediatePredicate == null){
                for(e in this){
                    val otherE= otherItr.next()
                    if(e != otherE)
                        return false
                }
            } else {
                for(e in this){
                    val otherE= otherItr.next()
                    if(!immediatePredicate(e, otherE))
                        return false
                }
            }
            true
        } else {
            if(immediatePredicate == null) containsAll(other.asList())
            else {
                for(e in this)
                    if(!other.any { immediatePredicate(e, it) })
                        return false
                true
            }
        }
    } else {
        contentEquals(other.asList(), shallowCheck, checkOrder, nestedPredicate, immediatePredicate)
    }
}
fun <T> Array<out T>.contentEquals(
    other: Array<out T>,
    shallowCheck: Boolean = true,
    checkOrder: Boolean = true,
    nestedPredicate: ((e1: Any?, e2: Any?) -> Boolean)?= null, // Untuk pengecekan terhadap elemen trahir jika elemen bkn brupa [T]
    immediatePredicate: ((e1: T, e2: T) -> Boolean)?= null
): Boolean {
    return size == other.size && if(shallowCheck) {
        if(checkOrder) {
            if(immediatePredicate == null) ktContentEquals_(other)
            else {
                val otherItr= other.iterator()
                for(e in this){
                    val otherE= otherItr.next()
                    if(!immediatePredicate(e, otherE))
                        return false
                }
                true
            }
        } else {
            if(immediatePredicate == null) asList().containsAll(other.asList())
            else {
                for(e in this)
                    if(!other.any { immediatePredicate(e, it) })
                        return false
                true
            }
        }
    } else {
        if(checkOrder && nestedPredicate == null) ktContentDeepEquals_(other)
        else asList().contentEquals(other.asList(), shallowCheck, checkOrder, nestedPredicate, immediatePredicate)
    }
}
fun <T> Array<out T>.contentEquals(
    other: Collection<T>,
    shallowCheck: Boolean = true,
    checkOrder: Boolean = true,
    nestedPredicate: ((e1: Any?, e2: Any?) -> Boolean)?= null, // Untuk pengecekan terhadap elemen trahir jika elemen bkn brupa [T]
    immediatePredicate: ((e1: T, e2: T) -> Boolean)?= null
): Boolean {
    return size == other.size && if(shallowCheck) {
        if(checkOrder) {
            val otherItr= other.iterator()
            if(immediatePredicate == null){
                for(e in this){
                    val otherE= otherItr.next()
                    if(e != otherE)
                        return false
                }
            } else {
                for(e in this){
                    val otherE= otherItr.next()
                    if(!immediatePredicate(e, otherE))
                        return false
                }
            }
            true
        } else {
            if(immediatePredicate == null) asList().containsAll(other)
            else {
                for(e in this)
                    if(!other.any { immediatePredicate(e, it) })
                        return false
                true
            }
        }
    } else {
        asList().contentEquals(other, shallowCheck, checkOrder, nestedPredicate, immediatePredicate)
    }
}//= size == other.size && asList().containsAll(other)

/*
internal fun Any.arrayContentEquals(
    other: Any,
    shallowCheck: Boolean = true,
    checkOrder: Boolean = true
): Boolean {
    return when(this){
        is Array<*> -> contentEquals(when(other){
            is Array<*> -> other
            is ByteArray -> other.toGeneral()
            is ShortArray -> other.toGeneral()
            is IntArray -> other.toGeneral()
            is LongArray -> other.toGeneral()
            is FloatArray -> other.toGeneral()
            is DoubleArray -> other.toGeneral()
            is BooleanArray -> other.toGeneral()
            is CharArray -> other.toGeneral()
            else -> return false
        }, shallowCheck, checkOrder)
        is ByteArray -> if(other is ByteArray) contentEquals(other, checkOrder)
        else toGeneral().contentEquals(when(other) {
            is Array<*> -> other
            is ByteArray -> other.toGeneral()
            is ShortArray -> other.toGeneral()
            is IntArray -> other.toGeneral()
            is LongArray -> other.toGeneral()
            is FloatArray -> other.toGeneral()
            is DoubleArray -> other.toGeneral()
            is BooleanArray -> other.toGeneral()
            is CharArray -> other.toGeneral()
            else -> return false
        }, shallowCheck, checkOrder)
        is ShortArray -> if(other is ShortArray) contentEquals(other, checkOrder)
        else toGeneral().contentEquals(when(other) {
            is Array<*> -> other
            is ByteArray -> other.toGeneral()
            is ShortArray -> other.toGeneral()
            is IntArray -> other.toGeneral()
            is LongArray -> other.toGeneral()
            is FloatArray -> other.toGeneral()
            is DoubleArray -> other.toGeneral()
            is BooleanArray -> other.toGeneral()
            is CharArray -> other.toGeneral()
            else -> return false
        }, shallowCheck, checkOrder)
        is IntArray -> if(other is IntArray) contentEquals(other, checkOrder)
        else toGeneral().contentEquals(when(other) {
            is Array<*> -> other
            is ByteArray -> other.toGeneral()
            is ShortArray -> other.toGeneral()
            is IntArray -> other.toGeneral()
            is LongArray -> other.toGeneral()
            is FloatArray -> other.toGeneral()
            is DoubleArray -> other.toGeneral()
            is BooleanArray -> other.toGeneral()
            is CharArray -> other.toGeneral()
            else -> return false
        }, shallowCheck, checkOrder)
        is LongArray -> other.toGeneral()
        is FloatArray -> other.toGeneral()
        is DoubleArray -> other.toGeneral()
        is BooleanArray -> other.toGeneral()
        is CharArray -> other.toGeneral()
    }
}

// */

internal fun ByteArray.numberArrayEquals(other: Any, checkOrder: Boolean = true): Boolean {
    return when(other){
        is ByteArray -> {
            if(checkOrder) ktContentEquals_(other)
            else {
                for(e in this){
                    if(e !in other)
                        return false
                }
                true
            }
        }
        is ShortArray -> {
            if(checkOrder) {
                val otherItr= other.iterator()
                for(e in this){
                    val otherE= otherItr.next()
                    if(e.compareTo(otherE) != 0)
                        return false
                }
            } else {
                for(e in this)
                    if(!other.any { e.compareTo(it) == 0 })
                        return false
            }
            true
        }
        is IntArray -> {
            if(checkOrder) {
                val otherItr= other.iterator()
                for(e in this){
                    val otherE= otherItr.next()
                    if(e.compareTo(otherE) != 0)
                        return false
                }
            } else {
                for(e in this)
                    if(!other.any { e.compareTo(it) == 0 })
                        return false
            }
            true
        }
        is LongArray -> {
            if(checkOrder) {
                val otherItr= other.iterator()
                for(e in this){
                    val otherE= otherItr.next()
                    if(e.compareTo(otherE) != 0)
                        return false
                }
            } else {
                for(e in this)
                    if(!other.any { e.compareTo(it) == 0 })
                        return false
            }
            true
        }
        else -> false
    }
}
internal fun ShortArray.numberArrayEquals(other: Any, checkOrder: Boolean = true): Boolean {
    return when(other){
        is ShortArray -> {
            if(checkOrder) ktContentEquals_(other)
            else {
                for(e in this){
                    if(e !in other)
                        return false
                }
                true
            }
        }
        is ByteArray -> {
            if(checkOrder) {
                val otherItr= other.iterator()
                for(e in this){
                    val otherE= otherItr.next()
                    if(e.compareTo(otherE) != 0)
                        return false
                }
            } else {
                for(e in this)
                    if(!other.any { e.compareTo(it) == 0 })
                        return false
            }
            true
        }
        is IntArray -> {
            if(checkOrder) {
                val otherItr= other.iterator()
                for(e in this){
                    val otherE= otherItr.next()
                    if(e.compareTo(otherE) != 0)
                        return false
                }
            } else {
                for(e in this)
                    if(!other.any { e.compareTo(it) == 0 })
                        return false
            }
            true
        }
        is LongArray -> {
            if(checkOrder) {
                val otherItr= other.iterator()
                for(e in this){
                    val otherE= otherItr.next()
                    if(e.compareTo(otherE) != 0)
                        return false
                }
            } else {
                for(e in this)
                    if(!other.any { e.compareTo(it) == 0 })
                        return false
            }
            true
        }
        else -> false
    }
}
internal fun IntArray.numberArrayEquals(other: Any, checkOrder: Boolean = true): Boolean {
    return when(other){
        is IntArray -> {
            if(checkOrder) ktContentEquals_(other)
            else {
                for(e in this){
                    if(e !in other)
                        return false
                }
                true
            }
        }
        is ByteArray -> {
            if(checkOrder) {
                val otherItr= other.iterator()
                for(e in this){
                    val otherE= otherItr.next()
                    if(e.compareTo(otherE) != 0)
                        return false
                }
            } else {
                for(e in this)
                    if(!other.any { e.compareTo(it) == 0 })
                        return false
            }
            true
        }
        is ShortArray -> {
            if(checkOrder) {
                val otherItr= other.iterator()
                for(e in this){
                    val otherE= otherItr.next()
                    if(e.compareTo(otherE) != 0)
                        return false
                }
            } else {
                for(e in this)
                    if(!other.any { e.compareTo(it) == 0 })
                        return false
            }
            true
        }
        is LongArray -> {
            if(checkOrder) {
                val otherItr= other.iterator()
                for(e in this){
                    val otherE= otherItr.next()
                    if(e.compareTo(otherE) != 0)
                        return false
                }
            } else {
                for(e in this)
                    if(!other.any { e.compareTo(it) == 0 })
                        return false
            }
            true
        }
        else -> false
    }
}

internal fun LongArray.numberArrayEquals(other: Any, checkOrder: Boolean = true): Boolean {
    return when(other){
        is LongArray -> {
            if(checkOrder) ktContentEquals_(other)
            else {
                for(e in this){
                    if(e !in other)
                        return false
                }
                true
            }
        }
        is ByteArray -> {
            if(checkOrder) {
                val otherItr= other.iterator()
                for(e in this){
                    val otherE= otherItr.next()
                    if(e.compareTo(otherE) != 0)
                        return false
                }
            } else {
                for(e in this)
                    if(!other.any { e.compareTo(it) == 0 })
                        return false
            }
            true
        }
        is ShortArray -> {
            if(checkOrder) {
                val otherItr= other.iterator()
                for(e in this){
                    val otherE= otherItr.next()
                    if(e.compareTo(otherE) != 0)
                        return false
                }
            } else {
                for(e in this)
                    if(!other.any { e.compareTo(it) == 0 })
                        return false
            }
            true
        }
        is IntArray -> {
            if(checkOrder) {
                val otherItr= other.iterator()
                for(e in this){
                    val otherE= otherItr.next()
                    if(e.compareTo(otherE) != 0)
                        return false
                }
            } else {
                for(e in this)
                    if(!other.any { e.compareTo(it) == 0 })
                        return false
            }
            true
        }
        else -> false
    }
}

 */

fun ByteArray.contentEquals(other: ByteArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().containsAll(other.asList())
fun ShortArray.contentEquals(other: ShortArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().containsAll(other.asList())
fun IntArray.contentEquals(other: IntArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().containsAll(other.asList())
fun LongArray.contentEquals(other: LongArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().containsAll(other.asList())
fun FloatArray.contentEquals(other: FloatArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().containsAll(other.asList())
fun DoubleArray.contentEquals(other: DoubleArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().containsAll(other.asList())
fun BooleanArray.contentEquals(other: BooleanArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().containsAll(other.asList())
fun CharArray.contentEquals(other: CharArray, checkOrder: Boolean = true): Boolean =
    size == other.size && if(checkOrder) ktContentEquals_(other) else asList().containsAll(other.asList())

fun <T> Array<out T>.ktContentEquals(other: Array<out T>): Boolean = ktContentEquals_(other)
fun ByteArray.ktContentEquals(other: ByteArray): Boolean = ktContentEquals_(other)
fun IntArray.ktContentEquals(other: IntArray): Boolean = ktContentEquals_(other)
fun LongArray.ktContentEquals(other: LongArray): Boolean = ktContentEquals_(other)
fun FloatArray.ktContentEquals(other: FloatArray): Boolean = ktContentEquals_(other)
fun DoubleArray.ktContentEquals(other: DoubleArray): Boolean = ktContentEquals_(other)
fun BooleanArray.ktContentEquals(other: BooleanArray): Boolean = ktContentEquals_(other)
fun CharArray.ktContentEquals(other: CharArray): Boolean = ktContentEquals_(other)