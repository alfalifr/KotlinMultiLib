package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.console.prine
import sidev.lib.exception.IllegalArgExc
import sidev.lib.number.*
import sidev.lib.reflex.getHashCode

/**
 * Progresi yang tiap langkahnya dapat ditentukan nilai perubahannya sebanyak [step].
 * Interface ini memiliki [step] yang konstan pada tiap progresinya.
 */
interface StepProgression<T: Comparable<T>, S: Number>: Progression<T> {
    val step: S

    operator fun contains(other: StepProgression<T, *>): Boolean {
        val pair= smallBigPair
        val otherPair= other.smallBigPair
        return otherPair.first >= pair.first && otherPair.second <= pair.second
    }
    operator fun contains(e: T): Boolean {
        val compareLimitFun: (e1: T, e: T) -> Boolean = if(step > 0) ::moreThan else ::lessThan
        for(e1 in this){
            if(e1.compareTo(e) == 0)
                return true
            if(compareLimitFun(e1, e))
                return false
        }
        return false
    }

    /** Menentukan apakan [other] beririsan dg `this`. */
    infix fun intersects(other: StepProgression<T, S>): Boolean {
        val pair= smallBigPair
        val otherPair= other.smallBigPair
        return otherPair.first <= pair.second && otherPair.second >= pair.first
    }
}


internal abstract class StepProgressionImpl<T: Comparable<T>, S: Number>(
    final override val first: T, final override val last: T,
    final override val step: S,
    final override val startExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
    final override val endExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
    checkConstraintFirst: Boolean = true
): StepProgression<T, S> {

    init {
        if(checkConstraintFirst)
            when {
                step == 0 -> throw IllegalArgExc(paramExcepted = arrayOf("step"), detailMsg = "Param `step` gak boleh 0 karena dapat menyebabkan infinite loop.")
                step.isNegative() && first < last -> throw IllegalArgExc(paramExcepted = arrayOf("step"), detailMsg = "Param `step` yg negatif dan `first < last` dapat menyebabkan infinite loop.")
                step.isPositive() && first > last -> throw IllegalArgExc(paramExcepted = arrayOf("step"), detailMsg = "Param `step` yg positif dan `first > last` dapat menyebabkan infinite loop.")
            }
    }

    override fun equals(other: Any?): Boolean = other is StepProgression<*, *>
            && other.first == first && other.last == last && other.step == step
            && other.startExclusiveness == startExclusiveness && other.endExclusiveness == endExclusiveness

    override fun hashCode(): Int = getHashCode(
        first, last, step, startExclusiveness, endExclusiveness,
        calculateOrder = false
    )

    override fun toString(): String = "$first..>> $step >>..$last"
}