package sidev.lib.collection.common

import sidev.lib.collection.array.arrayCopy
import sidev.lib.collection.array.arrayCopyAll
import sidev.lib.collection.array.contentSize
import sidev.lib.collection.array.copy
import sidev.lib.structure.data.Arrangeable
import sidev.lib.structure.data.FiniteIndexable
import sidev.lib.structure.data.RangeCopyable

/** Digunakan sbg interface common yg menunjukan sifat iterable dari [Array]. */
interface ArrayIterable<out T>: Iterable<T>

/**
 * Struktur data pembungkus [Array]. Tujuan dari interface ini adalah untuk memudahkan akses
 * elemen pada tipe data yg scr umum dapat berisi bbrp elemen.
 * Interface ini bersifat immutable agar sesuai dg konsep pada [CommonList].
 */
interface ArrayWrapper<T>: CommonIterable<T>, FiniteIndexable<T>, RangeCopyable<ArrayWrapper<T>> {
    /*
    /** Array yg dibungkus dalam interface ini. */
    val array: Array<T>
 */
/*
    /**
     * Creates a new array with the specified [size], where each element is calculated by calling the specified
     * [init] function.
     *
     * The function [init] is called for each array element sequentially starting from the first one.
     * It should return the value for an array element given its index.
     */
    public inline constructor(size: Int, init: (Int) -> T)
 */
    override val maxRange: Int
        get() = size

    val isPrimitive: Boolean
    //val contentType: KClass<T>

    /**
     * Returns the array element at the specified [index]. This method can be called using the
     * index operator.
     * ```
     * value = arr[index]
     * ```
     *
     * If the [index] is out of bounds of this array, throws an [IndexOutOfBoundsException] except in Kotlin/JS
     * where the behavior is unspecified.
     */
    override operator fun get(index: Int): T

    /**
     * Returns the number of elements in the array.
     */
    override val size: Int

    /**
     * Creates an iterator for iterating over the elements of the array.
     */
    override operator fun iterator(): Iterator<T>

    override fun copy(): ArrayWrapper<T> = copy(0)
    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayWrapper<T>

    fun toArray(in_: Any, copyFirst: Boolean = true): Any
    fun toArray(in_: Array<T>, copyFirst: Boolean = true): Any
    fun toArray(copyFirst: Boolean = true): Any
}

interface MutableArrayWrapper<T>: ArrayWrapper<T>, Arrangeable<T> {

    /**
     * Sets the array element at the specified [index] to the specified [element]. This method can
     * be called using the index operator.
     * ```
     * arr[index] = value
     * ```
     *
     * If the [index] is out of bounds of this array, throws an [IndexOutOfBoundsException] except in Kotlin/JS
     * where the behavior is unspecified.
     *
     * @return element sebelumnya pada [index]
     *   Tujuan dari pengembalian elemen adalah agar kompatibel dg [MutableList].
     */
    override operator fun set(index: Int, element: T): T
    override fun set_(index: Int, element: T) {
        this[index]= element
    }

    override fun copy(): MutableArrayWrapper<T> = copy(0)
    override fun copy(from: Int, until: Int, reversed: Boolean): MutableArrayWrapper<T>
}



internal interface ArrayWrapperImpl<T>: ArrayWrapper<T>

internal open class ArrayWrapperImpl_Object<T>(
    val array: Array<T>,
    //override val contentType: KType
): ArrayWrapperImpl<T> {

    //    constructor(size: Int, init: (Int) -> T): this(Array<T>(size, init))
    override val isPrimitive: Boolean = false
    override fun get(index: Int): T = array[index]
    override val size: Int get() = array.size
    override fun iterator(): Iterator<T> = array.iterator()
    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayWrapper<T> =
        (array as Array<Any?>).copy(from, until, reversed).asWrapped() as ArrayWrapper<T>


    override fun toArray(in_: Array<T>, copyFirst: Boolean): Any = in_.apply {
        arrayCopy(array, 0, in_, 0, in_.size)
    }
    override fun toArray(in_: Any, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(array, 0, in_, 0, contentSize)
    }
    override fun toArray(copyFirst: Boolean): Any =
        if(copyFirst) array.copyOf() //.let { it.copyOf() }
        else array
    //    override fun getValue(owner: Any?, prop: KProperty<*>): Array<T> = array
}

