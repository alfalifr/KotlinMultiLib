package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.exception.IllegalArgExc
import sidev.lib.number.isNegative
import sidev.lib.number.isPositive
import sidev.lib.reflex.getHashCode

/**
 * Progresi yang tiap langkahnya dapat ditentukan nilai perubahannya sebanyak [step].
 * Interface ini memiliki [step] yang konstan pada tiap progresinya.
 */
interface IntervalProgression<T: Comparable<T>, S: Number>: Progression<T> {
    val step: S
    /** Menentukan apakan [other] beririsan dg `this`. */
    operator fun contains(other: IntervalProgression<T, S>): Boolean {
        val first= first
        val last= last
        val small: T
        val big: T = if(first <= last){
            small= first
            last
        } else {
            small= last
            first
        }

        val otherFirst= other.first
        val otherLast= other.last
        val otherSmall: T
        val otherBig: T = if(otherFirst <= otherLast){
            otherSmall= otherFirst
            otherLast
        } else {
            otherSmall= otherLast
            otherFirst
        }

        return otherSmall <= big && otherBig >= small
                || otherSmall >= small && otherBig <= big
    }
}


internal abstract class IntervalProgressionImpl<T: Comparable<T>, S: Number>(
    final override val first: T, final override val last: T,
    final override val step: S,
    final override val startExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
    final override val endExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE
): IntervalProgression<T, S> {

    init {
        when {
            step == 0 -> throw IllegalArgExc(paramExcepted = arrayOf("step"), detailMsg = "Param `step` gak boleh 0.")
            step.isNegative() && first < last -> throw IllegalArgExc(paramExcepted = arrayOf("step"), detailMsg = "Param `step` yg negatif dan `first < last` dapat menyebabkan infinite loop.")
            step.isPositive() && first > last -> throw IllegalArgExc(paramExcepted = arrayOf("step"), detailMsg = "Param `step` yg positif dan `first > last` dapat menyebabkan infinite loop.")
        }
    }

    override fun equals(other: Any?): Boolean = other is IntervalProgression<*, *>
            && other.first == first && other.last == last && other.step == step
            && other.startExclusiveness == startExclusiveness && other.endExclusiveness == endExclusiveness

    override fun hashCode(): Int = getHashCode(
        first, last, step, startExclusiveness, endExclusiveness,
        calculateOrder = false
    )

    override fun toString(): String = "$first..<<$step>>..$last"
}