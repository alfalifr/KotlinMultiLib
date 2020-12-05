package sidev.lib.algo

import sidev.lib.`val`.SuppressLiteral

fun <T, R: Comparable<R>> List<T>.binarySearchBy(
        e: T, fromIndex: Int= 0, toIndex: Int= size, toComparableFun: (T) -> R
): Int = binarySearch(fromIndex, toIndex) { toComparableFun(it).compareTo(toComparableFun(e)) }

fun <T: Comparable<T>> List<T>.binarySearch(
        e: T, fromIndex: Int= 0, toIndex: Int= size
): Int = binarySearch(fromIndex, toIndex) { it.compareTo(e) }

fun <T> List<T>.binarySearch(
        fromIndex: Int= 0, toIndex: Int= size, compareFun: (midVal: T) -> Int
): Int {
    sidev.lib.collection.rangeCheck(size, fromIndex, toIndex)

    var index= -1
    var low= fromIndex
    var high= toIndex -1

    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    while(low <= high){
        val mid= (low + high) ushr 1
        val midVal= this[mid]
        val cmp= compareFun(midVal)
        when {
            cmp > 0 -> high= mid -1
            cmp < 0 -> low= mid +1
            else -> index= mid
        }
    }
    return index
}