package sidev.lib.collection

import sidev.lib.algo.binarySearch


fun <T: Comparable<T>> List<T>.fastSearch(e: T, isListSorted: Boolean = false): Int =
    if(!isListSorted) indexOf(e) else binarySearch { it.compareTo(e) }
fun <T> List<T>.fastSearch(e: T, comparator: Comparator<T>?= null): Int =
    if(comparator == null) indexOf(e) else binarySearch(e, comparator)
/*
    else {
        if(comparator == null) throw IllegalArgExc(
            paramExcepted = arrayOf("comparator"),
            detailMsg = "List `this`='$this' bkn merupakan `List<Comparable>`, maka sediakan param `comparator`"
        )
        binarySearch(e, comparator)
    }
 */


fun <T: Comparable<T>> Array<T>.fastSearch(e: T, isListSorted: Boolean = false): Int =
    if(!isListSorted) indexOf(e) else binarySearch { it.compareTo(e) }
fun <T> Array<T>.fastSearch(e: T, comparator: Comparator<T>?= null): Int =
    if(comparator == null) indexOf(e) else binarySearch(e, comparator)
/*
    else {
        if(comparator == null) throw IllegalArgExc(
            paramExcepted = arrayOf("comparator"),
            detailMsg = "List `this`='$this' bkn merupakan `List<Comparable>`, maka sediakan param `comparator`"
        )
        binarySearch(e, comparator)
    }
 */