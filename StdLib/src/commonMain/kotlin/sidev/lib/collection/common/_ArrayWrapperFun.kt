package sidev.lib.collection.common

import sidev.lib.`val`.SuppressLiteral


fun <T> ArrayWrapper<T>.toArray(): Array<T>
        = (this as ArrayWrapperImpl<T>).array.copyOf() //.let { it.copyOf() }

fun <T> Array<T>.asWrapped(): MutableArrayWrapper<T> =
    MutableArrayWrapperImpl(copyOf())
fun IntArray.asWrapped(): MutableArrayWrapper<Int> =
    MutableArrayWrapperImpl_Int(
        copyOf()
    )
fun LongArray.asWrapped(): MutableArrayWrapper<Long> =
    MutableArrayWrapperImpl_Long(
        copyOf()
    )
fun ShortArray.asWrapped(): MutableArrayWrapper<Short> =
    MutableArrayWrapperImpl_Short(
        copyOf()
    )
fun FloatArray.asWrapped(): MutableArrayWrapper<Float> =
    MutableArrayWrapperImpl_Float(
        copyOf()
    )
fun DoubleArray.asWrapped(): MutableArrayWrapper<Double> =
    MutableArrayWrapperImpl_Double(
        copyOf()
    )
fun ByteArray.asWrapped(): MutableArrayWrapper<Byte> =
    MutableArrayWrapperImpl_Byte(
        copyOf()
    )
fun BooleanArray.asWrapped(): MutableArrayWrapper<Boolean> =
    MutableArrayWrapperImpl_Boolean(
        copyOf()
    )
fun CharArray.asWrapped(): MutableArrayWrapper<Char> =
    MutableArrayWrapperImpl_Char(
        copyOf()
    )


fun <T> arrayWrapperOf(vararg element: T): ArrayWrapper<T>
        = object : ArrayWrapper<T> {
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    private val array= element as Array<T>

    override fun get(index: Int): T = array[index]
    override val size: Int get() = array.size
    override fun iterator(): Iterator<T> = array.iterator()
//    override fun getValue(owner: Any?, prop: KProperty<*>): Array<T> = array
}

fun <T> mutableArrayWrapperOf(vararg element: T): MutableArrayWrapper<T>
        = object : MutableArrayWrapper<T> {
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    private val array= element as Array<T>

    override fun get(index: Int): T = array[index]
    override fun set(index: Int, element: T): T {
        val prevVal= array[index]
        array[index]= element
        return prevVal
    }
    override val size: Int get() = array.size
    override fun iterator(): Iterator<T> = array.iterator()
//    override fun getValue(owner: Any?, prop: KProperty<*>): Array<T> = array
}