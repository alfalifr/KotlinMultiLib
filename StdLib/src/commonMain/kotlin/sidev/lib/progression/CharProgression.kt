package sidev.lib.progression

interface CharProgression: IntervalProgression<Char, Int>{
    override fun iterator(): CharProgressionIterator = CharProgressionIterator(first, last, step)
}