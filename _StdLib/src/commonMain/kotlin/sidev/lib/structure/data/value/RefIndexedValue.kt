package sidev.lib.structure.data.value

interface RefIndexedValue<T>: IndexedValue<T> {
    val indexBox: Var<Int>
    override val index: Int
        get()= indexBox.value
    override val value: T
}


internal open class RefIndexedValueImpl<T>(override val indexBox: Var<Int>, override val value: T)
    : IndexedValueImpl<T>(indexBox.value, value), RefIndexedValue<T> {
    constructor(index: Int, value: T): this(index.asBoxed(), value)

    override val index: Int
        get() = indexBox.value

    override fun toString(): String = "RefIndexedValue(index=$index, value=$value)"
}