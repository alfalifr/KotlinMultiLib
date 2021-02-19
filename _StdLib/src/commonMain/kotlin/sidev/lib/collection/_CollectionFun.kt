@file:OptIn(ExperimentalStdlibApi::class)
package sidev.lib.collection

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.collection.array.forEach
import sidev.lib.collection.iterator.SkippableIteratorImpl
import sidev.lib.collection.sequence.toOtherSequence
//import sidev.lib.console.prine
import sidev.lib.exception.IllegalArgExc
import sidev.lib.exception.InternalExc
import sidev.lib.reflex.getHashCode
import sidev.lib.reflex.isPrimitiveArray
import sidev.lib.reflex.isSubclassInstanceOf
import sidev.lib.structure.data.value.Var
import kotlin.jvm.JvmOverloads
import kotlin.collections.toTypedArray as kToTypedArray
import kotlin.collections.toList as kToList
import sidev.lib.structure.data.value.kIndexes


inline fun <reified T> Collection<T>.toTypedArray(): Array<T> = kToTypedArray()
inline fun <reified T> Iterable<T>.toTypedArray(): Array<T> = toTypedArray(false)
inline fun <reified T> Iterable<T>.toTypedArray(reversed: Boolean = false): Array<T> {
    val arr= when(this){
        is Collection<T> -> this
        else -> kToList()
    }.kToTypedArray()
    if(reversed)
        arr.reverse()
    return arr
}
inline fun <reified T> List<T>.toTypedArray(from: Int = 0, until: Int = size, reversed: Boolean = false): Array<T> = when {
    from == 0 && until == size -> kToTypedArray()
    !reversed -> Array(until - from){ this[it +from] }
    else -> {
        val lastIndex= until -1
        Array(until - from){ this[lastIndex -it] }
    }
}
inline fun <reified T> Iterable<T>.toTypedArray(from: Int = 0, until: Int = size, reversed: Boolean = false): Array<T> = when {
    from == 0 && until == size -> toTypedArray(reversed)
    this is List<T> -> toTypedArray(from, until, reversed)
    else -> {
        val itr= iterator()
        val range= from until until
        val arr= arrayOfNulls<T>(until - from)
        var i= 0
        while(itr.hasNext()){
            val e= itr.next()
            if(i in range)
                arr[i++]= e
            else if(i > range.last)
                break
        }
        if(reversed)
            arr.reverse()
        @Suppress(SuppressLiteral.UNCHECKED_CAST)
        arr as Array<T>
    }
}

fun Iterable<*>.toArray(): Array<Any?> = toTypedArray()
fun Iterable<*>.toArray(reversed: Boolean = false): Array<Any?> = toTypedArray(reversed)
fun Iterable<*>.toArray(from: Int = 0, until: Int = size, reversed: Boolean = false): Array<Any?> = toTypedArray(from, until, reversed)
fun List<*>.toArray(from: Int, until: Int, reversed: Boolean): Array<Any?> = toTypedArray(from, until, reversed)
fun Collection<*>.toArray(): Array<Any?> = kToTypedArray()


fun <T> Collection<T>.asMutableList(): MutableList<T> = when(this){
    is MutableList<*> -> this as MutableList<T>
    else -> toMutableList()
}
fun <T> Collection<T>.asList(): List<T> = when(this){
    is List<*> -> this as List<T>
    else -> kToList()
}

fun <T> Set<T>.asMutableSet(): MutableSet<T> = when(this){
    is MutableSet<*> -> this as MutableSet<T>
    else -> toMutableSet()
}

fun <T> listOf(size: Int, init: (index: Int) -> T): List<T>{
    return arrayListOf<T>().apply {
        for(i in 0 until size)
            add(init(i))
    }
}



/*
/** Mengambil bbrp elemen dari `this.extension` List dari [range.first] (inclusive) hingga [range.last] (exclusive). */
operator fun <T> List<T>.get(range: IntRange): List<T> = subList(range.first, range.last)

/** Mengambil bbrp elemen dari `this.extension` Array dari [range.first] (inclusive) hingga [range.last] (exclusive). */
operator fun <T> Array<T>.get(range: IntRange): Array<T> = sliceArray(range)
 */

