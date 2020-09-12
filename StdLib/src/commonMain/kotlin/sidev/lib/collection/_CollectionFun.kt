package sidev.lib.collection

import sidev.lib.collection.sequence.toOtherSequence


fun <T> listOf(size: Int, init: (index: Int) -> T): List<T>{
    return arrayListOf<T>().apply {
        for(i in 0 until size)
            add(init(i))
    }
}

/** Mengambil bbrp elemen dari `this.extension` List dari [range.first] (inclusive) hingga [range.last] (exclusive). */
operator fun <T> List<T>.get(range: IntRange): List<T> = subList(range.first, range.last)

/** Mengambil bbrp elemen dari `this.extension` Array dari [range.first] (inclusive) hingga [range.last] (exclusive). */
operator fun <T> Array<T>.get(range: IntRange): Array<T> = sliceArray(range)

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


inline fun <reified T> Array<T>.copy(reversed: Boolean= false): Array<T> {
    return if(!reversed) this.copyOf()
    else Array(this.size){this[size -it -1]}
}

fun <T> List<T>.copy(reversed: Boolean= false): List<T> {
    val newList= mutableListOf<T>()
    val range= if(!reversed) this.indices
    else this.size .. 0
    for(i in range)
        newList.add(this[i])
    return newList
}


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

infix fun <C: MutableCollection<T>, T> C.intersect(other: Iterable<T>): C{
    this.retainAll(other)
    return this
}

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