internal interface MutableArrayWrapperImpl<T>: ArrayWrapperImpl<T>, MutableArrayWrapper<T>
internal class MutableArrayWrapperImpl_Object<T>(array: Array<T>)
    : ArrayWrapperImpl_Object<T>(array), MutableArrayWrapper<T> {
    override fun set(index: Int, element: T): T {
        val prevVal= array[index]
        array[index]= element
        return prevVal
    }
    override fun copy(from: Int, until: Int, reversed: Boolean): MutableArrayWrapper<T> =
        (array as Array<Any?>).copy(from, until, reversed).asWrapped() as MutableArrayWrapper<T>
}



internal interface ArrayWrapperImpl_Primitive<T>: ArrayWrapperImpl<T> {
    override val isPrimitive: Boolean get()= true
}
internal interface MutableArrayWrapperImpl_Primitive<T>
    : ArrayWrapperImpl_Primitive<T>, MutableArrayWrapperImpl<T>, ArrayWrapperImpl<T>

/*
==============
IntArray
==============
 */
internal open class ArrayWrapperImpl_Int(val primitiveArray: IntArray): ArrayWrapperImpl_Primitive<Int> { //: ArrayWrapperImpl<Int>(primitiveArray.toTypedArray())
    //    constructor(size: Int, init: (Int) -> T): this(Array<T>(size, init))
    override fun get(index: Int): Int = primitiveArray[index]
    override val size: Int get() = primitiveArray.size
    override fun iterator(): Iterator<Int> = primitiveArray.iterator()
    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayWrapper<Int> =
        primitiveArray.copy(from, until, reversed).asWrapped() //as ArrayWrapper<Int>

    override fun toArray(in_: Array<Int>, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, in_.size)
    }
    override fun toArray(in_: Any, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, contentSize)
    }
    override fun toArray(copyFirst: Boolean): IntArray =
        if(copyFirst) primitiveArray.copyOf() //.let { it.copyOf() }
        else primitiveArray
    //    override fun getValue(owner: Any?, prop: KProperty<*>): Array<T> = array
}
internal class MutableArrayWrapperImpl_Int(array: IntArray)
    : ArrayWrapperImpl_Int(array), MutableArrayWrapperImpl_Primitive<Int> {
    override fun set(index: Int, element: Int): Int {
        val prevVal= primitiveArray[index]
        primitiveArray[index]= element
        return prevVal
    }
    override fun copy(from: Int, until: Int, reversed: Boolean): MutableArrayWrapper<Int> =
        primitiveArray.copy(from, until, reversed).asWrapped()
}

/*
==============
LongArray
==============
 */

internal open class ArrayWrapperImpl_Long(val primitiveArray: LongArray): ArrayWrapperImpl_Primitive<Long> { //: ArrayWrapperImpl<Int>(primitiveArray.toTypedArray())
    //    constructor(size: Int, init: (Int) -> T): this(Array<T>(size, init))
    override fun get(index: Int): Long = primitiveArray[index]
    override val size: Int get() = primitiveArray.size
    override fun iterator(): Iterator<Long> = primitiveArray.iterator()
    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayWrapper<Long> =
        primitiveArray.copy(from, until, reversed).asWrapped() //as ArrayWrapper<Int>

