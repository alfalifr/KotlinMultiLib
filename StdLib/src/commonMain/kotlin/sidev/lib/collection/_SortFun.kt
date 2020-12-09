package sidev.lib.collection

import sidev.lib.`val`.Order
import sidev.lib.`val`.SortConst
import sidev.lib.algo.insertionSortWith
import sidev.lib.algo.mergeSortWith
import sidev.lib.algo.quickSortWith
import sidev.lib.collection.array.toIntArray
import sidev.lib.structure.data.Arrangeable
import sidev.lib.structure.data.RangeCopyable
import sidev.lib.structure.data.asArrangeable
import kotlin.jvm.JvmName


/*
===============
FastSort - Tidak menjamin stabilitas
===============
 */

expect fun <T: Comparable<*>> Array<T>.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun <T> Array<T>.fastSortWith(from: Int = 0, until: Int = size, comparator: (n1: T, n2: T) -> Int)
expect fun <T, R: Comparable<*>> Array<T>.fastSortBy(
    from: Int = 0, until: Int = size, order: Order = Order.ASC, toComparableFun: (T) -> R
)
expect fun CharArray.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun ByteArray.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun ShortArray.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun IntArray.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun LongArray.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun FloatArray.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun DoubleArray.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun <T: Comparable<*>> MutableList<T>.fastSort(order: Order = Order.ASC, withNumberSafety: Boolean = false)
//expect fun <T: Comparable<*>> MutableList<T>.fastSortWith(c: Comparator<in T>)
expect fun <T> MutableList<T>.fastSortWith(comparator: (n1: T, n2: T) -> Int)
expect fun <T, R: Comparable<*>> MutableList<T>.fastSortBy(
//    from: Int= 0, until: Int= size,
    order: Order= Order.ASC, toComparableFun: (T) -> R
)

fun BooleanArray.fastSort(from: Int= 0, until: Int= size, order: Order= Order.ASC){
    val intArr= toIntArray()
    intArr.fastSort(from, until)
    if(order == Order.ASC)
        intArr.forEachIndexed { i, int -> this[i]= int > 0 }
    else {
        val lastIndex= lastIndex
        intArr.forEachIndexed { i, int -> this[lastIndex-i]= int > 0 }
    }
}

///*
fun <T> Arrangeable<T>.fastSortWith(from: Int = 0, until: Int = size, comparator: (n1: T, n2: T) -> Int) {
    when {
        size <= SortConst.LEVEL_1_LIMIT -> insertionSortWith(from, until, comparator = comparator)
//        size <= 100_000 -> mergeSortWith(from, until, comparator = comparator)
        else -> quickSortWith(from, until, comparator = comparator)
    }
}
@JvmName("fastSortWith_rangeCopyable")
fun <T, E> T.fastSortWith(from: Int = 0, until: Int = size, comparator: (n1: E, n2: E) -> Int)
where T: Arrangeable<E>, T: RangeCopyable<T> {
    when {
        size <= SortConst.LEVEL_1_LIMIT -> insertionSortWith(from, until, comparator = comparator)
        size <= SortConst.LEVEL_2_LIMIT -> mergeSortWith(from, until, comparator = comparator)
        else -> quickSortWith(from, until, comparator = comparator)
    }
}
fun CharArray.fastSortWith(from: Int = 0, until: Int = size, comparator: (n1: Char, n2: Char) -> Int) =
    asArrangeable().fastSortWith(from, until, comparator)
fun ByteArray.fastSortWith(from: Int = 0, until: Int = size, comparator: (n1: Byte, n2: Byte) -> Int) =
    asArrangeable().fastSortWith(from, until, comparator)
fun ShortArray.fastSortWith(from: Int = 0, until: Int = size, comparator: (n1: Short, n2: Short) -> Int) =
    asArrangeable().fastSortWith(from, until, comparator)
fun IntArray.fastSortWith(from: Int = 0, until: Int = size, comparator: (n1: Int, n2: Int) -> Int) =
    asArrangeable().fastSortWith(from, until, comparator)
