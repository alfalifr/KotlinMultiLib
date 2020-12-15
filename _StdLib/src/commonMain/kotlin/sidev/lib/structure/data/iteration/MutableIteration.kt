package sidev.lib.structure.data.iteration


/**
 * [Iteration] versi mutable, yaitu isi dari [index], [no], [repetition], [value] dapat diubah.
 */
interface MutableIteration: Iteration {
    override var index: Int
    override var no: Int
    override var repetition: Int
    var goToNext: Boolean

    override val value: MutableMap<String, Any>?
}

internal open class MutableIterationImpl(
    override var index: Int, override var repetition: Int, override var no: Int,
    override val value: MutableMap<String, Any>? = null
): IterationImpl(index, repetition, no, value), MutableIteration {
    override var goToNext: Boolean = true
    override fun toString(): String = "MutableIteration(i=$index, rep=$repetition, no=$no, state=$value)"
}