package sidev.lib.math.arithmetic

import sidev.lib.annotation.ComputedProperty
import sidev.lib.number.compareTo
import sidev.lib.number.div
import sidev.lib.reflex.getHashCode
import sidev.lib.console.prine
import sidev.lib.exception.IllegalArgExc
import sidev.lib.exception.IllegalStateExc

/**
 * [Equation] yang hanya memiliki 2 block, yaitu [left] dan [right] dg 1 tanda [sign].
 */
interface SimpleEquation: Equation {

    companion object {
        fun parse(equationStr: String): SimpleEquation {
            var signIndex= equationStr.indexOf('=')
            var sign= Equation.Sign.EQUAL

            val leftStr: String
            val rightStr: String

            if(signIndex > 0) {
                val startSignIndex= when(equationStr[signIndex-1]) {
                    '<' -> {
                        sign = Equation.Sign.LESS_THAN_EQUAL
                        signIndex -1
                    }
                    '>' -> {
                        sign = Equation.Sign.MORE_THAN_EQUAL
                        signIndex -1
                    }
                    else -> signIndex
                }
                leftStr= equationStr.substring(0, startSignIndex)
                rightStr= equationStr.substring(signIndex+1)
            } else {
                signIndex= equationStr.indexOf('<')
                if(signIndex > 0) {
                    sign= Equation.Sign.LESS_THAN
                } else {
                    signIndex= equationStr.indexOf('>')
                    if(signIndex < 0)
                        throw IllegalArgExc(
                            paramExcepted = arrayOf("equationStr"),
                            detailMsg = "Param `equationStr` tidak terdapat tanda ekuasi (=, <, >, <=, >=)."
                        )
                    sign= Equation.Sign.MORE_THAN
                }
                leftStr= equationStr.substring(0, signIndex)
                rightStr= equationStr.substring(signIndex+1)
            }

            val left= blockOf(leftStr).let {
                val els= it.elements
                if(els.size > 1) it else els.first()
            }
            val right= blockOf(rightStr).let {
                val els= it.elements
                if(els.size > 1) it else els.first()
            }

            return simpleEquationOf(left, right, sign)
        }
    }
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

    fun solveSimple(varName: String? = null, vararg varArg: Pair<Calculable, Calculable>): SimpleEquation?
    override fun solveWithCalc(varName: String?, vararg varArg: Pair<Calculable, Calculable>): List<SimpleEquation> =
        solveSimple(varName, *varArg).let {
            if(it != null) listOf(it) else listOf()
        }

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


    override fun solveSimple(
        varName: String?,
        vararg varArg: Pair<Calculable, Calculable>
    ): SimpleEquation? {
//        val replacedLeft= left.replaceVars(*varArg)
        val replacedRight= right.replaceCalcs(*varArg)

        prine("solveSImple() replacedRight= $replacedRight")

        when(val replacedLeft= left.replaceCalcs(*varArg)){
            is Constant<*> -> when(replacedRight){
                is Variable<*> -> return simpleEquationOf(
                    variableOf(replacedRight.name, 1),
                    constantOf(replacedLeft.number / replacedRight.coeficient.toDouble()),
                    if(sign == Equation.Sign.EQUAL || replacedRight.coeficient > 0) sign
                    else sign.opposite
                )
                is Constant<*> -> return null //listOf()
            }
            is Variable<*> -> when(replacedRight){
                is Constant<*> -> return simpleEquationOf(
                    variableOf(replacedLeft.name, 1),
                    constantOf(replacedRight.number / replacedLeft.coeficient.toDouble()),
                    if(sign == Equation.Sign.EQUAL || replacedLeft.coeficient > 0) sign
                    else sign.opposite
                )
                is Variable<*> -> {
                    val firstEl: Variable<*>
                    val solvedVar= if(varName == null) {
                        firstEl= replacedRight
                        replacedLeft
                    } else {
                        if(replacedRight.name == varName) {
                            firstEl= replacedLeft
                            replacedRight
                        } else {
                            firstEl= replacedRight
                            replacedLeft
                        }
                    }

                    return simpleEquationOf(
                        variableOf(solvedVar.name, 1),
                        blockOf(firstEl).addOperation(constantOf(solvedVar.coeficient), Operation.DIVIDES),
                        if(sign == Equation.Sign.EQUAL || solvedVar.coeficient > 0) sign
                        else sign.opposite
                    )
                }
            }
        }

        val usedLeft= (if(left is Block){
            (left as Block).simply().also { left= it }
        } else left)
            .replaceCalcs(*varArg)

        val usedRight= (if(right is Block){
            (right as Block).simply().also { right= it }
        } else replacedRight)
            .replaceCalcs(*varArg)

        prine("solveSImple() usedLeft= $usedLeft")
        prine("solveSImple() usedRight= $usedRight")


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

        val solvedVarName= if(varName != null && (usedLeft.hasVarName(varName) || usedRight.hasVarName(varName))){
            varName
        } else {
            ////////
            val varNameCounts= usedLeft.varNameCounts + usedRight.varNameCounts

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
        val calcHasVarName= if(usedLeft.hasVarName(solvedVarName)) {
            otherCalc= usedRight.clone_()
            usedLeft.clone_()
        }  else {
            otherCalc= usedLeft.clone_()
            usedRight.clone_()
        }

        var resLeft: Calculable= calcHasVarName
        var resRight: Calculable= otherCalc
        var resSign= sign

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
                                if (num >= 0) Operation.MINUS
                                else Operation.PLUS
                            },
                            prioritizePrecedence = false
                        )
                        removedElsInd += i
                    }
                }
                for (i in removedElsInd)
                    calcHasVarName.removeOperationAt(i)

                prine("SimpleEquation.solve() SBLUM AKHIR calcHasVarName= $calcHasVarName")

                resLeft = calcHasVarName.simply()
                if (resLeft is Variable<*> && resLeft.coeficient != 1) {
                    otherBlock.addOperation(
                        constantOf(resLeft.coeficient),
                        Operation.DIVIDES,
                        prioritizePrecedence = false
                    )
                    resSign= if(sign == Equation.Sign.EQUAL || resLeft.coeficient > 0) sign
                    else sign.opposite

                    resLeft = variableOf(resLeft.name, 1)
                }

                prine("SimpleEquation.solve() SBLUM AKHIR leftCalc:class= ${resLeft::class}")
                prine("SimpleEquation.solve() SBLUM AKHIR leftCalc= $resLeft")
                resRight = otherBlock.simply()
            }
            is Variable<*> -> {
                resLeft= variableOf(calcHasVarName.name, 1)
                val otherBlock= if(otherCalc is Block) otherCalc else blockOf(otherCalc)
                resRight= otherBlock
                    .addOperation(constantOf(calcHasVarName.coeficient), Operation.DIVIDES, prioritizePrecedence = false)
                    .simply()

                resSign= if(sign == Equation.Sign.EQUAL || calcHasVarName.coeficient > 0) sign
                else sign.opposite
            }
            else -> throw IllegalStateExc(currentState = "`calcHasVarName` brp Constant<*>", expectedState = "`calcHasVarName` tidak mungkin brp Constant<*>")
        }

        return simpleEquationOf(resLeft, resRight, resSign)
    }

    override fun hashCode(): Int = getHashCode(left, right, sign, calculateOrder = false)
    override fun equals(other: Any?): Boolean = when(other){
        is Equation -> hashCode() == other.hashCode()
        else -> super.equals(other)
    }
    override fun toString(): String = "$left $sign $right"
}