    override fun toArray(in_: Array<Long>, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, in_.size)
    }
    override fun toArray(in_: Any, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, contentSize)
    }
    override fun toArray(copyFirst: Boolean): LongArray =
        if(copyFirst) primitiveArray.copyOf() //.let { it.copyOf() }
        else primitiveArray
    //    override fun getValue(owner: Any?, prop: KProperty<*>): Array<T> = array
}
//internal open class ArrayWrapperImpl_Long(primitiveArray: LongArray): ArrayWrapperImpl_Object<Long>(primitiveArray.toTypedArray())
internal class MutableArrayWrapperImpl_Long(array: LongArray)
    : ArrayWrapperImpl_Long(array), MutableArrayWrapperImpl_Primitive<Long> {
    override fun set(index: Int, element: Long): Long {
        val prevVal= primitiveArray[index]
        primitiveArray[index]= element
        return prevVal
    }
    override fun copy(from: Int, until: Int, reversed: Boolean): MutableArrayWrapper<Long> =
        primitiveArray.copy(from, until, reversed).asWrapped()
}

/*
==============
ShortArray
==============
 */

internal open class ArrayWrapperImpl_Short(val primitiveArray: ShortArray): ArrayWrapperImpl_Primitive<Short> { //: ArrayWrapperImpl<Int>(primitiveArray.toTypedArray())
    //    constructor(size: Int, init: (Int) -> T): this(Array<T>(size, init))
    override fun get(index: Int): Short = primitiveArray[index]
    override val size: Int get() = primitiveArray.size
    override fun iterator(): Iterator<Short> = primitiveArray.iterator()
    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayWrapper<Short> =
        primitiveArray.copy(from, until, reversed).asWrapped() //as ArrayWrapper<Int>

    override fun toArray(in_: Array<Short>, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, in_.size)
    }
    override fun toArray(in_: Any, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, contentSize)
    }
    override fun toArray(copyFirst: Boolean): ShortArray =
        if(copyFirst) primitiveArray.copyOf() //.let { it.copyOf() }
        else primitiveArray
    //    override fun getValue(owner: Any?, prop: KProperty<*>): Array<T> = array
}
//internal open class ArrayWrapperImpl_Short(primitiveArray: ShortArray): ArrayWrapperImpl_Object<Short>(primitiveArray.toTypedArray())
internal class MutableArrayWrapperImpl_Short(array: ShortArray)
    : ArrayWrapperImpl_Short(array), MutableArrayWrapperImpl_Primitive<Short> {
    override fun set(index: Int, element: Short): Short {
        val prevVal= primitiveArray[index]
        primitiveArray[index]= element
        return prevVal
    }
    override fun copy(from: Int, until: Int, reversed: Boolean): MutableArrayWrapper<Short> =
        primitiveArray.copy(from, until, reversed).asWrapped()
}

/*
==============
FloatArray
==============
 */

internal open class ArrayWrapperImpl_Float(val primitiveArray: FloatArray): ArrayWrapperImpl_Primitive<Float> { //: ArrayWrapperImpl<Int>(primitiveArray.toTypedArray())
    //    constructor(size: Int, init: (Int) -> T): this(Array<T>(size, init))
    override fun get(index: Int): Float = primitiveArray[index]
    override val size: Int get() = primitiveArray.size
    override fun iterator(): Iterator<Float> = primitiveArray.iterator()
    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayWrapper<Float> =
        primitiveArray.copy(from, until, reversed).asWrapped() //as ArrayWrapper<Int>

    override fun toArray(in_: Array<Float>, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, in_.size)
    }
    override fun toArray(in_: Any, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, contentSize)
    }
    override fun toArray(copyFirst: Boolean): FloatArray =
        if(copyFirst) primitiveArray.copyOf() //.let { it.copyOf() }
        else primitiveArray
    //    override fun getValue(owner: Any?, prop: KProperty<*>): Array<T> = array
}
//internal open class ArrayWrapperImpl_Float(primitiveArray: FloatArray): ArrayWrapperImpl_Object<Float>(primitiveArray.toTypedArray())
internal class MutableArrayWrapperImpl_Float(array: FloatArray)
    : ArrayWrapperImpl_Float(array), MutableArrayWrapperImpl_Primitive<Float> {
    override fun set(index: Int, element: Float): Float {
        val prevVal= primitiveArray[index]
        primitiveArray[index]= element
        return prevVal
    }
    override fun copy(from: Int, until: Int, reversed: Boolean): MutableArrayWrapper<Float> =
        primitiveArray.copy(from, until, reversed).asWrapped()
}

