package sidev.lib.progression

import sidev.lib.`val`.Exclusiveness

/**
 * Progresi nilai dari [first] sampai [last] (inklusif).
 */
interface Progression<T>: Iterable<T> {
    val first: T
    val last: T
    val startExclusiveness: Exclusiveness
    val endExclusiveness: Exclusiveness
//    override fun toString(): String = "$first .. $last"
}