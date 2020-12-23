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

    override fun iterator(): NumberProgressionIterator<T> = NumberProgressionIterator(
        first, last, step, operationMode, reverseOperation, startExclusiveness, endExclusiveness
    )

    fun operate(
        e: T, step: T, //operationMode: NumberOperationMode = this.operationMode,
        reverseOperation: Boolean = this.reverseOperation
    ): T

    override infix fun containsInRange(e: T): Boolean {
        val (leftPair, rightPair)= smallBigPairWithExclusiveness
//        val firstSmall: T
//        val step: T
        val left: Pair<T, Exclusiveness>
        val right: Pair<T, Exclusiveness>
        val usedE: T
        if(
            operationMode != NumberOperationMode.MULTIPLICATIONAL
            || (first * last >= 0 && step > 0)
        ){
//            step= this.step
//            firstSmall= pair.first
//            otherPair.second <= pair.second
            left= leftPair
            right= rightPair
            usedE= e
//            pair.first <= e && e <= pair.second
        } else {
            val firstAbs= leftPair.first.absoluteValueCast
            val lastAbs= rightPair.first.absoluteValueCast
            val smallPair: Pair<T, Exclusiveness>
            val bigPair= if(lastAbs >= firstAbs) {
                smallPair= leftPair.copy(firstAbs)
                rightPair.copy(lastAbs)
            } else {
                smallPair= rightPair.copy(lastAbs)
                leftPair.copy(firstAbs)
            }
            val eAbs= e.absoluteValueCast
            usedE= eAbs
            left= smallPair
            right= bigPair

//            firstSmall= small
//            step= this.step.absoluteValueCast
//            small <= eAbs && eAbs <= big
        }

//        val leftAbs= left.first.absoluteValue
//        val rightAbs= right.first.absoluteValue

        val moreThanFirst= left.first < usedE
                || left.second == Exclusiveness.INCLUSIVE && left.first == usedE
        val lessThanLast= usedE < right.first
                || right.second == Exclusiveness.INCLUSIVE && usedE == right.first
//        prine("containsInRange() e= $e usedE= $usedE moreThanFirst= $moreThanFirst lessThanLast= $lessThanLast left= $left right= $right")
        return moreThanFirst && lessThanLast
    }

    override operator fun contains(e: T): Boolean {
//        val pair= smallBigPair
//        val firstSmall: T
//        val step: T
        val isInRange= containsInRange(e)
        if(!isInRange) return false
        if(e == first) return startExclusiveness == Exclusiveness.INCLUSIVE
        if(e == last) return endExclusiveness == Exclusiveness.INCLUSIVE

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

//        prine("contains e= $e pair= $pair step= $step isInRange= $isInRange first= $first") //firstSmall= $firstSmall
//        prine("4 root -2= ${4 root -2} 4 root 2= ${4 root 2} 2.0 % 1 == 0 = ${2.0 % 1 == 0.0} 0.0625 pow 0.5 = ${0.0625 pow 0.5} 0.0625 log 0.25 = ${0.0625 log 0.25}")

        return isInRange
        && when(operationMode){
            NumberOperationMode.INCREMENTAL -> ((e -first) % step) == 0 //.also { prine("INC it= $it") }
            NumberOperationMode.MULTIPLICATIONAL -> {
                val operatedVal= (e.toDouble() / first.absoluteValue).let {
                    if(step > 0) it else it.absoluteValue
                }
                val step= if(reverseOperation) 1.0/step else step
                val logRes= (step.absoluteValue log operatedVal).let {
                    if(e.isFloatingType() || !reverseOperation) it else it.toFormatLike(e) /* Agar log yg dihasilkan pada Int reverse jadi bulat. */
                }
                val resSignum= if(step > 0) 1 else (-1 pow logRes).toInt() //Untuk menentukan tanda - atau +
//                prine("operatedVal= $operatedVal logRes= $logRes resSignum= $resSignum step= $step logRes % 1= ${logRes % 1}")
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
                detailMsg = "Param `step` ($step) dapat menyebabkan infinite loop. `operationMode` ($operationMode), `first` ($first), `last` ($last), `order` ($order)"
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