fun <T> Iterable<T>.forEach(
    start: Int= 0, end: Int= -1,
    reversed: Boolean = false,
    breakRef: Var<Boolean>? = null,
    contRef: Var<Boolean>? = null,
    block: (T) -> Unit
) {
    if(this is List<*>) {
        val range= if(!reversed) start until if(end < 0) size else end
        else {
            (if(start > 0) start else size-1) downTo (if(end < 0) 0 else end+1)
        }
        for(i in range){
            if(breakRef?.value == true)
                break
            if(contRef?.value == true)
                continue
            block(this[i] as T)
        }
    } else {
        if(!reversed){
            val itr= iterator()
            val range= start until if(end < 0) Int.MAX_VALUE else end
            var i= 0

            while (itr.hasNext()){
                if(breakRef?.value == true)
                    break
                if(contRef?.value == true)
                    continue
                val e= itr.next()
                if(i in range)
                    block(e)
                i++
            }
        } else {
            val list= this.toList()
            val range= (if(start > 0) start else list.size-1) downTo (if(end < 0) 0 else end+1)
            for(i in range){
                if(breakRef?.value == true)
                    break
                if(contRef?.value == true)
                    continue
                block(list[i])
            }
        }
    }
}
fun <T> Iterable<T>.forEachIndexed(
    start: Int= 0, end: Int= -1,
    reversed: Boolean = false,
    breakRef: Var<Boolean>? = null,
    contRef: Var<Boolean>? = null,
    block: (i: Int, T) -> Unit
) {
    if(this is List<*>) {
        val range= if(!reversed) start until if(end < 0) size else end
        else {
            (if(start > 0) start else size-1) downTo (if(end < 0) 0 else end+1)
        }
        for(i in range){
            if(breakRef?.value == true)
                break
            if(contRef?.value == true)
                continue
            block(i, this[i] as T)
        }
    } else {
        if(!reversed){
            val itr= iterator()
            val range= start until if(end < 0) Int.MAX_VALUE else end
            var i= 0

            while (itr.hasNext()){
                if(breakRef?.value == true)
                    break
                if(contRef?.value == true)
                    continue
                val e= itr.next()
                if(i in range)
                    block(i, e)
                i++
            }
        } else {
            val list= this.toList()
            val range= (if(start > 0) start else list.size-1) downTo (if(end < 0) 0 else end+1)
            for(i in range){
                if(breakRef?.value == true)
                    break
                if(contRef?.value == true)
                    continue
                block(i, list[i])
            }
        }
    }
}


fun <T> Iterable<T>.forEach(
    start: Int= 0, end: Int= -1,
    reversed: Boolean = false,
    block: (T) -> Unit
) {
    if(this is List<*>) {
        val range= if(!reversed) start until if(end < 0) size else end
        else {
            (if(start > 0) start else size-1) downTo (if(end < 0) 0 else end+1)
        }
        for(i in range)
            block(this[i] as T)
    } else {
        if(!reversed){
            val itr= iterator()
            val range= start until if(end < 0) Int.MAX_VALUE else end
            var i= 0

            while (itr.hasNext()){
                val e= itr.next()
                if(i in range)
                    block(e)
                i++
            }
        } else {
            val list= this.toList()
            val range= (if(start > 0) start else list.size-1) downTo (if(end < 0) 0 else end+1)
            for(i in range){
                block(list[i])
            }
        }
    }
}
fun <T> Iterable<T>.forEachIndexed(
    start: Int= 0, end: Int= -1,
    reversed: Boolean = false,
    block: (i: Int, T) -> Unit
) {
    if(this is List<*>) {
        val range= if(!reversed) start until if(end < 0) size else end
        else {
            (if(start > 0) start else size-1) downTo (if(end < 0) 0 else end+1)
        }
        for(i in range)
            block(i, this[i] as T)
    } else {
        if(!reversed){
            val itr= iterator()
            val range= start until if(end < 0) Int.MAX_VALUE else end
            var i= 0

            while (itr.hasNext()){
                val e= itr.next()
                if(i in range)
                    block(i, e)
                i++
            }
        } else {
            val list= this.toList()
            val range= (if(start > 0) start else list.size-1) downTo (if(end < 0) 0 else end+1)
            for(i in range){
                block(i, list[i])
            }
        }
    }
}




