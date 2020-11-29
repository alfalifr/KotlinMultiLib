package sidev.lib.math.arithmetic

import sidev.lib.number.compareTo

interface Equation {
    enum class Sign(val compareFun: (Number, Number) -> Boolean, vararg val symbol: String): Operationable<Boolean> {
        EQUAL({ n1, n2 -> n1 == n2 }, "="),
        LESS_THAN({ n1, n2 -> n1 < n2 }, "<"),
        MORE_THAN({ n1, n2 -> n1 > n2 }, ">"),
        LESS_THAN_EQUAL({ n1, n2 -> n1 <= n2 }, "<="),
        MORE_THAN_EQUAL({ n1, n2 -> n1 >= n2 }, ">=");

        override fun doOperation(n1: Number, n2: Number): Boolean = compareFun(n1, n2)
    }

    /**
     * [Block] yang membentuk `this` Equation.
     * Minimal terdiri dari 2 [Block].
     */
    val blocks: List<Block>

    /**
     * Tanda yang memisahkan [blocks].
     * Jml [signs] harus n-1 jml [blocks].
     */
    val signs: List<Sign>
/*
    fun solve(): List<Pair<String, Number>> {

    }
// */
}