package sidev.lib.progression

/**
 * Progresi yang tiap langkahnya dapat ditentukan nilai perubahannya sebanyak [step].
 * Interface ini memiliki [step] yang konstan pada tiap progresinya.
 */
interface IntervalProgression<T: Comparable<T>, S: Comparable<S>>: Progression<T> {
    val step: S
//    override fun toString(): String = "($first .. $last step= $step)"
}