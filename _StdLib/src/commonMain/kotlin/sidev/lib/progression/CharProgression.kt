package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness

interface CharProgression: IntervalProgression<Char, Int>{
    override fun iterator(): CharProgressionIterator = CharProgressionIterator(first, last, step)
}

internal class CharProgressionImpl(
    first: Char, last: Char, step: Int,
    startExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
    endExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE
) : IntervalProgressionImpl<Char, Int>(first, last, step, startExclusiveness, endExclusiveness), CharProgression