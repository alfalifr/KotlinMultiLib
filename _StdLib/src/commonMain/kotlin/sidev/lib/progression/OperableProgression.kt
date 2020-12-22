package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.`val`.NumberOperationMode
import sidev.lib.`val`.Order
import sidev.lib.exception.IllegalArgExc
import sidev.lib.number.isPositive
import sidev.lib.number.isProgressingFactor

interface OperableProgression<T: Comparable<T>, S: Number>: StepProgression<T, S> {
    val operationMode: NumberOperationMode
    fun operate(e: T, step: S): T //operationMode: NumberOperationMode = this.operationMode
}

internal abstract class OperableProgressionImpl<T: Comparable<T>, S: Number>(
    first: T, last: T, step: S,
    final override val operationMode: NumberOperationMode,
    startExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
    endExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
    checkConstraintFirst: Boolean = true
): StepProgressionImpl<T, S>(first, last, step, startExclusiveness, endExclusiveness, false), OperableProgression<T, S> {
    init {
        if(checkConstraintFirst)
            if(!step.isProgressingFactor(operationMode, if(first <= last) Order.ASC else Order.DESC, true))
                throw IllegalArgExc(
                    paramExcepted = arrayOf("step"),
                    detailMsg = "Param `step` dapat menyebabkan infinite loop."
                )
    }
}