/*
==============
DoubleArray
==============
 */

internal open class ArrayWrapperImpl_Double(val primitiveArray: DoubleArray): ArrayWrapperImpl_Primitive<Double> { //: ArrayWrapperImpl<Int>(primitiveArray.toTypedArray())
    //    constructor(size: Int, init: (Int) -> T): this(Array<T>(size, init))
    override fun get(index: Int): Double = primitiveArray[index]
    override val size: Int get() = primitiveArray.size
    override fun iterator(): Iterator<Double> = primitiveArray.iterator()
    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayWrapper<Double> =
        primitiveArray.copy(from, until, reversed).asWrapped() //as ArrayWrapper<Int>

    override fun toArray(in_: Array<Double>, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, in_.size)
    }
    override fun toArray(in_: Any, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, contentSize)
    }
    override fun toArray(copyFirst: Boolean): DoubleArray =
        if(copyFirst) primitiveArray.copyOf() //.let { it.copyOf() }
        else primitiveArray
    //    override fun getValue(owner: Any?, prop: KProperty<*>): Array<T> = array
}
//internal open class ArrayWrapperImpl_Double(primitiveArray: DoubleArray): ArrayWrapperImpl_Object<Double>(primitiveArray.toTypedArray())
internal class MutableArrayWrapperImpl_Double(array: DoubleArray)
    : ArrayWrapperImpl_Double(array), MutableArrayWrapperImpl_Primitive<Double> {
    override fun set(index: Int, element: Double): Double {
        val prevVal= primitiveArray[index]
        primitiveArray[index]= element
        return prevVal
    }
    override fun copy(from: Int, until: Int, reversed: Boolean): MutableArrayWrapper<Double> =
        primitiveArray.copy(from, until, reversed).asWrapped()
}

/*
==============
ByteArray
==============
 */

internal open class ArrayWrapperImpl_Byte(val primitiveArray: ByteArray): ArrayWrapperImpl_Primitive<Byte> { //: ArrayWrapperImpl<Int>(primitiveArray.toTypedArray())
    //    constructor(size: Int, init: (Int) -> T): this(Array<T>(size, init))
    override fun get(index: Int): Byte = primitiveArray[index]
    override val size: Int get() = primitiveArray.size
    override fun iterator(): Iterator<Byte> = primitiveArray.iterator()
    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayWrapper<Byte> =
        primitiveArray.copy(from, until, reversed).asWrapped() //as ArrayWrapper<Int>

    override fun toArray(in_: Array<Byte>, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, in_.size)
    }
    override fun toArray(in_: Any, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, contentSize)
    }
    override fun toArray(copyFirst: Boolean): ByteArray =
        if(copyFirst) primitiveArray.copyOf() //.let { it.copyOf() }
        else primitiveArray
    //    override fun getValue(owner: Any?, prop: KProperty<*>): Array<T> = array
}
//internal open class ArrayWrapperImpl_Byte(primitiveArray: ByteArray): ArrayWrapperImpl_Object<Byte>(primitiveArray.toTypedArray())
internal class MutableArrayWrapperImpl_Byte(array: ByteArray)
    : ArrayWrapperImpl_Byte(array), MutableArrayWrapperImpl_Primitive<Byte> {
    override fun set(index: Int, element: Byte): Byte {
        val prevVal= primitiveArray[index]
        primitiveArray[index]= element
        return prevVal
    }
    override fun copy(from: Int, until: Int, reversed: Boolean): MutableArrayWrapper<Byte> =
        primitiveArray.copy(from, until, reversed).asWrapped()
}