/** Sama seperti [first] sekaligus mengahpus element pertama */
fun <T> MutableList<T>.takeFirst(): T = if(isEmpty()) throw NoSuchElementException("List is Empty") else removeAt(0)

/** Sama seperti [firstOrNull] sekaligus mengahpus element pertama
 *  Di Kotlin sama seperti [removeFirstOrNull].
 * */
fun <T> MutableList<T>.takeFirstOrNull(): T? = if(isEmpty()) null else removeAt(0)

/** Sama seperti [last] sekaligus mengahpus element pertama */
fun <T> MutableList<T>.takeLast(): T = if(isEmpty()) throw NoSuchElementException("List is Empty") else removeAt(lastIndex)

/** Sama seperti [lastOrNull] sekaligus mengahpus element pertama */
fun <T> MutableList<T>.takeLastOrNull(): T? = if(isEmpty()) null else removeAt(lastIndex)

/** Sama seperti [remove], namun menghapus last occurrence. */
fun <T> MutableList<T>.removeLast(element: T): Boolean{ //= if(isEmpty()) throw NoSuchElementException("List is Empty") else removeAt(lastIndex)
    for(i in size -1 downTo 0)
        if(this[i] == element){
            removeAt(i)
            return true
        }
    return false
}

/** Menghapus last occurrence jika [predicate] menghasilkan `true`. */
inline fun <T> MutableList<T>.removeLastIf(predicate: (T) -> Boolean): Boolean { //= if(isEmpty()) throw NoSuchElementException("List is Empty") else removeAt(lastIndex)
    for(i in size -1 downTo 0)
        if(predicate(this[i])){
            removeAt(i)
            return true
        }
    return false
}

/** Menghapus first occurrence jika [predicate] menghasilkan `true`. */
inline fun <T> MutableList<T>.removeIf_(predicate: (T) -> Boolean): Boolean { //= if(isEmpty()) throw NoSuchElementException("List is Empty") else removeAt(lastIndex)
    for((i, e) in this.withIndex())
        if(predicate(e)){
            removeAt(i)
            return true
        }
    return false
}


/**
 * [chekcFun] return `true` brarti element yg akan dimasukan adalah identik dg yg udah ada dan tidak dimasukan.
 * @return true jika [element] tidak terdapat sebelumnya di list ini.
 * Fungsi ini menggunakan standard equals().
 */
inline fun <T> MutableList<T>.addIfAbsent(element: T, chekcFun: ((existingElement: T) -> Boolean)= {true}): Boolean{
//    prine("MutableList<T>.addIfAbsent() this::class= ${this::class} this= $this")
//    log(this)
    val existingElementIndex= indexOf(element)
    val canAdd= existingElementIndex < 0 || !chekcFun(this[existingElementIndex])
    if(canAdd)
        this.add(element)
    return canAdd
}
/**
 * [chekcFun] return `true` brarti element yg akan dimasukan adalah identik dg yg udah ada dan tidak dimasukan.
 * @return true jika [element] tidak terdapat sebelumnya di list ini.
 * Fungsi ini menggunakan standard equals().
 */
inline fun <T> MutableList<T>.addAllIfAbsent(vararg element: T, chekcFun: ((existingElement: T) -> Boolean)= {true}): Boolean{
    var res= false
    for(e in element)
        res= res || addIfAbsent(e, chekcFun)
    return res
}



