package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness

class CharProgressionImpl(
    override val first: Char,
    override val last: Char,
    override val step: Int,
    override val startExclusiveness: Exclusiveness= Exclusiveness.INCLUSIVE,
    override val endExclusiveness: Exclusiveness= Exclusiveness.INCLUSIVE
) : CharProgression