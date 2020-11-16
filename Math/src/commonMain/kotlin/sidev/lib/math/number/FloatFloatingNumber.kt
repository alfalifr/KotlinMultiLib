package sidev.lib.math.number

import sidev.lib.number.isNegative
import sidev.lib.number.isPositive

internal class FloatFloatingNumber(override val floatValue: Float): FloatingNumber() {
    override val doubleValue: Double
        get() = floatValue.toDouble()
    override val bitSize: Int
        get() = Float.SIZE_BITS
    override val maxValue: Float
        get() = Float.MAX_VALUE
    override val minValue: Float
        get() = Float.MIN_VALUE
    override val primitiveValue: Float
        get() = floatValue

    override fun plus(other: FloatingNumber): FloatingNumber {
        if(!isStableWith(other))
            DoubleFloatingNumber(doubleValue + other.primitiveValue.toDouble())

        val otherVal= other.primitiveValue.toFloat() //if(other.isStableWith(this)) other.primitiveValue.toInt() else other.

        val thisVal= primitiveValue
        val newVal= thisVal + otherVal

        return if(otherVal.isPositive() && newVal < thisVal
            || otherVal.isNegative() && newVal > thisVal)
            DoubleFloatingNumber(doubleValue + otherVal)
        else FloatFloatingNumber(newVal)
    }

    override fun minus(other: FloatingNumber): FloatingNumber {
        if(!isStableWith(other))
            DoubleFloatingNumber(doubleValue - other.primitiveValue.toDouble())

        val otherVal= other.primitiveValue.toFloat() //if(other.isStableWith(this)) other.primitiveValue.toInt() else other.

        val thisVal= primitiveValue
        val newVal= thisVal - otherVal

        return if(otherVal.isPositive() && newVal > thisVal
            || otherVal.isNegative() && newVal < thisVal)
            DoubleFloatingNumber(doubleValue - otherVal)
        else FloatFloatingNumber(newVal)
    }

    override fun times(other: FloatingNumber): FloatingNumber {
        if(!isStableWith(other))
            DoubleFloatingNumber(doubleValue * other.primitiveValue.toDouble())

        val otherVal= other.primitiveValue.toFloat() //if(other.isStableWith(this)) other.primitiveValue.toInt() else other.

        val thisVal= primitiveValue
        val newVal= thisVal * otherVal

        return if((thisVal.isPositive() && otherVal.isPositive() || thisVal.isNegative() && otherVal.isNegative()) && newVal < thisVal
            || (thisVal.isPositive() && otherVal.isNegative() || thisVal.isNegative() && otherVal.isPositive()) && newVal > thisVal)
            DoubleFloatingNumber(doubleValue * otherVal)
        else FloatFloatingNumber(newVal)
    }

    override fun div(other: FloatingNumber): FloatingNumber = FloatFloatingNumber((primitiveValue / other.primitiveValue).toFloat())
    override fun rem(other: FloatingNumber): FloatingNumber = FloatFloatingNumber((primitiveValue % other.primitiveValue).toFloat())

    override fun compareTo(other: FloatingNumber): Int = floatValue.compareTo(other.floatValue)
    override fun compareTo(other: WholeNumber): Int = floatValue.compareTo(other.primitiveValue)
}