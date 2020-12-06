package sidev.lib.algo

import sidev.lib.`val`.SuppressLiteral

fun <T, R: Comparable<R>> List<T>.binarySearchBy(
    e: T, from: Int= 0, until: Int= size, toComparableFun: (T) -> R
): Int = binarySearch(from, until) { toComparableFun(it).compareTo(toComparableFun(e)) }

fun <T: Comparable<T>> List<T>.binarySearch(
        e: T, from: Int= 0, until: Int= size
): Int = binarySearch(from, until) { it.compareTo(e) }

fun <T> List<T>.binarySearch(
    e: T, comparator: Comparator<T>,
    from: Int= 0, until: Int= size
): Int = binarySearch(from, until) { comparator.compare(it, e) }

fun <T> List<T>.binarySearch(
        from: Int= 0, until: Int= size, compareFun: (midVal: T) -> Int
): Int {
    sidev.lib.collection.rangeCheck(size, from, until)

    var index= -1
    var low= from
    var high= until -1

    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    while(low <= high){
        val mid= (low + high) ushr 1
        val midVal= this[mid]
        val cmp= compareFun(midVal)
        when {
            cmp == 0 -> {
                index = mid
                break
            }
            low == high -> return -1
            cmp > 0 -> high= mid -1
            cmp < 0 -> low= mid +1
        }
    }
    return index
}



fun <T, R: Comparable<R>> Array<T>.binarySearchBy(
        e: T, from: Int= 0, until: Int= size, toComparableFun: (T) -> R
): Int = binarySearch(from, until) { toComparableFun(it).compareTo(toComparableFun(e)) }

fun <T: Comparable<T>> Array<T>.binarySearch(
        e: T, from: Int= 0, until: Int= size
): Int = binarySearch(from, until) { it.compareTo(e) }

fun <T> Array<T>.binarySearch(
    e: T, comparator: Comparator<T>,
    from: Int= 0, until: Int= size
): Int  = binarySearch(from, until) { comparator.compare(it, e) }

fun <T> Array<T>.binarySearch(
        from: Int= 0, until: Int= size, compareFun: (midVal: T) -> Int
): Int {
    sidev.lib.collection.rangeCheck(size, from, until)

    var index= -1
    var low= from
    var high= until -1

    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    while(low <= high){
        val mid= (low + high) ushr 1
        val midVal= this[mid]
        val cmp= compareFun(midVal)
        when {
            cmp == 0 -> {
                index = mid
                break
            }
            low == high -> return -1
            cmp > 0 -> high= mid -1
            cmp < 0 -> low= mid +1
        }
    }
    return index
}