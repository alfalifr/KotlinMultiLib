package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness
import sidev.lib.`val`.NumberOperationMode

interface CharProgression: OperableProgression<Char, Int>{
    override fun iterator(): CharProgressionIterator = CharProgressionIterator(first, last, step)
}

internal class CharProgressionImpl(
    first: Char, last: Char, step: Int,
    startExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE,
    endExclusiveness: Exclusiveness = Exclusiveness.INCLUSIVE
) : StepProgressionImpl<Char, Int>(first, last, step, startExclusiveness, endExclusiveness), CharProgression {
    override val operationMode: NumberOperationMode = NumberOperationMode.INCREMENTAL
    override fun operate(e: Char, step: Int): Char = e + step
}