package sidev.lib.collection

import sidev.lib.`val`.Order
import sidev.lib.collection.comparator.NaturalOrderComparator
import sidev.lib.collection.comparator.NumberNaturalOrderComparator
import sidev.lib.collection.comparator.NumberReversedOrderComparator
import sidev.lib.collection.comparator.ReversedOrderComparator


actual fun <T: Comparable<*>> Array<T>.fastSort(from: Int, until: Int, order: Order){
    (this as Array<Comparable<Any?>>).sort(from, until)
    if(order == Order.DESC) reverse()
}
actual fun <T> Array<T>.fastSortWith(from: Int, until: Int, comparator: (n1: T, n2: T) -> Int) = sortWith(comparator, from, until)
actual fun <T, R: Comparable<*>> Array<T>.fastSortBy(from: Int, until: Int, order: Order, toComparableFun: (T) -> R) {
    val comparator= if(order == Order.ASC) Comparator<T> { n1, n2 -> compareValuesBy(n1, n2, toComparableFun) }
        else Comparator { n1, n2 -> compareValuesBy(n2, n1, toComparableFun) }
    sortWith(comparator, from, until)
}
actual fun CharArray.fastSort(from: Int, until: Int, order: Order){
    sort(from, until)
    if(order == Order.DESC) reverse()
}
actual fun ByteArray.fastSort(from: Int, until: Int, order: Order){
    sort(from, until)
    if(order == Order.DESC) reverse()
}
actual fun ShortArray.fastSort(from: Int, until: Int, order: Order){
    sort(from, until)
    if(order == Order.DESC) reverse()
}
actual fun IntArray.fastSort(from: Int, until: Int, order: Order){
    sort(from, until)
    if(order == Order.DESC) reverse()
}
actual fun LongArray.fastSort(from: Int, until: Int, order: Order){
    sort(from, until)
    if(order == Order.DESC) reverse()
}
actual fun FloatArray.fastSort(from: Int, until: Int, order: Order){
    sort(from, until)
    if(order == Order.DESC) reverse()
}
actual fun DoubleArray.fastSort(from: Int, until: Int, order: Order){
    sort(from, until)
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
    sortWith(comparator as Comparator<in T>)
}
//actual fun <T: Comparable<*>> MutableList<T>.fastSortWith(c: Comparator<in T>) = sortWith(this, c)

actual fun <T> MutableList<T>.fastSortWith(comparator: (n1: T, n2: T) -> Int) = sortWith(comparator)
actual fun <T, R: Comparable<*>> MutableList<T>.fastSortBy(
    order: Order, toComparableFun: (T) -> R
) {
    val comparator= if(order == Order.ASC) Comparator<T> { n1, n2 -> compareValuesBy(n1, n2, toComparableFun) }
        else Comparator { n1, n2 -> compareValuesBy(n2, n1, toComparableFun) }
    sortWith(comparator)
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