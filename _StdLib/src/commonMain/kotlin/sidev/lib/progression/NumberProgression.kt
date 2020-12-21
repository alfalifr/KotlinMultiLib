package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.`val`.NumberOperationMode
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.number.*
import sidev.lib.range.rangeTo_
import sidev.lib.reflex.getHashCode

interface NumberProgression<T>: IntervalProgression<T, T> where T: Number, T: Comparable<T>{
    val operationMode: NumberOperationMode
    override fun iterator(): NumberProgressionIterator<T>
}


internal class NumberProgressionImpl<T>(
    first: T, last: T, step: T,
    override val operationMode: NumberOperationMode = NumberOperationMode.INCREMENTAL,
    startExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
    endExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
) : IntervalProgressionImpl<T, T>(first, last, step, startExclusiveness, endExclusiveness),
    NumberProgression<T> where T: Number, T: Comparable<T> {

    init {
        if(
            step == 0 ||
            (step == 1 || step == -1)
            && (operationMode == NumberOperationMode.MULTIPLICATIONAL || operationMode == NumberOperationMode.EXPONENTIAL)
        )
            throw IllegalArgumentException("step= $step dan operationMode= $operationMode maka akan terjadi infinite loop.")
    }

    override fun iterator(): NumberProgressionIterator<T> = object: NumberProgressionIterator<T>(
        first, last, step, startExclusiveness, endExclusiveness
    ){
        override fun hasNext(prev: T, next: T, last: T, exclusiveness: Exclusiveness): Boolean = when(operationMode){
            NumberOperationMode.INCREMENTAL ->
                if(exclusiveness == Exclusiveness.INCLUSIVE) {
                    if(step > 0) next <= last else next >= last
                } else {
                    if(step > 0) next < last else next > last
                }
            NumberOperationMode.MULTIPLICATIONAL -> when {
                step >= 1 || step <= -1 ->
                    if(exclusiveness == Exclusiveness.INCLUSIVE) {
                        next.absoluteValueCast <= last.absoluteValueCast as Number
                    } else {
                        next.absoluteValueCast < last.absoluteValueCast as Number
                    }
                else ->
                    if(exclusiveness == Exclusiveness.INCLUSIVE) {
                        next.absoluteValueCast >= last.absoluteValueCast as Number
                    } else {
                        next.absoluteValueCast > last.absoluteValueCast as Number
                    }
            }
            NumberOperationMode.EXPONENTIAL -> when {
                step >= 1 ->
                    if(exclusiveness == Exclusiveness.INCLUSIVE) {
                        next.absoluteValueCast <= last.absoluteValueCast as Number
                    } else {
                        next.absoluteValueCast < last.absoluteValueCast as Number
                    }
                step <= -1 ->
                    if(exclusiveness == Exclusiveness.INCLUSIVE) {
                        (if(next >= 1) next else 1.0 / next).absoluteValueCast <=
                                (if(last >= 1) last else 1.0 / last).absoluteValueCast
                    } else {
                        (if(next >= 1) next else 1.0 / next).absoluteValueCast <
                                (if(last >= 1) last else 1.0 / last).absoluteValueCast
                    }
                // Ini karena pangkat min sama dg 1/n, sehingga bentuk ini akan terus inverse pada progresi selanjutnya.
                step in @Suppress(SuppressLiteral.UNCHECKED_CAST)
                ((0.0.rangeTo_(1.0, endExclusiveness = Exclusiveness.EXCLUSIVE)) as ClosedRange<T>) ->
                    if(exclusiveness == Exclusiveness.INCLUSIVE) {
                        next.absoluteValueCast >= last.absoluteValueCast as Number
                    } else {
                        next.absoluteValueCast > last.absoluteValueCast as Number
                    }
                else ->
                    if(exclusiveness == Exclusiveness.INCLUSIVE) {
                        (if(next >= 1) next else 1.0 / next).absoluteValueCast >=
                                (if(last >= 1) last else 1.0 / last).absoluteValueCast
                    } else {
                        (if(next >= 1) next else 1.0 / next).absoluteValueCast >
                                (if(last >= 1) last else 1.0 / last).absoluteValueCast
                    }
                // Ini karena pangkat min sama dg 1/n, sehingga bentuk ini akan terus inverse pada progresi selanjutnya.
            }
        }

        override fun nextStep(prev: T, step: T): T = when(operationMode){
            NumberOperationMode.INCREMENTAL -> prev plusCast step
            NumberOperationMode.MULTIPLICATIONAL -> prev timesCast step
            NumberOperationMode.EXPONENTIAL -> prev powCast step
        }
    }

    override fun hashCode(): Int = getHashCode(super.hashCode(), operationMode, calculateOrder = false)

    override fun equals(other: Any?): Boolean = other is NumberProgression<*>
            && other.operationMode == operationMode && super.equals(other)
}