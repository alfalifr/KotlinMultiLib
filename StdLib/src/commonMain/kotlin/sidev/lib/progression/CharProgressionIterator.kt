package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness

open class CharProgressionIterator(
    first: Char, val last: Char, val step: Int,
    val startExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
    val endExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
): IntervalProgressionIterator<Char, Int> {
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

    //Hanya sbg formalitas
    override fun nextStep(prev: Char, step: Int): Char = prev +step
    override fun hasNext(prev: Char, next: Char, last: Char, exclusiveness: Exclusiveness): Boolean =
        if(exclusiveness == Exclusiveness.INCLUSIVE){
            if(step > 0) prev <= last else prev >= last
        } else {
            if(step > 0) prev < last else prev > last
        }

    override fun hasNext(): Boolean = hasNext
    override fun next(): Char {
        val value= next
        if(value >= last){
            if(!hasNext)
                throw NoSuchElementException()
            hasNext= false
        } else
            next += step
        return value
    }
}