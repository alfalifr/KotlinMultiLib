package sidev.lib.math.number

internal class DoubleFloatingNumber(override val doubleValue: Double): FloatingNumber() {
    override val floatValue: Float
        get() = doubleValue.toFloat()
    override val bitSize: Int
        get() = Double.SIZE_BITS
    override val maxValue: Double
        get() = Double.MAX_VALUE
    override val minValue: Double
        get() = Double.MIN_VALUE
    override val primitiveValue: Double
        get() = doubleValue

    override fun plus(other: FloatingNumber): FloatingNumber = DoubleFloatingNumber(primitiveValue + other.primitiveValue.toDouble())
    override fun minus(other: FloatingNumber): FloatingNumber = DoubleFloatingNumber(primitiveValue + other.primitiveValue.toDouble())
    override fun times(other: FloatingNumber): FloatingNumber = DoubleFloatingNumber(primitiveValue + other.primitiveValue.toDouble())
    override fun div(other: FloatingNumber): FloatingNumber = DoubleFloatingNumber(primitiveValue + other.primitiveValue.toDouble())
    override fun rem(other: FloatingNumber): FloatingNumber = DoubleFloatingNumber(primitiveValue + other.primitiveValue.toDouble())

    override fun compareTo(other: FloatingNumber): Int = doubleValue.compareTo(other.doubleValue)
    override fun compareTo(other: WholeNumber): Int = doubleValue.compareTo(other.primitiveValue)
}