/*
inline fun <reified T> Array<T>.copyTo(dest: Array<T>, from: Int= 0, until: Int= size, reversed: Boolean= false): Array<T> {
    rangeCheck(until - from, from, until)

    val rangeItr= (if(!reversed) from until until
        else until-1 downTo from).iterator()
    var i= 0
    for(u in rangeItr)
        dest[i++]= this[u]
    return dest //Array(end - start){ this[rangeItr.nextInt()] }
}
 */

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> List<T>.copy(from: Int= 0, until: Int= size, reversed: Boolean= false): List<T> =
    if(from == 0 && until == size && !reversed) ArrayList(this)
    else (this as List<*>).toTypedArray(from, until, reversed).asList() as List<T>

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Set<T>.copy(from: Int= 0, until: Int= size, reversed: Boolean= false): Set<T> =
    if(from == 0 && until == size && !reversed) HashSet(this)
    else (this as Set<*>).toTypedArray(from, until, reversed).toSet() as Set<T>

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Collection<T>.copy(from: Int= 0, until: Int= size, reversed: Boolean= false): Collection<T> = (this as List).copy(from, until, reversed)
    //(this as Collection<*>).toTypedArray(from, until, reversed).asList() as Collection<T>

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Iterable<T>.copy(from: Int= 0, until: Int= size, reversed: Boolean= false): Iterable<T> = when(this) {
    is List<*> -> (this as List<T>).copy(from, until, reversed)
    is Set<*> -> (this as Set<T>).copy(from, until, reversed)
    is Collection<*> -> (this as Collection<T>).copy(from, until, reversed)
    else -> (this as Iterable<*>).toTypedArray(from, until, reversed).asList() as Iterable<T>
}

/*
{
    val newList= mutableListOf<T>()
    val range= if(!reversed) start until end
        else end-1 downTo start
    for(i in range)
        newList.add(this[i])
    return newList
}
 */


/*
==========================
Math Collection Operation
==========================
 */
infix fun <T> Collection<T>.duplicatUnion(other: Collection<T>): List<T>{
    val minColl= if(size > other.size) other else this
    val maxColl= (if(size <= other.size) other else this).toMutableList()

    for(e in minColl)
        maxColl -= e

    return minColl + maxColl
}

infix fun <T> Collection<T>.duplicatIntersect(other: Collection<T>): List<T>{
    val minColl= if(size > other.size) other else this
    val maxColl= (if(size <= other.size) other else this).toMutableList()

    val res= mutableListOf<T>()

    for(e in minColl){
        if(e in maxColl){
            res += e
            maxColl -= e
        }
    }

    return res //minColl + maxColl
}

infix fun <C: MutableCollection<T>, T> C.intersect(other: Iterable<T>): C{
    this.retainAll(other)
    return this
}

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <C: MutableCollection<T>, T> C.distinct(): C{
    return toMutableSet().toMutableList() as C
}


/*
==========================
Collection Operation
==========================
 */

fun <T> Iterable<T>.findIndexed(predicate: (IndexedValue<T>) -> Boolean): IndexedValue<T>?{
    for(vals in this.withIndex()){
        if(predicate(vals))
            return vals
    }
    return null
}

fun <T> Iterable<T>.findLastIndexed(predicate: (IndexedValue<T>) -> Boolean): IndexedValue<T>?{
    var foundElement: IndexedValue<T>?= null
    for(vals in this.withIndex()){
        if(predicate(vals))
            foundElement= vals
    }
    return foundElement
}

fun <T> List<T>.findLastIndexed(predicate: (IndexedValue<T>) -> Boolean): IndexedValue<T>?{
    var foundElement: IndexedValue<T>?= null
    for(i in lastIndex downTo 0){
        val vals= i kIndexes this[i]
        if(predicate(vals))
            foundElement= vals
    }
    return foundElement
}


fun <T> Sequence<T>.findIndexed(predicate: (IndexedValue<T>) -> Boolean): IndexedValue<T>?{
    for(vals in this.withIndex()){
        if(predicate(vals))
            return vals
    }
    return null
}

fun <T> Sequence<T>.findLastIndexed(predicate: (IndexedValue<T>) -> Boolean): IndexedValue<T>?{
    var foundElement: IndexedValue<T>?= null
    for(vals in this.withIndex()){
        if(predicate(vals))
            foundElement= vals
    }
    return foundElement
}



