package sidev.lib.collection.comparator

import sidev.lib.`val`.SuppressLiteral


internal object NaturalOrderComparator: Comparator<Comparable<Any>> {
    override fun compare(a: Comparable<Any>, b: Comparable<Any>): Int = a.compareTo(b)
    @Suppress(SuppressLiteral.VIRTUAL_MEMBER_HIDDEN)
    fun reversed(): ReversedOrderComparator= ReversedOrderComparator
}

internal object ReversedOrderComparator: Comparator<Comparable<Any>> {
    override fun compare(a: Comparable<Any>, b: Comparable<Any>): Int = b.compareTo(a)
    @Suppress(SuppressLiteral.VIRTUAL_MEMBER_HIDDEN)
    fun reversed(): NaturalOrderComparator= NaturalOrderComparator
}


/*
=============================
Number Comparator - Berguna untuk membandingkan sesama `Number` namun beda class.
  Contoh: `Int` dengan `Double`, maka cara teraman adalah dg mengubahnya menjadi `Double`.
=============================
 */

internal object NumberNaturalOrderComparator: Comparator<Comparable<Number>> {
    override fun compare(a: Comparable<Number>, b: Comparable<Number>): Int =
        (a as Number).toDouble().compareTo((b as Number).toDouble())
    @Suppress(SuppressLiteral.VIRTUAL_MEMBER_HIDDEN)
    fun reversed(): NumberReversedOrderComparator= NumberReversedOrderComparator
}

internal object NumberReversedOrderComparator: Comparator<Comparable<Number>> {
    override fun compare(a: Comparable<Number>, b: Comparable<Number>): Int =
        (b as Number).toDouble().compareTo((a as Number).toDouble())
    @Suppress(SuppressLiteral.VIRTUAL_MEMBER_HIDDEN)
    fun reversed(): NumberNaturalOrderComparator= NumberNaturalOrderComparator
}


internal class SelectorComparator<T, R: Comparable<R>>(val selector: (T) -> R): Comparator<T> {
    constructor(selector: (T) -> R, origin: Comparator<R>): this(selector){
        compareFun= { n1, n2 -> origin.compare(n1, n2) }
    }
    private var compareFun: (n1: R, n2: R) -> Int = { n1, n2 -> n1.compareTo(n2) }
    override fun compare(a: T, b: T): Int = compareFun(selector(a), selector(b))
}