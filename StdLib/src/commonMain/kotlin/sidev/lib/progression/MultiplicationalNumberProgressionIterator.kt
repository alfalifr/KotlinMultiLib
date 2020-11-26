package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.number.absoluteValue
import sidev.lib.number.compareTo
import sidev.lib.number.times
import sidev.lib.number.timesCast
import sidev.lib.range.rangeTo_

open class MultiplicationalNumberProgressionIterator<T>(first: T, last: T, step: T)
    : NumberProgressionIterator<T>(first, last, step) where T: Number, T: Comparable<T> {
//    private var hasNext: Boolean = true //if(step > 0) first <= last else first >= last

    override fun nextStep(prev: T, step: T): T = prev timesCast step
    override fun hasNext(prev: T, next: T, last: T, exclusiveness: Exclusiveness): Boolean = when {
        step > 1 || step < 1 ->
            if(exclusiveness == Exclusiveness.INCLUSIVE){
                prev.absoluteValue <= last.absoluteValue
            } else{
                prev.absoluteValue < last.absoluteValue
            }
        else ->
            if(exclusiveness == Exclusiveness.INCLUSIVE){
                prev.absoluteValue >= last.absoluteValue
            } else{
                prev.absoluteValue > last.absoluteValue
            }
    }
}