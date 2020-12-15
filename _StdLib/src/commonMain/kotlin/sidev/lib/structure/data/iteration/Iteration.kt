package sidev.lib.structure.data.iteration

import sidev.lib.reflex.getHashCode
import sidev.lib.structure.data.value.IndexedValue

/**
 * Interface yang digunakan untuk menampung data terkait
 * sebuah iterasi, yaitu [index], [no], [repetition], dan [value].
 */
interface Iteration: IndexedValue<Map<String, Any>?> {
    /**
     * Nomor iterasi yang dilakukan terhadap data atau state yang sama.
     */
    val no: Int

    /**
     * Perulangan untuk [index] yang sama dalam [no] yang sama.
     */
    val repetition: Int

    /**
     * Merupakan state untuk setiap step.
     */
    override val value: Map<String, Any>?
}

internal open class IterationImpl(
    override val index: Int, override val repetition: Int, override val no: Int,
    override val value: Map<String, Any>? = null
): Iteration {
    override fun equals(other: Any?): Boolean = other is Iteration
            && index == other.index && repetition == other.repetition && no == other.no
            && value == other.value

    override fun hashCode(): Int = getHashCode(index, repetition, no, value, calculateOrder = false)
    override fun toString(): String = "Iteration(i=$index, rep=$repetition, no=$no, state=$value)"
}