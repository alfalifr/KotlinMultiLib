package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.`val`.NumberOperationMode
import sidev.lib.`val`.SuppressLiteral
import sidev.lib.number.*
import sidev.lib.range.rangeTo_

open class NumberProgressionIterator<T>(
    val first: T, override val last: T, override val step: T,
    override val operationMode: NumberOperationMode,
    val reverseOperation: Boolean = false,
    val startExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
    override val endExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE
): OperableProgressionIterator<T, T> where T: Number, T: Comparable<T> {
//    private var hasNext: Boolean = true //if(step > 0) first <= last else first >= last
    private var next: T= first //if(hasNext) first else last
    private var status: Int= -2 //0 berhenti, 1 lanjut, -1 tidak diketahui, -2 init

//    abstract override fun nextStep(prev: T, step: T): T
//    abstract override fun hasNext(prev: T, next: T, last: T, exclusiveness: Exclusiveness): Boolean

    override fun operate(prev: T, step: T): T = when(operationMode){
        NumberOperationMode.INCREMENTAL -> prev plusCast step
        NumberOperationMode.MULTIPLICATIONAL -> prev timesCast step
        NumberOperationMode.EXPONENTIAL -> prev powCast step
    }

    private fun calculateNext(){
        if(status >= 0) //status udah diketahui
            return

        val prev= next
        if(status == -1 || startExclusiveness == Exclusiveness.EXCLUSIVE)
            next= nextStep(next)

        status=
            if(status == -1){ // Jika tidak dalam init.
                if(hasNext(prev, next)) 1
                else 0
            } else { // Jika init,
                if(startExclusiveness == Exclusiveness.INCLUSIVE && endExclusiveness == Exclusiveness.INCLUSIVE){ //Jika sama-sama inklusif,
                    if(prev == last) 1 //Jika first == last, maka start jelas ikut.
                    else {
                        if(hasNext(prev, next)) 1 else 0
                          //Jika first != last, blum tentu start bisa ikut.
                          // Hal tersebut dikarenakan first dan last tidak sesuai dg step-nya.
                    }
                }
                else { //Jika salah satunya eksklusif,
                    if(prev != last){
                        if(hasNext(prev, next)) 1 else 0
                          //Jika first != last, blum tentu start bisa ikut.
                          // Hal tersebut dikarenakan first dan last tidak sesuai dg step-nya.
                    } else 0 //Jika first == last, jelas start tidak ikut.
                }
            }
    }

    override fun hasNext(): Boolean {
        if(status < 0)
            calculateNext()
        return status == 1
    }
    override fun next(): T {
        if(status < 0)
            calculateNext()
        if(status == 0)
            throw NoSuchElementException("Iterasi habis")
        status= -1
        return next
    }

    override fun hasNext(prev: T, next: T): Boolean {
        if(prev == next && prev != first) return false
        val exclusiveness= endExclusiveness
        return when(operationMode){
            NumberOperationMode.INCREMENTAL ->
                if(exclusiveness == Exclusiveness.INCLUSIVE) {
                    if(step > 0) next <= last else next >= last
                } else {
                    if(step > 0) next < last else next > last
                }
            NumberOperationMode.MULTIPLICATIONAL -> {
                val step= if(!reverseOperation) step else 1 / step
                when {
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
            }
            NumberOperationMode.EXPONENTIAL -> {
                @Suppress(SuppressLiteral.UNCHECKED_CAST)
                val step= (if(!reverseOperation) step else 1 / step) as T
                when {
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
        }
    }

    override fun nextStep(prev: T): T = when(operationMode){
        NumberOperationMode.INCREMENTAL -> if(!reverseOperation) prev plusCast  step else prev minusCast step
        NumberOperationMode.MULTIPLICATIONAL -> if(!reverseOperation) prev timesCast step else prev divCast step
        NumberOperationMode.EXPONENTIAL -> if(!reverseOperation) prev powCast step else prev rootCast step
/*
        NumberOperationMode.INCREMENTAL -> prev plusCast step
        NumberOperationMode.MULTIPLICATIONAL -> prev timesCast step
        NumberOperationMode.EXPONENTIAL -> prev powCast step
 */
    }
}