fun <T> Iterable<T>.filterIndexed(predicate: (IndexedValue<T>) -> Boolean): List<IndexedValue<T>>{
    val res= ArrayList<IndexedValue<T>>()
    for(vals in this.withIndex())
        if(predicate(vals))
            res += vals
    return res
}
fun <T> Sequence<T>.filterIndexed(predicate: (IndexedValue<T>) -> Boolean): Sequence<IndexedValue<T>>{
    var index= 0
    return toOtherSequence { IndexedValue(index++, it) }.filter(predicate)
}

fun <T> Iterable<T>.filterContainedIn(array: Array<T>): List<T> {
    val out= ArrayList<T>()
    for(e in this)
        if(e in array)
            out.add(e)
    return out
}


fun CharSequence.filterContainedIn(array: Array<String>): List<String> {
    val out= ArrayList<String>()
    for(e in array)
        if(e in this)
            out.add(e)
    return out
}

fun <T> Iterable<T>.indexOfWhere(start: Int = 0, predicate: (T) -> Boolean): Int {
    if(this is List){
        for(i in start until size)
            if(predicate(this[i]))
                return i
    } else {
        for((i, e) in this.withIndex())
            if(i >= start && predicate(e))
                return i
    }
    return -1
}


/*
================================
Operator Overriding
================================
 */

operator fun <C: MutableCollection<T>, T> C.plus(element: T): C{
    this.add(element)
    return this
}
operator fun <C: MutableCollection<T>, T> C.rangeTo(collection: Collection<T>): C{
    this.addAll(collection)
    return this
}

operator fun <C: MutableCollection<T>, T> C.minus(element: T): C{
    this.remove(element)
    return this
}
operator fun <C: MutableCollection<T>, T> C.minus(collection: Collection<T>): C{
    this.removeAll(collection)
    return this
}

operator fun <L: MutableList<T>, T> L.times(factor: Int): L{
    this.growTimely(factor)
    return this
}


@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> MutableList<T>.pop(): T = when(this){
    is Vector<*> -> pop()
    else -> removeFirst()
} as T

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Collection<T>.peek(): T = when(this){
    is Vector<*> -> peek()
    else -> first()
} as T

fun <T> MutableList<T>.push(item: T): T = when(this){
    is Vector<*> -> (this as Stack<T>).push(item)
    else -> {
        add(0, item)
        item
    }
}


/**
 * Merapatkan elemen `this.extension` List dari index [start] hingga [end]
 * dg cara menghapus elemen null yg berada di tengah-tengahnya.
 * Fungsi ini mengembalikan `true` jika terjadi perpindahan tempat pada elemen yg dikarenakan adanya `null`.
 */
