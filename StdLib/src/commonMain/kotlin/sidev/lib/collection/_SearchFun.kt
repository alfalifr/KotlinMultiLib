package sidev.lib.collection

import sidev.lib.algo.binarySearch
import sidev.lib.exception.IllegalArgExc


fun <T: Comparable<T>> List<T>.fastIndexOf(e: T, isListSorted: Boolean = false): Int =
    if(!isListSorted) indexOf(e) else binarySearch { it.compareTo(e) }
fun <T> List<T>.fastIndexOf(e: T, isListSorted: Boolean = false, comparator: Comparator<T>?= null): Int =
    if(!isListSorted) indexOf(e)
    else {
        if(comparator == null) throw IllegalArgExc(
            paramExcepted = arrayOf("comparator"),
            detailMsg = "List `this`='$this' bkn merupakan `List<Comparable>`, maka sediakan param `comparator`"
        )
        binarySearch(e, comparator)
    }


fun <T: Comparable<T>> Array<T>.fastIndexOf(e: T, isListSorted: Boolean = false): Int =
    if(!isListSorted) indexOf(e) else binarySearch { it.compareTo(e) }
fun <T> Array<T>.fastIndexOf(e: T, isListSorted: Boolean = false, comparator: Comparator<T>?= null): Int =
    if(!isListSorted) indexOf(e)
    else {
        if(comparator == null) throw IllegalArgExc(
            paramExcepted = arrayOf("comparator"),
            detailMsg = "List `this`='$this' bkn merupakan `List<Comparable>`, maka sediakan param `comparator`"
        )
        binarySearch(e, comparator)
    }