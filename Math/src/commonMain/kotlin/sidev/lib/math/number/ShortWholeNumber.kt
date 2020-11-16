package sidev.lib.math.number

import sidev.lib.number.isNegative
import sidev.lib.number.isPositive

internal class ShortWholeNumber(override val shortValue: Short): WholeNumber() {
    override val byteValue: Byte
        get() = shortValue.toByte()
    override val intValue: Int
        get() = shortValue.toInt()
    override val longValue: Long
        get() = shortValue.toLong()
    override val bitSize: Int
        get() = Short.SIZE_BITS
    override val maxValue: Short
        get() = Short.MAX_VALUE
    override val minValue: Short
        get() = Short.MIN_VALUE
    override val primitiveValue: Short
        get() = shortValue

    override fun plus(other: WholeNumber): WholeNumber {
        if(!isStableWith(other))
            return fromAny(longValue + other.primitiveValue)

        val otherVal= other.primitiveValue.toShort() //if(other.isStableWith(this)) other.primitiveValue.toInt() else other.

        val thisVal= primitiveValue
        val newVal= (thisVal + otherVal).toShort()

        return if(otherVal.isPositive() && newVal < thisVal
            || otherVal.isNegative() && newVal > thisVal)
            IntWholeNumber(intValue + otherVal)
        else ShortWholeNumber(newVal)
    }

    override fun minus(other: WholeNumber): WholeNumber {
        if(!isStableWith(other))
            return fromAny(longValue - other.primitiveValue)

        val otherVal= other.primitiveValue.toShort() //if(other.isStableWith(this)) other.primitiveValue.toInt() else other.

        val thisVal= primitiveValue
        val newVal= (thisVal - otherVal).toShort()

        return if(otherVal.isPositive() && newVal > thisVal
            || otherVal.isNegative() && newVal < thisVal)
            IntWholeNumber(intValue - otherVal)
        else ShortWholeNumber(newVal)
    }

    override fun times(other: WholeNumber): WholeNumber {
        if(!isStableWith(other))
            return fromAny(longValue * other.primitiveValue)

        val otherVal= other.primitiveValue.toShort() //if(other.isStableWith(this)) other.primitiveValue.toInt() else other.

        val thisVal= primitiveValue
        val newVal= (thisVal * otherVal).toShort()

        return if((thisVal.isPositive() && otherVal.isPositive() || thisVal.isNegative() && otherVal.isNegative()) && newVal < thisVal
            || (thisVal.isPositive() && otherVal.isNegative() || thisVal.isNegative() && otherVal.isPositive()) && newVal > thisVal)
            IntWholeNumber(intValue * otherVal)
        else ShortWholeNumber(newVal)
    }

    override fun div(other: WholeNumber): WholeNumber = ShortWholeNumber((primitiveValue / other.primitiveValue).toShort())
    override fun rem(other: WholeNumber): WholeNumber = ShortWholeNumber((primitiveValue % other.primitiveValue).toShort())

    override fun compareTo(other: WholeNumber): Int = shortValue.compareTo(other.shortValue)
    override fun compareTo(other: FloatingNumber): Int = shortValue.compareTo(other.primitiveValue)
}