/*
==============
BooleanArray
==============
 */

internal open class ArrayWrapperImpl_Boolean(val primitiveArray: BooleanArray): ArrayWrapperImpl_Primitive<Boolean> { //: ArrayWrapperImpl<Int>(primitiveArray.toTypedArray())
    //    constructor(size: Int, init: (Int) -> T): this(Array<T>(size, init))
    override fun get(index: Int): Boolean = primitiveArray[index]
    override val size: Int get() = primitiveArray.size
    override fun iterator(): Iterator<Boolean> = primitiveArray.iterator()
    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayWrapper<Boolean> =
        primitiveArray.copy(from, until, reversed).asWrapped() //as ArrayWrapper<Int>

    override fun toArray(in_: Array<Boolean>, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, in_.size)
    }
    override fun toArray(in_: Any, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, contentSize)
    }
    override fun toArray(copyFirst: Boolean): BooleanArray =
        if(copyFirst) primitiveArray.copyOf() //.let { it.copyOf() }
        else primitiveArray
    //    override fun getValue(owner: Any?, prop: KProperty<*>): Array<T> = array
}
//internal open class ArrayWrapperImpl_Boolean(primitiveArray: BooleanArray): ArrayWrapperImpl_Object<Boolean>(primitiveArray.toTypedArray())
internal class MutableArrayWrapperImpl_Boolean(array: BooleanArray)
    : ArrayWrapperImpl_Boolean(array), MutableArrayWrapperImpl_Primitive<Boolean> {
    override fun set(index: Int, element: Boolean): Boolean {
        val prevVal= primitiveArray[index]
        primitiveArray[index]= element
        return prevVal
    }
    override fun copy(from: Int, until: Int, reversed: Boolean): MutableArrayWrapper<Boolean> =
        primitiveArray.copy(from, until, reversed).asWrapped()
}

/*
==============
CharArray
==============
 */

internal open class ArrayWrapperImpl_Char(val primitiveArray: CharArray): ArrayWrapperImpl_Primitive<Char> { //: ArrayWrapperImpl<Int>(primitiveArray.toTypedArray())
    //    constructor(size: Int, init: (Int) -> T): this(Array<T>(size, init))
    override fun get(index: Int): Char = primitiveArray[index]
    override val size: Int get() = primitiveArray.size
    override fun iterator(): Iterator<Char> = primitiveArray.iterator()
    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayWrapper<Char> =
        primitiveArray.copy(from, until, reversed).asWrapped() //as ArrayWrapper<Int>

    override fun toArray(in_: Array<Char>, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, in_.size)
    }
    override fun toArray(in_: Any, copyFirst: Boolean): Any = in_.apply {
        arrayCopyAll(primitiveArray, 0, in_, 0, contentSize)
    }
    override fun toArray(copyFirst: Boolean): CharArray =
        if(copyFirst) primitiveArray.copyOf() //.let { it.copyOf() }
        else primitiveArray
    //    override fun getValue(owner: Any?, prop: KProperty<*>): Array<T> = array
}
//internal open class ArrayWrapperImpl_Char(primitiveArray: CharArray): ArrayWrapperImpl_Object<Char>(primitiveArray.toTypedArray())
internal class MutableArrayWrapperImpl_Char(array: CharArray)
    : ArrayWrapperImpl_Char(array), MutableArrayWrapperImpl_Primitive<Char> {
    override fun set(index: Int, element: Char): Char {
        val prevVal= primitiveArray[index]
        primitiveArray[index]= element
        return prevVal
    }
    override fun copy(from: Int, until: Int, reversed: Boolean): MutableArrayWrapper<Char> =
        primitiveArray.copy(from, until, reversed).asWrapped()
}