@JvmOverloads
fun <T> MutableList<T>.trimNulls(start: Int= 0, end: Int= size): Boolean{
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
 * Merapatkan elemen `this.extension` Collection dari index [start] hingga [end]
 * dg cara menghapus elemen null yg berada di tengah-tengahnya.
 * Fungsi ini mengembalikan `true` jika terjadi perpindahan tempat pada elemen yg dikarenakan adanya `null`.
 */
fun <T> MutableCollection<T>.trimNulls(): Boolean = when(this){
    is MutableList<*> -> trimNulls()
    else -> {
        var bool= false
        for(e in this)
            if(e == null){
                remove(e)
                bool= true
            }
        bool
    }
}

/**
 * Mengambil sub-list dari `this.extension` List dimulai dari index [range.first] sampai [range.last]
 */
@Suppress(SuppressLiteral.UNCHECKED_CAST)
operator fun <C: Collection<T>, T> C.get(range: IntRange): C {
//    if(range.step < 0) // Gak perlu pake pengecekan karena `IntRange` itu otomatis step nya positif.
//        throw IllegalArgumentException("Progression dari range harus positif, progression skrg= ${range.step}")
    return (if(this is List<*>) this else toList()).subList(range.first, range.last) as C
}


fun <T> stackOf(vararg elements: T): Stack<T> = StackImpl<T>(elements.size +5).apply {
    elements.forEach { push(it) }
}

fun <T> queueOf(vararg elements: T): Queue<T> = QueueImpl<T>(elements.size +5).apply {
    elements.forEach { push(it) }
}

val Iterable<*>.size: Int get()= when(this){
    is Collection<*> -> size
    else -> toList().size
}


fun rangeCheck(size: Int, fromIndex: Int, toIndex: Int) {
    when {
        fromIndex > toIndex -> throw IllegalArgExc(paramExcepted = sidev.lib.collection.array.arrayOf(
            "fromIndex",
            "toIndex"
        ), detailMsg = "`fromIndex`='$fromIndex' lebih besar dari `toIndex`='$toIndex'.")
        fromIndex < 0 -> throw IndexOutOfBoundsException("`fromIndex`='$fromIndex' kurang dari 0.")
        toIndex > size -> throw IndexOutOfBoundsException("`toIndex`='$toIndex' lebih dari `size`='$size'")
    }
}

@JvmOverloads
fun <T> Iterable<T>.asReadOnly(copyFirst: Boolean = true): ReadOnlyList<T> {
    val list= if(this is List<*>) this as List else kToList()
    return ReadOnlyList(list, copyFirst)
}

@JvmOverloads
fun <T> Set<T>.asReadOnly(copyFirst: Boolean = true): ReadOnlySet<T> = ReadOnlySet(this, copyFirst)


/*
fun <T, R> Iterable<T>.countBy(selector: (T) -> R): Map<R, Int> {
    val map= mutableMapOf<R, Int>()
    for(e in this){
        val key= selector(e)
        map[key]= map[key]?.plus(1) ?: 1
    }
    return map
}
 */

/**
 * Menghitung jumlah duplikat. Jml yang dikembalikan adalah jml duplikat,
 * tidak termasuk jml elemen unik yng pertama. Jadi jika elemen e berjumlah hanya 1 di
 * `this.extension` `Iterable`, maka jml duplikasinya aalah 0.
 */
fun <T, R> Iterable<T>.countDuplicationBy(selector: (T) -> R): Map<R, Int> {
    val map= mutableMapOf<R, Int>()
    for(e in this){
        val key= selector(e)
        map[key]= map[key]?.plus(1) ?: 0
    }
    return map
}
fun <T> Iterable<T>.countDuplication(): Map<T, Int> = countDuplicationBy { it }

/**
 * Menghitung jumlah tiap elemen unik berdasakan hasil return [selector].
 */
fun <T, R> Iterable<T>.countUniqueBy(selector: (T) -> R): Map<R, Int> {
    val map= mutableMapOf<R, Int>()
    for(e in this){
        val key= selector(e)
        map[key]= map[key]?.plus(1) ?: 1
    }
    return map
}
fun <T> Iterable<T>.countUnique(): Map<T, Int> = countUniqueBy { it }


fun <T, R> Iterable<T>.countProbabilitiesBy(selector: (T) -> R): Map<R, Double> {
    val unique= countUniqueBy(selector)
    val sum= unique.values.sum().toDouble()
    val map= mutableMapOf<R, Double>()
    for((key, dup) in unique){
        map[key]= dup / sum
    }
    return map
}

fun <T> Iterable<T>.countProbabilities(): Map<T, Double> = countProbabilitiesBy { it }


/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik.
 */
fun <T> Iterable<T>.isUnique(): Boolean = isUniqueBy { it }
/**
 * Menentukan apakah semua elemen pada `this.extension` `Iterable` semuanya unik berdasarkan [selector]
 */
fun <T, R> Iterable<T>.isUniqueBy(selector: (T) -> R): Boolean {
    val set= HashSet<R>()
    for(e in this){
        if(!set.add(selector(e)))
            return false
    }
    return true
}


/**
 * [start] inklusif dan [end] eksklusif.
 */
//fun <T: Number> Iterable<T>.gaps(start: T?= null, end: T?= null): List<Int> {}
fun Iterable<Int>.gaps(start: Int= -1, end: Int= -1): List<Int> {
    @Suppress(SuppressLiteral.NAME_SHADOWING)
    var start= start
    @Suppress(SuppressLiteral.NAME_SHADOWING)
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
fun <T> Iterable<T>.gapsBy(start: Int= -1, end: Int= -1, selector: (T) -> Int): List<Int> {
    @Suppress(SuppressLiteral.NAME_SHADOWING)
    var start= start
    @Suppress(SuppressLiteral.NAME_SHADOWING)
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

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Iterable<T?>.notNullIterator(): Iterator<T> =
    object: SkippableIteratorImpl<T>(this.iterator() as Iterator<T>) {
        override fun skip(now: T): Boolean = now == null
    }
@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> Array<out T?>.notNullIterator(): Iterator<T> =
    object: SkippableIteratorImpl<T>(this.iterator() as Iterator<T>) {
        override fun skip(now: T): Boolean = now == null
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


private fun <T> Collection<T>.shallowOrderedContentEquals(
    other: Collection<T>,
    selectFun: ((T) -> Any?)? = null,
    checkFun: (e1: Any?, e2: Any?) -> Boolean = { e1, e2 -> e1 == e2 }
): Boolean {
    if(size != other.size) return false
    if(isEmpty()) return true

    @Suppress(SuppressLiteral.NAME_SHADOWING)
    val checkFun: (e1: T, e2: T) -> Boolean =
        if(selectFun == null) checkFun
        else { e1, e2 -> checkFun(selectFun(e1), selectFun(e2)) }

    if(this is List){
        if(other is List){
            for(i in indices)
                if(!checkFun(this[i], other[i]))
                //if(selectFun(this[i]) != selectFun(other[i]))
                    return false
        } else {
            for((i, e2) in other.withIndex())
                if(!checkFun(this[i], e2))
                //if(selectFun(this[i]) != selectFun(e2))
                    return false
        }
    } else {
        if(other is List){
            for((i, e1) in withIndex())
                if(!checkFun(e1, other[i]))
                //if(selectFun(e1) != selectFun(other[i]))
                    return false
        } else {
            val otherItr= other.iterator()
            for(e1 in this){
                if(!otherItr.hasNext() || !checkFun(e1, otherItr.next()))
                //if(!otherItr.hasNext() || selectFun(e1) != selectFun(otherItr.next()))
                    return false
            }
        }
    }
    return true
}

internal fun <T> Collection<T>.shallowUnorderedContentEquals(
    other: Collection<T>,
    selectFun: ((T) -> Any?)? = null
): Boolean {
    //prine("shallowUnorderedContentEquals() AWAL DW this= ${this.toBeautyString()} other= ${other.toBeautyString()}")

    if(size != other.size) return false
    if(isEmpty()) return true

    //prine("shallowUnorderedContentEquals() selectFun= $selectFun AWAL HBS RETURN")

    @Suppress(SuppressLiteral.NAME_SHADOWING)
    val selectFun= selectFun ?: { it }

    val count1= countUniqueBy(selectFun)
    val count2= other.countUniqueBy(selectFun)


    //prine("shallowUnorderedContentEquals() count1= $count1 count2= $count2")

    for((k1, v1) in count1)
        if(v1 != count2[k1])
            return false

    return true
}


@JvmOverloads
@ExperimentalStdlibApi
fun <T> Collection<T>.shallowContentEquals(
    other: Collection<T>,
    checkOrder: Boolean = true,
    selectFun: ((T) -> Any?)?= null
): Boolean =
    if(checkOrder) shallowOrderedContentEquals(other, selectFun)
    else shallowUnorderedContentEquals(other, selectFun)


private fun <T> Collection<T>.deepContentEquals(
    other: Collection<T>,
    //isFlat: Boolean,
    checkOrder: Boolean = true,
    selectFun: ((Any?) -> Any?)? = null
    //checkFun: (e1: Any?, e2: Any?) -> Boolean = { e1, e2 -> e1 == e2 }
    //level: Int = 0
): Boolean {
/*
    if(isFlat)
        return shallowContentEquals(other, checkOrder)
 */

    if(size != other.size) return false
    if(isEmpty()) return true

    val selectFun_= selectFun ?: { it }

    //prine("deepContentEquals() AWAL UDAH RETUR AWAL checkOrder= $checkOrder selectFun= $selectFun")

    fun checkEquality(e1: Any?, e2: Any?): Boolean {
        //prine("deepContentEquals().checkEquality() AWAL DW e1=$e1 e2=$e2")
        if((e1 == null) xor (e2 == null)) // Jika salah satu saja null, namun tidak keduanya.
            return false
        if(e1 == null || e1 === e2) // Otomatis e2 == e1 dalam hal nullability
            return true

        //prine("e1::class=${e1::class} e2::class=${e2!!::class}")
        //prine("e1.isSubclassInstanceOf(e2!!)= ${e1.isSubclassInstanceOf(e2!!)} e2.isSubclassInstanceOf(e1)= ${e2.isSubclassInstanceOf(e1)}")

        if(!(e1.isSubclassInstanceOf(e2!!) || e2.isSubclassInstanceOf(e1))) // Jika e1 dan e2 tidak berada pada 1 inheritance
            return false

        //prine("deepContentEquals().checkEquality() LANJUT")

        return when(e1){
            is Collection<*> -> e1.deepContentEquals(e2 as Collection<*>, checkOrder, selectFun) //level+1
            is Array<*> -> e1.asList().deepContentEquals((e2 as Array<*>).asList(), checkOrder, selectFun)
            else -> {
                if(e1::class.isPrimitiveArray){
                    when(e1){
                        is ByteArray -> e1.asList()
                        is ShortArray -> e1.asList()
                        is IntArray -> e1.asList()
                        is LongArray -> e1.asList()
                        is FloatArray -> e1.asList()
                        is DoubleArray -> e1.asList()
                        is BooleanArray -> e1.asList()
                        is CharArray -> e1.asList()
                        else -> throw  InternalExc(msg = "Terjadi kesalahan pada logika e1::class.isPrimitiveArray (true), e1::class= ${e1::class}")
                    }.deepContentEquals(
                        when(e2){
                            is ByteArray -> e2.asList()
                            is ShortArray -> e2.asList()
                            is IntArray -> e2.asList()
                            is LongArray -> e2.asList()
                            is FloatArray -> e2.asList()
                            is DoubleArray -> e2.asList()
                            is BooleanArray -> e2.asList()
                            is CharArray -> e2.asList()
                            else -> throw  InternalExc(msg = "Terjadi kesalahan pada logika e2::class.isPrimitiveArray (true), e2::class= ${e2::class}")
                        },
                        checkOrder
                    )
                } else selectFun_(e1) == selectFun_(e2)
            }
        }
    }

    return if(checkOrder) shallowOrderedContentEquals(other, selectFun, ::checkEquality)
    else shallowUnorderedContentEquals(other){ getHashCode(it, calculateOrder = false, ignoreLevel = false) }
}


@JvmOverloads
fun <T> Collection<T>.contentEquals(
    other: Collection<T>,
    shallowCheck: Boolean = true,
    checkOrder: Boolean = true,
    //isFlat: Boolean = true,
    selectFun: ((Any?) -> Any?)? = null
): Boolean =
    if(shallowCheck) shallowContentEquals(other, checkOrder, selectFun)
    else deepContentEquals(other, checkOrder, selectFun)


fun <T> Collection<T>.contentEquals(
    other: Array<out T>,
    shallowCheck: Boolean = true,
    checkOrder: Boolean = true,
    //isFlat: Boolean = true,
    selectFun: ((Any?) -> Any?)? = null
): Boolean = contentEquals(
    other.asList(),
    shallowCheck,
    checkOrder,
    //isFlat,
    selectFun
)

fun <T> Array<out T>.contentEquals(
    other: Collection<T>,
    shallowCheck: Boolean = true,
    checkOrder: Boolean = true,
    //isFlat: Boolean = true,
    selectFun: ((Any?) -> Any?)? = null
): Boolean = asList().contentEquals(
    other,
    shallowCheck,
    checkOrder,
    //isFlat,
    selectFun
)

@JvmOverloads
fun <T> Array<out T>.contentEquals(
    other: Array<out T>,
    shallowCheck: Boolean = true,
    checkOrder: Boolean = true,
    //isFlat: Boolean = true,
    selectFun: ((Any?) -> Any?)? = null
): Boolean = asList().contentEquals(
    other.asList(),
    shallowCheck,
    checkOrder,
    //isFlat,
    selectFun
)