package sidev.lib.structure.data

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.collection.array.copy

interface ArrayArrangeable<T>: Arrangeable<T>, RangeCopyable<ArrayArrangeable<T>> {
    override val maxRange: Int
        get() = size
}


internal class ArrayArrangeableImpl<T>(val array: Array<Any?>): ArrayArrangeable<T> {
    constructor(size: Int, init: (Int) -> T): this(Array<Any?>(size, init))

    override val size: Int
        get() = array.size
    //    override val origin: Any get() = array
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    override fun get(index: Int): T = array[index] as T
    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    override fun set(index: Int, element: T): T {
        val old= array[index]
        array[index]= element
        return old as T
    }
    override fun set_(index: Int, element: T) {
        array[index]= element
    }

    @Suppress(SuppressLiteral.UNCHECKED_CAST)
    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayArrangeable<T> =
        array.copy(from, until, reversed).asArrangeable() as ArrayArrangeable<T>

    override fun equals(other: Any?): Boolean = array.equals(other)
    override fun hashCode(): Int = array.hashCode()
    override fun toString(): String = array.joinToString(prefix = "Array[", postfix = "]")
}


internal class ByteArrayArrangeable(val array: ByteArray): ArrayArrangeable<Byte> {
    constructor(size: Int, init: (Int) -> Byte): this(ByteArray(size, init))

    override val size: Int
        get() = array.size
    //    override val origin: Any get() = array
    override fun get(index: Int): Byte = array[index]
    override fun set(index: Int, element: Byte): Byte {
        val old= array[index]
        array[index]= element
        return old
    }
    override fun set_(index: Int, element: Byte) {
        array[index]= element
    }

    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayArrangeable<Byte> =
        array.copy(from, until, reversed).asArrangeable()

    override fun equals(other: Any?): Boolean = array.equals(other)
    override fun hashCode(): Int = array.hashCode()
    override fun toString(): String = array.joinToString(prefix = "Array[", postfix = "]")
}

internal class ShortArrayArrangeable(val array: ShortArray): ArrayArrangeable<Short> {
    constructor(size: Int, init: (Int) -> Short): this(ShortArray(size, init))

    override val size: Int
        get() = array.size
    //    override val origin: Any get() = array
    override fun get(index: Int): Short = array[index]
    override fun set(index: Int, element: Short): Short {
        val old= array[index]
        array[index]= element
        return old
    }
    override fun set_(index: Int, element: Short) {
        array[index]= element
    }

    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayArrangeable<Short> =
        array.copy(from, until, reversed).asArrangeable()

    override fun equals(other: Any?): Boolean = array.equals(other)
    override fun hashCode(): Int = array.hashCode()
    override fun toString(): String = array.joinToString(prefix = "Array[", postfix = "]")
}

internal class IntArrayArrangeable(val array: IntArray): ArrayArrangeable<Int> {
    constructor(size: Int, init: (Int) -> Int): this(IntArray(size, init))

    override val size: Int
        get() = array.size
    //    override val origin: Any get() = array
    override fun get(index: Int): Int = array[index]
    override fun set(index: Int, element: Int): Int {
        val old= array[index]
        array[index]= element
        return old
    }
    override fun set_(index: Int, element: Int) {
        array[index]= element
    }

    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayArrangeable<Int> =
        array.copy(from, until, reversed).asArrangeable()

    override fun equals(other: Any?): Boolean = array.equals(other)
    override fun hashCode(): Int = array.hashCode()
    override fun toString(): String = array.joinToString(prefix = "Array[", postfix = "]")
}

internal class LongArrayArrangeable(val array: LongArray): ArrayArrangeable<Long> {
    constructor(size: Int, init: (Int) -> Long): this(LongArray(size, init))

    override val size: Int
        get() = array.size
    //    override val origin: Any get() = array
    override fun get(index: Int): Long = array[index]
    override fun set(index: Int, element: Long): Long {
        val old= array[index]
        array[index]= element
        return old
    }
    override fun set_(index: Int, element: Long) {
        array[index]= element
    }

    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayArrangeable<Long> =
        array.copy(from, until, reversed).asArrangeable()

    override fun equals(other: Any?): Boolean = array.equals(other)
    override fun hashCode(): Int = array.hashCode()
    override fun toString(): String = array.joinToString(prefix = "Array[", postfix = "]")
}

internal class FloatArrayArrangeable(val array: FloatArray): ArrayArrangeable<Float> {
    constructor(size: Int, init: (Int) -> Float): this(FloatArray(size, init))

    override val size: Int
        get() = array.size
    //    override val origin: Any get() = array
    override fun get(index: Int): Float = array[index]
    override fun set(index: Int, element: Float): Float {
        val old= array[index]
        array[index]= element
        return old
    }
    override fun set_(index: Int, element: Float) {
        array[index]= element
    }

    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayArrangeable<Float> =
        array.copy(from, until, reversed).asArrangeable()

    override fun equals(other: Any?): Boolean = array.equals(other)
    override fun hashCode(): Int = array.hashCode()
    override fun toString(): String = array.joinToString(prefix = "Array[", postfix = "]")
}

internal class DoubleArrayArrangeable(val array: DoubleArray): ArrayArrangeable<Double> {
    constructor(size: Int, init: (Int) -> Double): this(DoubleArray(size, init))

    override val size: Int
        get() = array.size
    //    override val origin: Any get() = array
    override fun get(index: Int): Double = array[index]
    override fun set(index: Int, element: Double): Double {
        val old= array[index]
        array[index]= element
        return old
    }
    override fun set_(index: Int, element: Double) {
        array[index]= element
    }

    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayArrangeable<Double> =
        array.copy(from, until, reversed).asArrangeable()

    override fun equals(other: Any?): Boolean = array.equals(other)
    override fun hashCode(): Int = array.hashCode()
    override fun toString(): String = array.joinToString(prefix = "Array[", postfix = "]")
}

internal class CharArrayArrangeable(val array: CharArray): ArrayArrangeable<Char> {
    constructor(size: Int, init: (Int) -> Char): this(CharArray(size, init))

    override val size: Int
        get() = array.size
    //    override val origin: Any get() = array
    override fun get(index: Int): Char = array[index]
    override fun set(index: Int, element: Char): Char {
        val old= array[index]
        array[index]= element
        return old
    }
    override fun set_(index: Int, element: Char) {
        array[index]= element
    }

    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayArrangeable<Char> =
        array.copy(from, until, reversed).asArrangeable()

    override fun equals(other: Any?): Boolean = array.equals(other)
    override fun hashCode(): Int = array.hashCode()
    override fun toString(): String = array.joinToString(prefix = "Array[", postfix = "]")
}

internal class BooleanArrayArrangeable(val array: BooleanArray): ArrayArrangeable<Boolean> {
    constructor(size: Int, init: (Int) -> Boolean): this(BooleanArray(size, init))

    override val size: Int
        get() = array.size
    //    override val origin: Any get() = array
    override fun get(index: Int): Boolean = array[index]
    override fun set(index: Int, element: Boolean): Boolean {
        val old= array[index]
        array[index]= element
        return old
    }
    override fun set_(index: Int, element: Boolean) {
        array[index]= element
    }

    override fun copy(from: Int, until: Int, reversed: Boolean): ArrayArrangeable<Boolean> =
        array.copy(from, until, reversed).asArrangeable()

    override fun equals(other: Any?): Boolean = array.equals(other)
    override fun hashCode(): Int = array.hashCode()
    override fun toString(): String = array.joinToString(prefix = "Array[", postfix = "]")
}