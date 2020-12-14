package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness

interface IntervalProgressionIterator<T: Comparable<T>, S: Comparable<S>>: Iterator<T> {
    /**
     * Mengambil nilai `next` dengan [prev] adalah nilai `next` sebelumnya dan [step] adalah faktor perubahnya.
     */
    fun nextStep(prev: T, step: S): T

    /**
     * [exclusiveness] ditujukan pada [next]. Jika [exclusiveness] == [Exclusiveness.EXCLUSIVE] maka [next] tidak diikutkan, dan sebaliknya.
     */
    fun hasNext(prev: T, next: T, last: T, exclusiveness: Exclusiveness): Boolean
}