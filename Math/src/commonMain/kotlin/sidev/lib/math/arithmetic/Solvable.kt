package sidev.lib.math.arithmetic

/**
 * Interface yg dapat melakukan proses penyelesaian, termasuk [Equation] dan [System].
 */
interface Solvable {
    fun solve(vararg varArg: Pair<String, Calculable>): List<SimpleEquation>
    fun solveWithCalc(vararg varArg: Pair<Calculable, Calculable>): List<SimpleEquation>
//    fun solve(): List<SimpleEquation> = solveWithCalc(*arrayOf<Pair<String, Calculable>>())
}