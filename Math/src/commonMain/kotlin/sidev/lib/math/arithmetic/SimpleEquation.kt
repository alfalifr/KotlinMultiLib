package sidev.lib.math.arithmetic

import sidev.lib.annotation.ComputedProperty
import sidev.lib.number.compareTo
import sidev.lib.number.div
import sidev.lib.reflex.getHashCode
import sidev.lib.collection.forEachIndexed
import sidev.lib.console.prine
import sidev.lib.exception.IllegalStateExc

/**
 * [Equation] yang hanya memiliki 2 block, yaitu [left] dan [right] dg 1 tanda [sign].
 */
interface SimpleEquation: Equation {
    /**
     * Hanya berisi dari 2 [Block], yaitu [left] dan [right].
     */
    @ComputedProperty
    override val blocks: List<Calculable>
        get() = listOf(left, right)

    /**
     * Hanya berisi 1, yaitu [sign] yang memisahkan [left] dan [right].
     */
    @ComputedProperty
    override val signs: List<Equation.Sign>
        get() = listOf(sign)

    val left: Calculable
    val right: Calculable
    val sign: Equation.Sign

    override fun test(vararg varArg: Pair<String, Number>): Boolean = sign(
        left.calculate(*varArg), right.calculate(*varArg)
    )
}


internal class SimpleEquationImpl(
    left: Calculable,
    right: Calculable,
    override val sign: Equation.Sign = Equation.Sign.EQUAL
): SimpleEquation {

    override var left: Calculable= left
        private set
    override var right: Calculable= right
        private set

    /**
     * Menyelesaikan persamaan / pertidak-samaan yang ada pada [blocks] sehingga menghasilkan
     * penyelesaian berupa persamaan [Variable] dg [Calculable].
     */
    override fun solve(
        varName: String?,
        vararg varArg: Pair<String, Calculable>
    ): List<SimpleEquation> {

        when(val left= left){
            is Constant<*> -> when(val right= right){
                is Variable<*> -> return listOf(
                    simpleEquationOf(
                        variableOf(right.name, 1),
                        constantOf(left.number / right.coeficient.toDouble()),
                        sign
                    )
                )
                is Constant<*> -> return listOf()
            }
            is Variable<*> -> when(val right= right){
                is Constant<*> -> return listOf(
                    simpleEquationOf(
                        variableOf(left.name, 1),
                        constantOf(right.number / left.coeficient.toDouble()),
                        sign
                    )
                )
                is Variable<*> -> {
                    val firstEl: Variable<*>
                    val solvedVar= if(varName == null) {
                        firstEl= right
                        left
                    } else {
                        if(right.name == varName) {
                            firstEl= left
                            right
                        } else {
                            firstEl= right
                            left
                        }
                    }

                    return listOf(
                        simpleEquationOf(
                            variableOf(solvedVar.name, 1),
                            blockOf(firstEl).addOperation(constantOf(solvedVar.coeficient), Operation.DIVIDES),
                            sign
                        )
                    )
                }
            }
        }

        val left= if(left is Block){
            (left as Block).simply().also { left= it }
        } else left

        val right= if(right is Block){
            (right as Block).simply().also { right= it }
        } else right


        // 2x - 3 = 5
        // 2x = 8
        // x = 4

        // -3 + 2x = 5
        // 2x = 5 + 3

        // 2x - 3 = 5y
        // 2x = 5y + 3
        // x = (5y + 3) / 2

        // (2x - 3) * 2 = 5y
        // 2x - 3 = 5y / 2
        // 2x = (5y / 2) + 3
        // x = ((5y / 2) + 3) / 2

        // (2x - 3) * ((5x^2) + 2) = 5y
        // (2x - 3) * ((5xx) + 2) = 5y
        // 2(x^3) + 10x - 15(x^2) -6 = 5y
        // 2(x^3) + 10x - 15(x^2) -6 = 5y

        // 2x - 3 + (x^2) = (5y + (y^2)) * y
        // 2x + (x^2) = ((5y + (y^2)) * y) + 3
        // 2x + (x.x) = ((5y + (y^2)) * y) + 3
        // x.(2 + (x)) = ((5y + (y^2)) * y) + 3

        // 2x + (1/x) = ((5y + (y^2)) * y) + 3
        // x.(2 + (1/(x^2))) = ((5y + (y^2)) * y) + 3

        val solvedVarName= if(varName != null && (left.hasVarName(varName) || right.hasVarName(varName))){
            varName
        } else {
            ////////
            val varNameCounts= left.varNameCounts + right.varNameCounts

            var minVarName= ""
            var countItr= Int.MAX_VALUE
            varNameCounts.forEach { (name, count) ->
                if(countItr > count){
                    countItr= count
                    minVarName= name
                }
            }
            minVarName
        }

        prine("Equation.solve() solvedVarName= $solvedVarName")

        val otherCalc: Calculable
        val calcHasVarName= if(left.hasVarName(solvedVarName)) {
            otherCalc= right.clone_()
            left.clone_()
        }  else {
            otherCalc= left.clone_()
            right.clone_()
        }

        var leftCalc: Calculable= calcHasVarName
        var rightCalc: Calculable= otherCalc

        when(calcHasVarName){
            is Block -> {
                val otherBlock = if (otherCalc is Block) otherCalc else blockOf(otherCalc)

                val removedElsInd = mutableListOf<Int>()
                calcHasVarName.elements.forEachIndexed(reversed = true) { i, e1 ->
                    if (!e1.hasVarName(solvedVarName)) {
                        otherBlock.addOperation(
                            e1,
                            if (i > 0) calcHasVarName.operations[i - 1].opposite
                            else {
                                val num = e1.numberComponent() ?: 1
                                if (num >= 0) Operation.PLUS
                                else Operation.MINUS
                            },
                            prioritizePrecedence = false
                        )
                        removedElsInd += i
                    }
                }
                for (i in removedElsInd)
                    calcHasVarName.removeOperationAt(i)

                prine("SimpleEquation.solve() SBLUM AKHIR calcHasVarName= $calcHasVarName")

                leftCalc = calcHasVarName.simply()
                if (leftCalc is Variable<*> && leftCalc.coeficient != 1) {
                    otherBlock.addOperation(
                        constantOf(leftCalc.coeficient),
                        Operation.DIVIDES,
                        prioritizePrecedence = false
                    )
                    leftCalc = variableOf(leftCalc.name, 1)
                }

                prine("SimpleEquation.solve() SBLUM AKHIR leftCalc:class= ${leftCalc::class}")
                prine("SimpleEquation.solve() SBLUM AKHIR leftCalc= $leftCalc")
                rightCalc = otherBlock.simply()
            }
            is Variable<*> -> {
                leftCalc= variableOf(calcHasVarName.name, 1)
                val otherBlock= if(otherCalc is Block) otherCalc else blockOf(otherCalc)
                rightCalc= otherBlock
                    .addOperation(constantOf(calcHasVarName.coeficient), Operation.DIVIDES, prioritizePrecedence = false)
                    .simply()
            }
            else -> throw IllegalStateExc(currentState = "`calcHasVarName` brp Constant<*>", expectedState = "`calcHasVarName` tidak mungkin brp Constant<*>")
        }


        return listOf(
            simpleEquationOf(leftCalc, rightCalc, sign)
        )
    }

    override fun hashCode(): Int = getHashCode(left, right, sign, calculateOrder = false)
    override fun equals(other: Any?): Boolean = when(other){
        is Equation -> hashCode() == other.hashCode()
        else -> super.equals(other)
    }
    override fun toString(): String = "$left $sign $right"
}