package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.`val`.NumberOperationMode

open class CharProgressionIterator(
    first: Char, final override val last: Char, final override val step: Int,
    val startExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
    final override val endExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
): OperableProgressionIterator<Char, Int> {
    override val operationMode: NumberOperationMode = NumberOperationMode.INCREMENTAL
    private var hasNext: Boolean =
        if(startExclusiveness == Exclusiveness.INCLUSIVE && endExclusiveness == Exclusiveness.INCLUSIVE){
            if(step > 0) first <= last else first >= last
        } else {
            if(step > 0) first < last else first > last
        }
    private var next: Char=
        if(hasNext) {
            if(startExclusiveness == Exclusiveness.INCLUSIVE) first else first +1
        } else {
            if(endExclusiveness == Exclusiveness.INCLUSIVE) last else last -1
        }
    private val limitFun: (next: Char, las: Char) -> Boolean =
        if(step > 0) { next, last -> next > last }
        else { next, last -> next < last }

    //Hanya sbg formalitas
    override fun operate(prev: Char, step: Int): Char = prev + step
//    override fun nextStep(prev: Char): Char = prev +step

    override fun hasNext(): Boolean = hasNext
    override fun next(): Char {
        val value= next
        val nextStep= value + step
        if(limitFun(nextStep, last)){
            if(!hasNext)
                throw NoSuchElementException()
            hasNext= false
        } else
            next = nextStep
        return value
    }
}