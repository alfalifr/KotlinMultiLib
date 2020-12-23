package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.console.prine
import sidev.lib.exception.IllegalArgExc
import sidev.lib.number.*
import sidev.lib.reflex.getHashCode

/**
 * Progresi yang tiap langkahnya dapat ditentukan nilai perubahannya sebanyak [step].
 * Interface ini memiliki [step] yang konstan pada tiap progresinya.
 * Jika [step]:
 *  - Positif, maka [first] <= [last]
 *  - Negatif, maka [first] >= [last].
 */
interface StepProgression<T: Comparable<T>, S: Number>: Progression<T> {
    val step: S

    /**
     * Mengecek apakah [e] masih di dalam jangkauan `this` `StepProgression`.
     * Fungsi ini tidak mengecek apakah [e] merupakan elemen yang diemti dari `this`.
     */
    infix fun containsInRange(e: T): Boolean {
        val (leftPair, rightPair)= smallBigPairWithExclusiveness
        val moreThanFirst= leftPair.first < e
                || leftPair.second == Exclusiveness.INCLUSIVE && leftPair.first == e
        val lessThanLast= e < rightPair.first
                || rightPair.second == Exclusiveness.INCLUSIVE && e == rightPair.first
        return moreThanFirst && lessThanLast
    }

    /**
     * Mengecek apakah [other] secara inklusif memiliki semua elemen yang merupakan
     * elemen `this`.
     */
    infix operator fun contains(other: StepProgression<T, *>): Boolean {
        val (leftPair, rightPair)= smallBigPairWithExclusiveness
        val (otherLeftPair, otherRightPair)= other.smallBigPairWithExclusiveness

        val left= leftPair.first
        val right= rightPair.first

        val otherLeft= otherLeftPair.first
        val otherRight= otherRightPair.first

        val leftExclusiveness= leftPair.second
        val rightExclusiveness= rightPair.second

        val otherLeftExclusiveness= otherLeftPair.second
        val otherRightExclusiveness= otherRightPair.second

        val moreThanFirst= otherLeft > left
                || leftExclusiveness == Exclusiveness.INCLUSIVE && otherLeft == left
                && otherLeftExclusiveness == Exclusiveness.INCLUSIVE
        val lessThanLast= otherRight < right
                || rightExclusiveness == Exclusiveness.INCLUSIVE && otherRight == right
                && otherRightExclusiveness == Exclusiveness.INCLUSIVE
//        prine("contains(StepProgression) leftPair= $leftPair rightPair= $rightPair otherLeftPair= $otherLeftPair otherRightPair= $otherRightPair")
        return moreThanFirst && lessThanLast
//        return otherPair.first >= pair.first && otherPair.second <= pair.second
    }
    /**
     * Mengecek apakah [e] merupakan elemen yang diemti dari `this`.
     * Fungsi ini juga termasuk pengecekan range pada [containsInRange].
     */
    infix operator fun contains(e: T): Boolean {
        if(!containsInRange(e)) return false
        if(e == first) return startExclusiveness == Exclusiveness.INCLUSIVE
        if(e == last) return endExclusiveness == Exclusiveness.INCLUSIVE

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
//        val pair= smallBigPair
//        val otherPair= other.smallBigPair

        val (leftPair, rightPair)= smallBigPairWithExclusiveness
        val (otherLeftPair, otherRightPair)= other.smallBigPairWithExclusiveness

        val left= leftPair.first
        val right= rightPair.first

        val otherLeft= otherLeftPair.first
        val otherRight= otherRightPair.first

        val leftExclusiveness= leftPair.second
        val rightExclusiveness= rightPair.second

        val otherLeftExclusiveness= otherLeftPair.second
        val otherRightExclusiveness= otherRightPair.second

//        val otherBothInclusive= otherLeftExclusiveness == Exclusiveness.INCLUSIVE && otherRightExclusiveness == Exclusiveness.INCLUSIVE

        val moreThanFirst= otherRight > left
                || leftExclusiveness == Exclusiveness.INCLUSIVE && otherRight == left
                && otherRightExclusiveness == Exclusiveness.INCLUSIVE //Jika otherRight == left, maka otherStartExclusiveness harus INCLUSIVE.
                //|| (otherRight == left && otherStartExclusiveness == Exclusiveness.EXCLUSIVE) Jika otherRight == left EXCLUSIVE, brarti titik di other krg dar left.
        val lessThanLast= otherLeft < right
                || rightExclusiveness == Exclusiveness.INCLUSIVE && otherLeft == right
                && otherLeftExclusiveness == Exclusiveness.INCLUSIVE
                //|| (otherRight == right && otherEndExclusiveness == Exclusiveness.EXCLUSIVE)
        return moreThanFirst && lessThanLast
//        return otherPair.first <= pair.second && otherPair.second >= pair.first
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