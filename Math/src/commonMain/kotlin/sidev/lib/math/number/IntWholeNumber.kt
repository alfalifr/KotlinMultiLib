package sidev.lib.math.number

import sidev.lib.number.isNegative
import sidev.lib.number.isPositive

internal class IntWholeNumber(override val intValue: Int): WholeNumber() {
    override val byteValue: Byte
        get() = intValue.toByte()
    override val shortValue: Short
        get() = intValue.toShort()
    override val longValue: Long
        get() = intValue.toLong()
    override val bitSize: Int
        get() = Int.SIZE_BITS
    override val minValue: Int
        get() = Int.MIN_VALUE
    override val maxValue: Int
        get() = Int.MAX_VALUE
    override val primitiveValue: Int
        get() = intValue


    override fun plus(other: WholeNumber): WholeNumber {
        if(!isStableWith(other))
            LongWholeNumber(longValue + other.primitiveValue.toLong())

        val otherVal= other.primitiveValue.toInt() //if(other.isStableWith(this)) other.primitiveValue.toInt() else other.

        val thisVal= primitiveValue
        val newVal= thisVal + otherVal

        return if(otherVal.isPositive() && newVal < thisVal
            || otherVal.isNegative() && newVal > thisVal)
            LongWholeNumber(longValue + otherVal)
        else IntWholeNumber(newVal)
    }

    override fun minus(other: WholeNumber): WholeNumber {
        if(!isStableWith(other))
            LongWholeNumber(longValue - other.primitiveValue.toLong())

        val otherVal= other.primitiveValue.toInt() //if(other.isStableWith(this)) other.primitiveValue.toInt() else other.

        val thisVal= primitiveValue
        val newVal= thisVal - otherVal

        return if(otherVal.isPositive() && newVal > thisVal
            || otherVal.isNegative() && newVal < thisVal)
            LongWholeNumber(longValue - otherVal)
        else IntWholeNumber(newVal)
    }

    override fun times(other: WholeNumber): WholeNumber {
        if(!isStableWith(other))
            LongWholeNumber(longValue * other.primitiveValue.toLong())

        val otherVal= other.primitiveValue.toInt() //if(other.isStableWith(this)) other.primitiveValue.toInt() else other.

        val thisVal= primitiveValue
        val newVal= thisVal * otherVal

        return if((thisVal.isPositive() && otherVal.isPositive() || thisVal.isNegative() && otherVal.isNegative()) && newVal < thisVal
            || (thisVal.isPositive() && otherVal.isNegative() || thisVal.isNegative() && otherVal.isPositive()) && newVal > thisVal)
            LongWholeNumber(longValue * otherVal)
        else IntWholeNumber(newVal)
    }

    //Dua operasi berikut tidak akan pernah overflow karena operasi di bawah ini menghasilkan angka yg mendekati 0.
    override fun div(other: WholeNumber): WholeNumber = IntWholeNumber((primitiveValue / other.primitiveValue).toInt())
    override fun rem(other: WholeNumber): WholeNumber = IntWholeNumber((primitiveValue % other.primitiveValue).toInt())

    override fun compareTo(other: WholeNumber): Int = intValue.compareTo(other.intValue)
    override fun compareTo(other: FloatingNumber): Int = intValue.compareTo(other.primitiveValue)
}

