package sidev.lib.collection

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.collection.sequence.toOtherSequence
import sidev.lib.console.log
import sidev.lib.console.prine
import sidev.lib.exception.IllegalArgExc
import sidev.lib.structure.data.value.Val
import kotlin.jvm.JvmOverloads
import sidev.lib.collection.array.get as stdSubArray
import kotlin.collections.toTypedArray as kToTypedArray


fun <T> listOf(size: Int, init: (index: Int) -> T): List<T>{
    return arrayListOf<T>().apply {
        for(i in 0 until size)
            add(init(i))
    }
}

inline fun <reified T> Iterable<T>.toTypedArray(): Array<T> = when(this){
    is Collection<*> -> this as Collection<T>
    else -> toList()
}.kToTypedArray()



/*
/** Mengambil bbrp elemen dari `this.extension` List dari [range.first] (inclusive) hingga [range.last] (exclusive). */
operator fun <T> List<T>.get(range: IntRange): List<T> = subList(range.first, range.last)

/** Mengambil bbrp elemen dari `this.extension` Array dari [range.first] (inclusive) hingga [range.last] (exclusive). */
operator fun <T> Array<T>.get(range: IntRange): Array<T> = sliceArray(range)
 */

fun <T> Iterable<T>.forEach(
    start: Int= 0, end: Int= -1,
    reversed: Boolean = false,
    breakRef: Val<Boolean>? = null,
    contRef: Val<Boolean>? = null,
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
    breakRef: Val<Boolean>? = null,
    contRef: Val<Boolean>? = null,
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


inline fun <reified T> Array<T>.copy(start: Int= 0, end: Int= size, reversed: Boolean= false): Array<T> = copyTo(arrayOfNulls<T>(end - start) as Array<T>, start, end, reversed)
inline fun <reified T> Array<T>.copyTo(dest: Array<T>, start: Int= 0, end: Int= size, reversed: Boolean= false): Array<T> {
    rangeCheck(end - start, start, end)

    val rangeItr= (if(!reversed) start until end
        else end-1 downTo start).iterator()
    var i= 0
    for(u in rangeItr)
        dest[i++]= this[u]
    return dest //Array(end - start){ this[rangeItr.nextInt()] }
}

inline fun <reified T> List<T>.copy(start: Int= 0, end: Int= size, reversed: Boolean= false): List<T> = kToTypedArray().copy(start, end, reversed).asList()

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

operator fun <T> Array<T>.get(range: IntRange): Array<T> = stdSubArray(range)


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
        fromIndex > toIndex -> throw IllegalArgExc(paramExcepted = arrayOf("fromIndex", "toIndex"), detailMsg = "`fromIndex`='$fromIndex' lebih besar dari `toIndex`='$toIndex'.")
        fromIndex < 0 -> throw IndexOutOfBoundsException("`fromIndex`='$fromIndex' kurang dari 0.")
        toIndex > size -> throw IndexOutOfBoundsException("`toIndex`='$toIndex' lebih dari `size`='$size'")
    }
}