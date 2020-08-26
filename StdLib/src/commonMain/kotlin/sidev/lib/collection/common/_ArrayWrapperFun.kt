package sidev.lib.collection.common


fun <T> Array<T>.asWrapped(): MutableArrayWrapper<T> =
    MutableArrayWrapperImpl(this)
fun IntArray.asWrapped(): MutableArrayWrapper<Int> =
    MutableArrayWrapperImpl_Int(
        this
    )
fun LongArray.asWrapped(): MutableArrayWrapper<Long> =
    MutableArrayWrapperImpl_Long(
        this
    )
fun ShortArray.asWrapped(): MutableArrayWrapper<Short> =
    MutableArrayWrapperImpl_Short(
        this
    )
fun FloatArray.asWrapped(): MutableArrayWrapper<Float> =
    MutableArrayWrapperImpl_Float(
        this
    )
fun DoubleArray.asWrapped(): MutableArrayWrapper<Double> =
    MutableArrayWrapperImpl_Double(
        this
    )
fun ByteArray.asWrapped(): MutableArrayWrapper<Byte> =
    MutableArrayWrapperImpl_Byte(
        this
    )
fun BooleanArray.asWrapped(): MutableArrayWrapper<Boolean> =
    MutableArrayWrapperImpl_Boolean(
        this
    )
fun CharArray.asWrapped(): MutableArrayWrapper<Char> =
    MutableArrayWrapperImpl_Char(
        this
    )


fun <T> arrayWrapperOf(vararg element: T): ArrayWrapper<T>
        = object : ArrayWrapper<T> {
    private val array= element as Array<T>

    override fun get(index: Int): T = array[index]
    override val size: Int get() = array.size
    override fun iterator(): Iterator<T> = array.iterator()
//    override fun getValue(owner: Any?, prop: KProperty<*>): Array<T> = array
}

fun <T> mutableArrayWrapperOf(vararg element: T): MutableArrayWrapper<T>
        = object : MutableArrayWrapper<T> {
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