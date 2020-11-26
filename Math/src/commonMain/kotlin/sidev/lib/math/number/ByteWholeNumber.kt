package sidev.lib.math.number

import sidev.lib.number.compareTo
import sidev.lib.number.isNegative
import sidev.lib.number.isPositive
import sidev.lib.number.rem
import sidev.lib.number.plus
import sidev.lib.number.div
import sidev.lib.number.times
import sidev.lib.number.minus

internal class ByteWholeNumber(override val byteValue: Byte): WholeNumber() {
    override val shortValue: Short
        get() = byteValue.toShort()
    override val intValue: Int
        get() = byteValue.toInt()
    override val longValue: Long
        get() = byteValue.toLong()
    override val bitSize: Int
        get() = Byte.SIZE_BITS
    override val maxValue: Byte
        get() = Byte.MAX_VALUE
    override val minValue: Byte
        get() = Byte.MIN_VALUE
    override val primitiveValue: Byte
        get() = byteValue

    override fun plus(other: WholeNumber): WholeNumber {
        if(!isStableWith(other))
            return fromAny(longValue + other.primitiveValue)

        val otherVal= other.primitiveValue.toByte() //if(other.isStableWith(this)) other.primitiveValue.toInt() else other.

        val thisVal= primitiveValue
        val newVal= (thisVal + otherVal).toShort()

        return if(otherVal.isPositive() && newVal < thisVal
            || otherVal.isNegative() && newVal > thisVal)
            ShortWholeNumber((shortValue + otherVal).toShort())
        else ShortWholeNumber(newVal)
    }

    override fun minus(other: WholeNumber): WholeNumber {
        if(!isStableWith(other))
            return fromAny(longValue - other.primitiveValue)

        val otherVal= other.primitiveValue.toByte() //if(other.isStableWith(this)) other.primitiveValue.toInt() else other.

        val thisVal= primitiveValue
        val newVal= (thisVal + otherVal).toByte()

        return if(otherVal.isPositive() && newVal > thisVal
            || otherVal.isNegative() && newVal < thisVal)
            ShortWholeNumber((shortValue - otherVal).toShort())
        else ByteWholeNumber(newVal)
    }

    override fun times(other: WholeNumber): WholeNumber {
        if(!isStableWith(other))
            return fromAny(longValue * other.primitiveValue)

        val otherVal= other.primitiveValue.toByte() //if(other.isStableWith(this)) other.primitiveValue.toInt() else other.

        val thisVal= primitiveValue
        val newVal= (thisVal * otherVal).toByte()

        return if((thisVal.isPositive() && otherVal.isPositive() || thisVal.isNegative() && otherVal.isNegative()) && newVal < thisVal
            || (thisVal.isPositive() && otherVal.isNegative() || thisVal.isNegative() && otherVal.isPositive()) && newVal > thisVal)
            ShortWholeNumber((shortValue * otherVal).toShort())
        else ByteWholeNumber(newVal)
    }

    override fun div(other: WholeNumber): WholeNumber = ByteWholeNumber((primitiveValue / other.primitiveValue).toByte())
    override fun rem(other: WholeNumber): WholeNumber = ByteWholeNumber((primitiveValue % other.primitiveValue).toByte())

    override fun compareTo(other: WholeNumber): Int = byteValue.compareTo(other.byteValue)
    override fun compareTo(other: FloatingNumber): Int = byteValue.compareTo(other.primitiveValue)
}