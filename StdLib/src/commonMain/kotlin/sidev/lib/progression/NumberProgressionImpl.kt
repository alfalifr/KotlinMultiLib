package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.`val`.NumberOperationMode
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.number.*
import sidev.lib.range.rangeTo_

class NumberProgressionImpl<T>(
    override val first: T,
    override val last: T,
    override val step: T,
    override val operationMode: NumberOperationMode = NumberOperationMode.INCREMENTAL,
    override val startExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
    override val endExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
) : NumberProgression<T> where T: Number, T: Comparable<T>{

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
                        next.absoluteValue <= last.absoluteValue as Number
                    } else {
                        next.absoluteValue < last.absoluteValue as Number
                    }
                else ->
                    if(exclusiveness == Exclusiveness.INCLUSIVE) {
                        next.absoluteValue >= last.absoluteValue as Number
                    } else {
                        next.absoluteValue > last.absoluteValue as Number
                    }
            }
            NumberOperationMode.EXPONENTIAL -> when {
                step >= 1 ->
                    if(exclusiveness == Exclusiveness.INCLUSIVE) {
                        next.absoluteValue <= last.absoluteValue as Number
                    } else {
                        next.absoluteValue < last.absoluteValue as Number
                    }
                step <= -1 ->
                    if(exclusiveness == Exclusiveness.INCLUSIVE) {
                        (if(next >= 1) next else 1.0 / next).absoluteValue <=
                                (if(last >= 1) last else 1.0 / last).absoluteValue
                    } else {
                        (if(next >= 1) next else 1.0 / next).absoluteValue <
                                (if(last >= 1) last else 1.0 / last).absoluteValue
                    }
                    // Ini karena pangkat min sama dg 1/n, sehingga bentuk ini akan terus inverse pada progresi selanjutnya.
                step in @Suppress(SuppressLiteral.UNCHECKED_CAST)
                        ((0.0.rangeTo_(1.0, endExclusiveness = Exclusiveness.EXCLUSIVE)) as ClosedRange<T>) ->
                    if(exclusiveness == Exclusiveness.INCLUSIVE) {
                        next.absoluteValue >= last.absoluteValue as Number
                    } else {
                        next.absoluteValue > last.absoluteValue as Number
                    }
                else ->
                    if(exclusiveness == Exclusiveness.INCLUSIVE) {
                        (if(next >= 1) next else 1.0 / next).absoluteValue >=
                                (if(last >= 1) last else 1.0 / last).absoluteValue
                    } else {
                        (if(next >= 1) next else 1.0 / next).absoluteValue >
                                (if(last >= 1) last else 1.0 / last).absoluteValue
                    }
                    // Ini karena pangkat min sama dg 1/n, sehingga bentuk ini akan terus inverse pada progresi selanjutnya.
            }
        }

        override fun nextStep(prev: T, step: T): T = when(operationMode){
            NumberOperationMode.INCREMENTAL -> prev plusCast step
            NumberOperationMode.MULTIPLICATIONAL -> prev timesCast step
            NumberOperationMode.EXPONENTIAL -> prev pow step
        }
    }
}