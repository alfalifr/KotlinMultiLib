package sidev.lib.algo

import sidev.lib.`val`.Order
import sidev.lib.`val`.SuppressLiteral
//import sidev.lib.collection.comparator.NaturalOrderComparator
import sidev.lib.collection.comparator.naturalComparator
import sidev.lib.collection.comparator.numberNaturalComparator
import sidev.lib.structure.data.Indexable
import sidev.lib.structure.prop.SizeProp


fun <T, E, R: Comparable<*>> T.binarySearchBy(
    e: E, from: Int= 0, until: Int= size,
    order: Order= Order.ASC,
    withNumberSafety: Boolean= false,
    toComparableFun: (E) -> R
): Int where T: Indexable<E>, T: SizeProp //= binarySearch(from, until) { (toComparableFun(it) as Comparable<Any?>).compareTo(toComparableFun(e)) }
{
    val comparator= if(!withNumberSafety) naturalComparator<R>() else numberNaturalComparator<Double>() as Comparator<R>
    val comparableE= toComparableFun(e)
    return binarySearch(from, until, order) { comparator.compare(toComparableFun(it), comparableE) }
}

fun <T, E: Comparable<*>> T.binarySearch(
    e: E, from: Int= 0, until: Int= size,
    order: Order= Order.ASC,
    withNumberSafety: Boolean= false
): Int where T: Indexable<E>, T: SizeProp {
    val comparator= if(!withNumberSafety) naturalComparator<E>() else numberNaturalComparator<Double>() as Comparator<E>
    return binarySearch(from, until, order) { comparator.compare(it, e) }
}

fun <T, E> T.binarySearch(
    e: E, comparator: Comparator<E>,
    from: Int= 0, until: Int= size,
//    order: Order= Order.ASC
): Int where T: Indexable<E>, T: SizeProp = binarySearch(from, until) { comparator.compare(it, e) }

fun <T, E> T.binarySearch(
    from: Int= 0, until: Int= size,
    order: Order= Order.ASC,
    compareFun: (midVal: E) -> Int
): Int where T: Indexable<E>, T: SizeProp {
    sidev.lib.collection.rangeCheck(size, from, until)

    val resComp= if(order == Order.ASC) naturalOrder<Int>() else reverseOrder()
    var index= -1
    var low= from
    var high= until -1

    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    while(low <= high){
        val mid= (low + high) ushr 1
        val midVal= this[mid]
        val cmp= compareFun(midVal)
        when {
            resComp.compare(cmp, 0) == 0 -> {
                index = mid
                break
            }
            low == high -> return -1
            resComp.compare(cmp, 0) > 0 -> high= mid -1
            resComp.compare(cmp, 0) < 0 -> low= mid +1
        }
    }
    return index
}





fun <T, R: Comparable<R>> List<T>.binarySearchBy(
    e: T, from: Int= 0, until: Int= size,
    order: Order= Order.ASC,
    withNumberSafety: Boolean= false,
    toComparableFun: (T) -> R
): Int //= binarySearch(from, until) { toComparableFun(it).compareTo(toComparableFun(e)) }
{
    val comparator= if(!withNumberSafety) naturalComparator<R>() else numberNaturalComparator<Double>() as Comparator<R>
    val comparableE= toComparableFun(e)
    return binarySearch(from, until, order) { comparator.compare(toComparableFun(it), comparableE) }
}

fun <T: Comparable<T>> List<T>.binarySearch(
    e: T, from: Int= 0, until: Int= size,
    order: Order= Order.ASC,
    withNumberSafety: Boolean= false
): Int// = binarySearch(from, until) { it.compareTo(e) }
{
    val comparator= if(!withNumberSafety) naturalComparator<T>() else numberNaturalComparator<Double>() as Comparator<T>
    return binarySearch(from, until, order) { comparator.compare(it, e) }
}

fun <T> List<T>.binarySearch(
    e: T, comparator: Comparator<T>,
    from: Int= 0, until: Int= size
): Int = binarySearch(from, until) { comparator.compare(it, e) }

fun <T> List<T>.binarySearch(
    from: Int= 0, until: Int= size,
    order: Order= Order.ASC,
    compareFun: (midVal: T) -> Int
): Int {
    sidev.lib.collection.rangeCheck(size, from, until)

    val resComp= if(order == Order.ASC) naturalOrder<Int>() else reverseOrder()
    var index= -1
    var low= from
    var high= until -1

    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    while(low <= high){
        val mid= (low + high) ushr 1
        val midVal= this[mid]
        val cmp= compareFun(midVal)
        when {
            resComp.compare(cmp, 0) == 0 -> {
                index = mid
                break
            }
            low == high -> return -1
            resComp.compare(cmp, 0) > 0 -> high= mid -1
            resComp.compare(cmp, 0) < 0 -> low= mid +1
        }
    }
    return index
}



fun <T, R: Comparable<R>> Array<T>.binarySearchBy(
    e: T, from: Int= 0, until: Int= size,
    order: Order= Order.ASC,
    withNumberSafety: Boolean= false,
    toComparableFun: (T) -> R
): Int //= binarySearch(from, until) { toComparableFun(it).compareTo(toComparableFun(e)) }
{
    val comparator= if(!withNumberSafety) naturalComparator<R>() else numberNaturalComparator<Double>() as Comparator<R>
    val comparableE= toComparableFun(e)
    return binarySearch(from, until, order) { comparator.compare(toComparableFun(it), comparableE) }
}

fun <T: Comparable<T>> Array<T>.binarySearch(
    e: T, from: Int= 0, until: Int= size,
    order: Order= Order.ASC,
    withNumberSafety: Boolean= false
): Int //= binarySearch(from, until) { it.compareTo(e) }
{
    val comparator= if(!withNumberSafety) naturalComparator<T>() else numberNaturalComparator<Double>() as Comparator<T>
    return binarySearch(from, until, order) { comparator.compare(it, e) }
}

fun <T> Array<T>.binarySearch(
    e: T, comparator: Comparator<T>,
    from: Int= 0, until: Int= size
): Int  = binarySearch(from, until) { comparator.compare(it, e) }

fun <T> Array<T>.binarySearch(
    from: Int= 0, until: Int= size,
    order: Order= Order.ASC,
    compareFun: (midVal: T) -> Int
): Int {
    sidev.lib.collection.rangeCheck(size, from, until)

    val resComp= if(order == Order.ASC) naturalOrder<Int>() else reverseOrder()
    var index= -1
    var low= from
    var high= until -1

    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    while(low <= high){
        val mid= (low + high) ushr 1
        val midVal= this[mid]
        val cmp= compareFun(midVal)
        when {
            resComp.compare(cmp, 0) == 0 -> {
                index = mid
                break
            }
            low == high -> return -1
            resComp.compare(cmp, 0) > 0 -> high= mid -1
            resComp.compare(cmp, 0) < 0 -> low= mid +1
        }
    }
    return index
}