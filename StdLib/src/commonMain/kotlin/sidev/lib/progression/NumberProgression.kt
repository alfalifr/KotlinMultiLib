package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.`val`.NumberOperationMode

interface NumberProgression<T>: IntervalProgression<T, T> where T: Number, T: Comparable<T>{
    val operationMode: NumberOperationMode
    override fun iterator(): NumberProgressionIterator<T>
}