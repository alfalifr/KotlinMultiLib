package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness

interface StepProgressionIterator<T: Comparable<T>, S: Number>: Iterator<T> {
    val step: S
    val last: T
    val endExclusiveness: Exclusiveness

    /**
     * Mengambil nilai `next` dengan [prev] adalah nilai `next` sebelumnya dan [step] adalah faktor perubahnya.
     */
    fun nextStep(prev: T): T

    /**
     * [exclusiveness] ditujukan pada [next]. Jika [exclusiveness] == [Exclusiveness.EXCLUSIVE] maka [next] tidak diikutkan, dan sebaliknya.
     */
    fun hasNext(prev: T, next: T): Boolean //, exclusiveness: Exclusiveness
}