fun LongArray.fastSortWith(from: Int = 0, until: Int = size, comparator: (n1: Long, n2: Long) -> Int) =
    asArrangeable().fastSortWith(from, until, comparator)
fun FloatArray.fastSortWith(from: Int = 0, until: Int = size, comparator: (n1: Float, n2: Float) -> Int) =
    asArrangeable().fastSortWith(from, until, comparator)
fun DoubleArray.fastSortWith(from: Int = 0, until: Int = size, comparator: (n1: Double, n2: Double) -> Int) =
    asArrangeable().fastSortWith(from, until, comparator)
fun BooleanArray.fastSortWith(from: Int = 0, until: Int = size, comparator: (n1: Boolean, n2: Boolean) -> Int) =
    asArrangeable().fastSortWith(from, until, comparator)

// */

fun <T: Comparable<*>> Array<T>.fastSorted(from: Int = 0, until: Int = size, order: Order = Order.ASC): Array<T> =
    copyOfRange(from, until).apply { fastSort(order = order) }
fun <T> Array<T>.fastSortedWith(from: Int = 0, until: Int = size, comparator: (n1: T, n2: T) -> Int): Array<T> =
    copyOfRange(from, until).apply { fastSortWith(comparator = comparator) }
fun <T, R: Comparable<*>> Array<T>.fastSortedBy(from: Int = 0, until: Int = size, order: Order= Order.ASC, toComparableFun: (T) -> R): Array<T> =
    copyOfRange(from, until).apply { fastSortBy(order = order, toComparableFun = toComparableFun) }
/*
fun <T> Array<T>.fastSorted(from: Int = 0, until: Int = size, comparator: (n1: T, n2: T) -> Int): Array<T> =
    copyOfRange(from, until).apply { fastSortBy(toComparableFun = comparator) }
 */
fun CharArray.fastSorted(from: Int = 0, until: Int = size, order: Order = Order.ASC): CharArray =
    copyOfRange(from, until).apply { fastSort(order = order) }
fun ByteArray.fastSorted(from: Int = 0, until: Int = size, order: Order = Order.ASC): ByteArray =
    copyOfRange(from, until).apply { fastSort(order = order) }
fun ShortArray.fastSorted(from: Int = 0, until: Int = size, order: Order = Order.ASC): ShortArray =
    copyOfRange(from, until).apply { fastSort(order = order) }
fun IntArray.fastSorted(from: Int = 0, until: Int = size, order: Order = Order.ASC): IntArray =
    copyOfRange(from, until).apply { fastSort(order = order) }
fun LongArray.fastSorted(from: Int = 0, until: Int = size, order: Order = Order.ASC): LongArray =
    copyOfRange(from, until).apply { fastSort(order = order) }
fun FloatArray.fastSorted(from: Int = 0, until: Int = size, order: Order = Order.ASC): FloatArray =
    copyOfRange(from, until).apply { fastSort(order = order) }
fun DoubleArray.fastSorted(from: Int = 0, until: Int = size, order: Order = Order.ASC): DoubleArray =
    copyOfRange(from, until).apply { fastSort(order = order) }
fun BooleanArray.fastSorted(from: Int = 0, until: Int = size, order: Order = Order.ASC): BooleanArray =
    copyOfRange(from, until).apply { fastSort(order = order) }
fun <T: Comparable<*>> List<T>.fastSorted(from: Int = 0, until: Int = size, order: Order = Order.ASC): List<T> =
    copy(from, until).asMutableList().apply { fastSort(order = order) }
fun <T> List<T>.fastSortedWith(from: Int = 0, until: Int = size, comparator: (n1: T, n2: T) -> Int): List<T> =
    copy(from, until).asMutableList().apply { fastSortWith(comparator = comparator) }
fun <T, R: Comparable<*>> List<T>.fastSortedBy(
    from: Int = 0, until: Int = size,
    order: Order= Order.ASC, toComparableFun: (T) -> R
): List<T> =
    copy(from, until).asMutableList().apply { fastSortBy(order, toComparableFun) }



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