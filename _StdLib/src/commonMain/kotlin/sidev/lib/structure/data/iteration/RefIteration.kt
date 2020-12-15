package sidev.lib.structure.data.iteration

import sidev.lib.structure.data.value.RefIndexedValue
import sidev.lib.structure.data.value.Var
import sidev.lib.structure.data.value.asBoxed

/**
 * [Iteration] versi mutable, yaitu isi dari [index], [no], [repetition], [value] dapat diubah.
 */
interface RefIteration: Iteration, RefIndexedValue<Map<String, Any>?> {
    val noBox: Var<Int>
    val repetitionBox: Var<Int>

    override val no: Int
        get() = noBox.value
    override val repetition: Int
        get() = repetitionBox.value

    val goToNext: Var<Boolean>

    override val value: MutableMap<String, Any>?
}

internal open class RefIterationImpl(
    override val indexBox: Var<Int>, override val repetitionBox: Var<Int>, override val noBox: Var<Int>,
    override val value: MutableMap<String, Any>? = null
): IterationImpl(indexBox.value, repetitionBox.value, noBox.value, value), RefIteration {
    constructor(
        index: Int, repetition: Int, no: Int, value: MutableMap<String, Any>? = null
    ): this(index.asBoxed(), repetition.asBoxed(), no.asBoxed(), value)

    override val index: Int
        get() = indexBox.value
    override val repetition: Int
        get() = repetitionBox.value
    override val no: Int
        get() = noBox.value

    override val goToNext: Var<Boolean> = true.asBoxed()

    override fun toString(): String = "RefIteration(i=$index, rep=$repetition, no=$no, state=$value)"
}