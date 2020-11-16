package sidev.lib.math.number

internal class LongWholeNumber(override val longValue: Long): WholeNumber() {
    override val byteValue: Byte
        get() = longValue.toByte()
    override val shortValue: Short
        get() = longValue.toShort()
    override val intValue: Int
        get() = longValue.toInt()
    override val bitSize: Int
        get() = Long.SIZE_BITS
    override val maxValue: Long
        get() = Long.MAX_VALUE
    override val minValue: Long
        get() = Long.MIN_VALUE
    override val primitiveValue: Long
        get() = longValue

    //Operasi pada Long mengabaikan overflow karena `Long` adalah tipe data dg ukuran memory space yg paling besar di Kotlin.
    override fun plus(other: WholeNumber): WholeNumber = LongWholeNumber(primitiveValue + other.primitiveValue.toLong())
    override fun minus(other: WholeNumber): WholeNumber = LongWholeNumber(primitiveValue - other.primitiveValue.toLong())
    override fun times(other: WholeNumber): WholeNumber = LongWholeNumber(primitiveValue * other.primitiveValue.toLong())
    override fun div(other: WholeNumber): WholeNumber = LongWholeNumber(primitiveValue / other.primitiveValue.toLong())
    override fun rem(other: WholeNumber): WholeNumber = LongWholeNumber(primitiveValue % other.primitiveValue.toLong())

    override fun compareTo(other: WholeNumber): Int = longValue.compareTo(other.longValue)
    override fun compareTo(other: FloatingNumber): Int = longValue.compareTo(other.primitiveValue)
}