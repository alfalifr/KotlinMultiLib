package sidev.lib.collection

import sidev.lib.`val`.Order


/*
===============
FastSort - Tidak menjamin stabilitas
===============
 */

expect fun <T: Comparable<*>> Array<T>.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun <T> Array<T>.fastSortBy(from: Int = 0, until: Int = size, comparator: (n1: T, n2: T) -> Int)
expect fun CharArray.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun ByteArray.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun ShortArray.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun IntArray.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun LongArray.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun FloatArray.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun DoubleArray.fastSort(from: Int = 0, until: Int = size, order: Order = Order.ASC)
expect fun <T: Comparable<*>> MutableList<T>.fastSort(order: Order = Order.ASC, withNumberSafety: Boolean = false)
//expect fun <T: Comparable<*>> MutableList<T>.fastSortWith(c: Comparator<in T>)
expect fun <T> MutableList<T>.fastSortBy(comparator: (n1: T, n2: T) -> Int)


fun <T: Comparable<*>> Array<T>.fastSorted(from: Int = 0, until: Int = size, order: Order = Order.ASC): Array<T> =
    copyOfRange(from, until).apply { fastSort(order = order) }
fun <T> Array<T>.fastSorted(from: Int = 0, until: Int = size, comparator: (n1: T, n2: T) -> Int): Array<T> =
    copyOfRange(from, until).apply { fastSortBy(comparator = comparator) }
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
fun <T: Comparable<*>> List<T>.fastSorted(from: Int = 0, until: Int = size, order: Order = Order.ASC): List<T> =
    copy(from, until).asMutableList().apply { fastSort(order = order) }
fun <T: Comparable<*>> List<T>.fastSortedBy(from: Int = 0, until: Int = size, comparator: (n1: T, n2: T) -> Int): List<T> =
    copy(from, until).asMutableList().apply { fastSortBy(comparator = comparator) }



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