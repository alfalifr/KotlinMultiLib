package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.`val`.NumberOperationMode
import sidev.lib.number.plus
import sidev.lib.number.compareTo
import sidev.lib.number.plusCast

open class IncrementalNumberProgressionIterator<T>(first: T, last: T, step: T)
    : NumberProgressionIterator<T>(first, last, step, NumberOperationMode.INCREMENTAL) where T: Number, T: Comparable<T>
/*
{
    //    private var hasNext: Boolean = true //if(step > 0) first <= last else first >= last

//    override fun nextStep(prev: T, step: T): T = prev plusCast step
    override fun hasNext(prev: T, next: T, last: T, exclusiveness: Exclusiveness): Boolean =
        if(exclusiveness == Exclusiveness.INCLUSIVE){
            if(step > 0) prev <= last else prev >= last
        } else{
            if(step > 0) prev < last else prev > next
        }
}
 */