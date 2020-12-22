package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.`val`.NumberOperationMode
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.console.prine
import sidev.lib.exception.IllegalArgExc
import sidev.lib.number.*
import sidev.lib.reflex.getHashCode

interface NumberProgression<T>: OperableProgression<T, T> where T: Number, T: Comparable<T>{
    override val operationMode: NumberOperationMode
    val reverseOperation: Boolean
    override fun iterator(): NumberProgressionIterator<T>

    fun operate(
        e: T, step: T, //operationMode: NumberOperationMode = this.operationMode,
        reverseOperation: Boolean = this.reverseOperation
    ): T
    override operator fun contains(e: T): Boolean {
        val pair= smallBigPair
//        val firstSmall: T
//        val step: T
        val isInDomain= if(
            operationMode != NumberOperationMode.MULTIPLICATIONAL
            || first * last >= 0
        ){
//            step= this.step
//            firstSmall= pair.first
            pair.first <= e && e <= pair.second
        } else {
            val firstAbs= pair.first.absoluteValueCast
            val lastAbs= pair.second.absoluteValueCast
            val small: T
            val big= if(lastAbs >= firstAbs) {
                small= firstAbs
                lastAbs
            } else {
                small= lastAbs
                firstAbs
            }
            val eAbs= e.absoluteValue
//            firstSmall= small
//            step= this.step.absoluteValueCast
            small <= eAbs && eAbs <= big
        }
//        prine("contains e= $e pair= $pair step= $step isInDomain= $isInDomain") //firstSmall= $firstSmall
//        prine("4 root -2= ${4 root -2} 4 root 2= ${4 root 2} 2.0 % 1 == 0 = ${2.0 % 1 == 0.0} 0.0625 pow 0.5 = ${0.0625 pow 0.5} 0.0625 log 0.25 = ${0.0625 log 0.25}")


        fun containsIteratively(): Boolean {
            val reverse= step < 1 || reverseOperation
            val compareLimitFun: (e1: T, e: T) -> Boolean = if(!reverse) ::moreThan else ::lessThan
            for(e1 in this){
                if(e1.compareTo(e) == 0)
                    return true
                if(compareLimitFun(e1, e))
                    return false
            }
            return false
        }

        return isInDomain
        && when(operationMode){
            NumberOperationMode.INCREMENTAL -> operate(e, -pair.first, reverseOperation = reverseOperation) % step == 0 //.also { prine("INC it= $it") }
            NumberOperationMode.MULTIPLICATIONAL -> {
                val operatedVal= operate(e, first.absoluteValueCast, reverseOperation = !reverseOperation).let {
                    if(step > 0) it else it.absoluteValue
                }
                val logRes= step.absoluteValue log operatedVal
                val resSignum= if(step > 0) 1 else (-1 pow logRes).toInt()
                (logRes % 1).compareTo(0) == 0 && e.signum == resSignum
            }
            else -> containsIteratively() // Karena aturan untuk deret EXPONENTIAL masih belum diketahui.
        }
    }
}


internal class NumberProgressionImpl<T>(
    first: T, last: T, step: T,
    operationMode: NumberOperationMode = NumberOperationMode.INCREMENTAL,
    override val reverseOperation: Boolean = false,
    startExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
    endExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
) : OperableProgressionImpl<T, T>(first, last, step, operationMode, startExclusiveness, endExclusiveness, false),
    NumberProgression<T> where T: Number, T: Comparable<T> {

    init {
        val order= getProgressingOrder(operationMode, first, last).run {
            if(reverseOperation) reverse else this
        }
        if(!step.isProgressingFactor(operationMode, order, true))
            throw IllegalArgExc(
                paramExcepted = arrayOf("step"),
                detailMsg = "Param `step` ($step) dapat menyebabkan infinite loop. `operationMode` ($operationMode), `first` ($first), `last` ($last)"
            )
/*
        if(
            step == 0 ||
            (step == 1 || step == -1)
            && (operationMode == NumberOperationMode.MULTIPLICATIONAL || operationMode == NumberOperationMode.EXPONENTIAL)
        )
            throw IllegalArgumentException("step= $step dan operationMode= $operationMode maka akan terjadi infinite loop.")
 */
    }

    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    override fun operate(
        e: T, step: T, //operationMode: NumberOperationMode,
        reverseOperation: Boolean
    ): T = when(operationMode){
        NumberOperationMode.INCREMENTAL -> if(!reverseOperation) e + step else e - step
        NumberOperationMode.MULTIPLICATIONAL -> if(!reverseOperation) e * step else e / step
        NumberOperationMode.EXPONENTIAL -> if(!reverseOperation) e powCast step else e rootCast step
    } as T
    override fun operate(e: T, step: T): T = operate(e, step, reverseOperation)

    override fun iterator(): NumberProgressionIterator<T> = NumberProgressionIterator(
        first, last, step, operationMode, reverseOperation, startExclusiveness, endExclusiveness
    )

    override fun hashCode(): Int = getHashCode(super.hashCode(), operationMode, calculateOrder = false)

    override fun equals(other: Any?): Boolean = other is NumberProgression<*>
            && other.operationMode == operationMode && super.equals(other)

    override fun toString(): String {
        val sign= when {
            operationMode == NumberOperationMode.INCREMENTAL -> ""
            !reverseOperation -> operationMode.sign
            else -> operationMode.reverseSign
        }
        return "$first..>> $sign$step >>..$last"
    }
}