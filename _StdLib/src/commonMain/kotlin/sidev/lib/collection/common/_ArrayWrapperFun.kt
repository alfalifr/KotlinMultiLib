package sidev.lib.collection.common

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.collection.array.arrayCopyAll

///*
inline fun <reified T> ArrayWrapper<T>.toTypedArray(copyFirst: Boolean = true): Array<T> = toArray(copyFirst).let {
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    val arr= arrayOfNulls<T>(size) as Array<T>
    arrayCopyAll(it, 0, arrayOfNulls<T>(size), 0, size)
    arr
}
        //= (this as ArrayWrapperImpl_Object<T>).array.copyOf() //.let { it.copyOf() }
// */

fun <T> Array<T>.asWrapped(copyFirst: Boolean = true): MutableArrayWrapper<T> =
    MutableArrayWrapperImpl_Object(if(copyFirst) this else copyOf())
fun IntArray.asWrapped(copyFirst: Boolean = true): MutableArrayWrapper<Int> =
    MutableArrayWrapperImpl_Int(if(copyFirst) this else copyOf())
fun LongArray.asWrapped(copyFirst: Boolean = true): MutableArrayWrapper<Long> =
    MutableArrayWrapperImpl_Long(if(copyFirst) this else copyOf())
fun ShortArray.asWrapped(copyFirst: Boolean = true): MutableArrayWrapper<Short> =
    MutableArrayWrapperImpl_Short(if(copyFirst) this else copyOf())
fun FloatArray.asWrapped(copyFirst: Boolean = true): MutableArrayWrapper<Float> =
    MutableArrayWrapperImpl_Float(if(copyFirst) this else copyOf())
fun DoubleArray.asWrapped(copyFirst: Boolean = true): MutableArrayWrapper<Double> =
    MutableArrayWrapperImpl_Double(if(copyFirst) this else copyOf())
fun ByteArray.asWrapped(copyFirst: Boolean = true): MutableArrayWrapper<Byte> =
    MutableArrayWrapperImpl_Byte(if(copyFirst) this else copyOf())
fun BooleanArray.asWrapped(copyFirst: Boolean = true): MutableArrayWrapper<Boolean> =
    MutableArrayWrapperImpl_Boolean(if(copyFirst) this else copyOf())
fun CharArray.asWrapped(copyFirst: Boolean = true): MutableArrayWrapper<Char> =
    MutableArrayWrapperImpl_Char(if(copyFirst) this else copyOf())

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> arrayWrapperOf(vararg element: T): ArrayWrapper<T> = ArrayWrapperImpl_Object(element as Array<T>)
/*
    object : ArrayWrapper<T> {
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    private val array= element as Array<T>

    override fun get(index: Int): T = array[index]
    override val size: Int get() = array.size
    override fun iterator(): Iterator<T> = array.iterator()
    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayWrapper<T> {
        TODO("Not yet implemented")
    }
    //    override fun getValue(owner: Any?, prop: KProperty<*>): Array<T> = array
}
 */

@Suppress(SuppressLiteral.UNCHECKED_CAST)
fun <T> mutableArrayWrapperOf(vararg element: T): MutableArrayWrapper<T> = MutableArrayWrapperImpl_Object(element as Array<T>)

/*
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
 */