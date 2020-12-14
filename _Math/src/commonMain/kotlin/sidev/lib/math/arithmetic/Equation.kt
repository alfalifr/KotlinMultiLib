package sidev.lib.math.arithmetic

import sidev.lib.collection.toArrayOf
import sidev.lib.number.compareTo

interface Equation: Solvable {
    enum class Sign(val compareFun: (Number, Number) -> Boolean, vararg val symbol: String): Operationable<Boolean> {
        EQUAL({ n1, n2 -> n1 == n2 }, "="),
        LESS_THAN({ n1, n2 -> n1 < n2 }, "<"),
        MORE_THAN({ n1, n2 -> n1 > n2 }, ">"),
        LESS_THAN_EQUAL({ n1, n2 -> n1 <= n2 }, "<="),
        MORE_THAN_EQUAL({ n1, n2 -> n1 >= n2 }, ">=");

        val opposite: Sign get()= when(this) {
            LESS_THAN -> MORE_THAN
            MORE_THAN -> LESS_THAN
            LESS_THAN_EQUAL -> MORE_THAN_EQUAL
            MORE_THAN_EQUAL -> LESS_THAN_EQUAL
            EQUAL -> EQUAL
//            else -> throw NotYetSupportedExc(accessedElement = this, detailMsg = "Tanda $this belum didefinisikan `opposite`-nya.")
        }
        override fun doOperation(n1: Number, n2: Number): Boolean = compareFun(n1, n2)
        override fun toString(): String = symbol.first().toString()
    }

    /**
     * [Calculable] yang membentuk `this` Equation.
     * Minimal terdiri dari 2 [Calculable], seperti [Variable], [Constant], atau [Block].
     */
    val blocks: List<Calculable>

    /**
     * Tanda yang memisahkan [blocks].
     * Jml [signs] harus n-1 jml [blocks].
     */
    val signs: List<Sign>

    /*
    2+3 = 5 = 4+1
     */

    /**
     * Menyelesaikan persamaan / pertidak-samaan yang ada pada [blocks] sehingga menghasilkan
     * penyelesaian berupa persamaan [Variable] dg [Calculable].
     */
    fun solve(
        varName: String? = null,
        vararg varArg: Pair<String, Calculable>
    ): List<SimpleEquation> = solveWithCalc(varName, *varArg.toArrayOf { variableForHash(it.first) to it.second })

    fun solveWithCalc(
        varName: String? = null,
        vararg varArg: Pair<Calculable, Calculable>
    ): List<SimpleEquation>

    override fun solveWithCalc(vararg varArg: Pair<Calculable, Calculable>): List<SimpleEquation> =
        solveWithCalc(null, *varArg)
    override fun solve(vararg varArg: Pair<String, Calculable>): List<SimpleEquation> =
        this.solve(null, *varArg)

    fun test(vararg varArg: Pair<String, Number>): Boolean {
        val blockItr= blocks.iterator()
        val signItr= signs.iterator()

        var res1= blockItr.next().calculate(*varArg)
        while(blockItr.hasNext()){
            val res2= blockItr.next().calculate(*varArg)
            if(!signItr.next()(res1, res2))
                return false
            res1= res2
        }
        return true
    }
}