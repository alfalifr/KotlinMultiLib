package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.`val`.NumberOperationMode
import sidev.lib.number.compareTo

interface OperableProgressionIterator<T: Comparable<T>, S: Number>: StepProgressionIterator<T, S> {
    val operationMode: NumberOperationMode
    override val step: S

    /** Melakukan operasi matematika terhadap [prev] dengan [step] yang menghasilkan nilai `next` [T]. */
    fun operate(prev: T, step: S): T

    override fun nextStep(prev: T): T = operate(prev, step)

    override fun hasNext(prev: T, next: T): Boolean { //, exclusiveness: Exclusiveness
        val exclusiveness= endExclusiveness
        val last= last
        return if(exclusiveness == Exclusiveness.INCLUSIVE){
            if(step > 0) next <= last else next >= last
        } else {
            if(step > 0) next < last else next > last
        }
    }
}