@file:JvmName("_SortFunJvm")
package sidev.lib.collection

import sidev.lib.`val`.Order
import sidev.lib.collection.array.toIntArray
import sidev.lib.collection.comparator.NaturalOrderComparator
import sidev.lib.collection.comparator.NumberNaturalOrderComparator
import sidev.lib.collection.comparator.NumberReversedOrderComparator
import sidev.lib.collection.comparator.ReversedOrderComparator
import java.util.*
import java.util.Arrays.sort as jsort
import java.util.Collections.sort as jsortList

/**
 * Mengurutkan `this.extension` [Array<Any?>].
 * Throws [ClassCastException] jika terdapat elemen yang bkn [Comparable].
 * Knp kok receiver gak dijadikan sebagai Array<Comparable<T>>?
 *  -Karena kelas [Array] akan dubah menjaditipe kelas [T] scr langsung
 *  dan java dapat mengecek tipe [T] yang sesungguhnya.
 *  Contoh: Object[] tidak dapat dicast ke A[] walau semua isinya berkelas A.
 */
fun <T> Array<T>.fastSort(from: Int, until: Int, order: Order) =
    if(order == Order.ASC) jsort(this, from, until)
    else jsort(this, from, until, Collections.reverseOrder())
actual fun <T: Comparable<*>> Array<T>.fastSort(from: Int, until: Int, order: Order) =
    if(order == Order.ASC) jsort(this, from, until)
    else jsort(this, from, until, Collections.reverseOrder())
actual fun <T> Array<T>.fastSortWith(from: Int, until: Int, comparator: (n1: T, n2: T) -> Int) =
    jsort(this, from, until, comparator)
actual fun <T, R: Comparable<*>> Array<T>.fastSortBy(from: Int, until: Int, order: Order, toComparableFun: (T) -> R) {
    val comparator= if(order == Order.ASC) Comparator<T> { n1, n2 -> compareValuesBy(n1, n2, toComparableFun) }
        else Comparator { n1, n2 -> compareValuesBy(n2, n1, toComparableFun) }
    jsort(this, from, until, comparator)
}
actual fun CharArray.fastSort(from: Int, until: Int, order: Order){
    jsort(this, from, until)
    if(order == Order.DESC) reverse()
}
actual fun ByteArray.fastSort(from: Int, until: Int, order: Order){
    jsort(this, from, until)
    if(order == Order.DESC) reverse()
}
actual fun ShortArray.fastSort(from: Int, until: Int, order: Order) {
    jsort(this, from, until)
    if(order == Order.DESC) reverse()
}
actual fun IntArray.fastSort(from: Int, until: Int, order: Order){
    jsort(this, from, until)
    if(order == Order.DESC) reverse()
}
actual fun LongArray.fastSort(from: Int, until: Int, order: Order){
    jsort(this, from, until)
    if(order == Order.DESC) reverse()
}
actual fun FloatArray.fastSort(from: Int, until: Int, order: Order){
    jsort(this, from, until)
    if(order == Order.DESC) reverse()
}
actual fun DoubleArray.fastSort(from: Int, until: Int, order: Order){
    jsort(this, from, until)
    if(order == Order.DESC) reverse()
}
actual fun <T: Comparable<*>> MutableList<T>.fastSort(order: Order, withNumberSafety: Boolean) {
    val comparator= if(!withNumberSafety){
        if(order == Order.ASC) NaturalOrderComparator
        else ReversedOrderComparator
    } else {
        if(order == Order.ASC) NumberNaturalOrderComparator
        else NumberReversedOrderComparator
    }
    jsortList(this, comparator as Comparator<in T>)
}
//actual fun <T: Comparable<*>> MutableList<T>.fastSortWith(c: Comparator<in T>) = jsortList(this, c)
/*
actual fun <T, R:Comparable<*>> MutableList<T>.fastSortBy(comparator: Int, until: Int, order: Order, toComparableFun: (T) -> R) = jsortList(this,
    comparator
)
 */
actual fun <T> MutableList<T>.fastSortWith(comparator: (n1: T, n2: T) -> Int) = jsortList(this, comparator)
actual fun <T, R: Comparable<*>> MutableList<T>.fastSortBy(
    order: Order, toComparableFun: (T) -> R
) {
    val comparator= if(order == Order.ASC) Comparator<T> { n1, n2 -> compareValuesBy(n1, n2, toComparableFun) }
    else Comparator { n1, n2 -> compareValuesBy(n2, n1, toComparableFun) }
    jsortList(this, comparator)
}

/*
===============
Comparison
===============
 */
/*
fun <T: Comparable<T>> Collection<T>.sortedWith(f: (T, T) -> Boolean = ::asc): List<T> = this.toMutableList().sort(f)
/**
 * Mengurutkan isi dari `this.extension` [List] jika isinya merupakan turunan [Comparable]
 * dan mengembalikan `this.extension` sehingga dapat di-chain.
 */
fun <L: MutableList<T>, T: Comparable<T>> L.sort(f: (T, T) -> Boolean = ::asc): L {
    for(i in indices)
        for(u in i+1 until size){
            val isOrderTrue = try{ f(this[i], this[u]) }
            catch (e: ClassCastException){
                val ascFun: (T, T) -> Boolean = ::asc
                val descFun: (T, T) -> Boolean = ::desc
                when(f){
                    ascFun -> univAsc(this[i], this[u])
                    descFun -> univDesc(this[i], this[u])
                    else -> throw UndefinedDeclarationExc(undefinedDeclaration = "${this[i]::class}.compareTo(${this[u]::class})")
                }
            }
            if(!isOrderTrue){
                val temp= this[i]
                this[i]= this[u]
                this[u]= temp
            }
        }
    return this
}
/ *
/** Sama dg [MutableList.sort] di atas, namun digunakan pada [Array]. */
fun <T: Comparable<T>> Array<T>.sort(f: (T, T) -> Boolean = ::asc): Array<T> {
    for(i in indices)
        for(u in i+1 until size){
            val isOrderTrue = try{ f(this[i], this[u]) }
            catch (e: ClassCastException){
                val ascFun: (T, T) -> Boolean = ::asc
                val descFun: (T, T) -> Boolean = ::desc
                when(f){
                    ascFun -> univAsc(this[i], this[u])
                    descFun -> univDesc(this[i], this[u])
                    else -> throw UndefinedDeclarationExc(undefinedDeclaration = "${this[i]::class}.compareTo(${this[u]::class})")
                }
            }
            if(!isOrderTrue){
                val temp= this[i]
                this[i]= this[u]
                this[u]= temp
            }
        }
    return this
}
 */