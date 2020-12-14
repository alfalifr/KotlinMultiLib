package sidev.lib.math.number

import sidev.lib.exception.UnavailableOperationExc
import sidev.lib.number.minus
import sidev.lib.number.plus
import sidev.lib.structure.data.Cloneable

/**
 * Tipe data untuk angka dengan tanda desimal, yaitu titik (.).
 * [floatValue] dan [doubleValue] harus memiliki nilai yg sesuai satu sama lain.
 * Misal nilai [floatValue] == 123.23, maka [doubleValue] == 123.23.
 */
abstract class FloatingNumber
internal constructor(): Number(), BroaderNumber, Cloneable<FloatingNumber> {

    companion object {
        fun from(float: Float): FloatingNumber = FloatFloatingNumber(float)
        fun from(double: Double): FloatingNumber = DoubleFloatingNumber(double)
        fun fromAny(number: Number): FloatingNumber = when {
            number in floatValueRange -> from(number.toFloat())
            number in doubleValueRange ->from(number.toDouble())
            else -> throw UnavailableOperationExc(
                this::class,
                "`number` : \"$number\" memiliki nilai di luar jangkauan angka primitif."
            )
        }
    }

    abstract val floatValue: Float //= floatValue
    abstract val doubleValue: Double //= doubleValue
/*
    init {
        when {
            floatValue.compareTo(0) != 0 -> this.doubleValue= floatValue.toDouble()
            doubleValue.compareTo(0) != 0 -> this.floatValue= doubleValue.toFloat()
        }
    }
 */
/*
    /**
     * Menunjukan bahwa nilai [intValue] belum overflow
     * sehingga nilai [floatValueRange] scr program sama dg nila [doubleValue].
     */
    override val isStable: Boolean
        get() = floatValue.compareTo(doubleValue) == 0
 */

    /**
     * Menunjukan bahwa objek [FloatingNumber] ini memiliki nilai [floatValueRange] dan [doubleValue] yang sama.
     */
    override val isInGoodIntegrity: Boolean
        get() = floatValue.isSameAs(doubleValue)


    operator fun plus(other: Byte): Double = doubleValue + other
    operator fun plus(other: Short): Double = doubleValue + other
    operator fun plus(other: Int): Double = doubleValue + other
    operator fun plus(other: Long): Double = doubleValue + other

    operator fun plus(other: Float): Float = floatValue + other
    operator fun plus(other: Double): Double = doubleValue + other
    abstract operator fun plus(other: FloatingNumber): FloatingNumber //= from(doubleValue + other.doubleValue)
    operator fun plus(other: WholeNumber): FloatingNumber = fromAny(primitiveValue + other.primitiveValue)


    operator fun minus(other: Byte): Double = doubleValue - other
    operator fun minus(other: Short): Double = doubleValue - other
    operator fun minus(other: Int): Double = doubleValue - other
    operator fun minus(other: Long): Double = doubleValue - other

    operator fun minus(other: Float): Float = floatValue - other
    operator fun minus(other: Double): Double = doubleValue - other
    abstract operator fun minus(other: FloatingNumber): FloatingNumber //= from(doubleValue - other.doubleValue)
    operator fun minus(other: WholeNumber): FloatingNumber = fromAny(primitiveValue - other.primitiveValue)


    operator fun times(other: Byte): Double = doubleValue * other
    operator fun times(other: Short): Double = doubleValue * other
    operator fun times(other: Int): Double = doubleValue * other
    operator fun times(other: Long): Double = doubleValue * other

    operator fun times(other: Float): Float = floatValue * other
    operator fun times(other: Double): Double = doubleValue * other
    abstract operator fun times(other: FloatingNumber): FloatingNumber //= from(doubleValue * other.doubleValue)
    operator fun times(other: WholeNumber): FloatingNumber = fromAny(primitiveValue - other.primitiveValue)


    operator fun div(other: Byte): Double = doubleValue / other
    operator fun div(other: Short): Double = doubleValue / other
    operator fun div(other: Int): Double = doubleValue / other
    operator fun div(other: Long): Double = doubleValue / other

    operator fun div(other: Float): Float = floatValue / other
    operator fun div(other: Double): Double = doubleValue / other
    abstract operator fun div(other: FloatingNumber): FloatingNumber //= from(doubleValue / other.doubleValue)
    operator fun div(other: WholeNumber): FloatingNumber = fromAny(primitiveValue - other.primitiveValue)


    operator fun rem(other: Byte): Double = doubleValue % other
    operator fun rem(other: Short): Double = doubleValue % other
    operator fun rem(other: Int): Double = doubleValue % other
    operator fun rem(other: Long): Double = doubleValue % other

    operator fun rem(other: Float): Float = floatValue % other
    operator fun rem(other: Double): Double = doubleValue % other
    abstract operator fun rem(other: FloatingNumber): FloatingNumber //= from(doubleValue % other.doubleValue)
    operator fun rem(other: WholeNumber): FloatingNumber = fromAny(primitiveValue - other.primitiveValue)


    operator fun compareTo(other: Byte): Int = doubleValue.compareTo(other)
    operator fun compareTo(other: Short): Int = doubleValue.compareTo(other)
    operator fun compareTo(other: Int): Int = doubleValue.compareTo(other)
    operator fun compareTo(other: Long): Int = doubleValue.compareTo(other)

    operator fun compareTo(other: Float): Int = floatValue.compareTo(other)
    operator fun compareTo(other: Double): Int = doubleValue.compareTo(other)
    abstract operator fun compareTo(other: FloatingNumber): Int //= floatValue.compareTo(other.floatValue)
    abstract operator fun compareTo(other: WholeNumber): Int //= floatValue.compareTo(other.longValue)

    override fun toDouble(): Double = doubleValue
    override fun toFloat(): Float = floatValue

    override fun toByte(): Byte = doubleValue.toInt().toByte()
    override fun toShort(): Short = doubleValue.toInt().toShort()

    override fun toChar(): Char = doubleValue.toChar()
    override fun toInt(): Int = doubleValue.toInt()
    override fun toLong(): Long = doubleValue.toLong()

    override fun clone_(isShallowClone: Boolean): FloatingNumber = when(this) {
        is FloatFloatingNumber -> FloatFloatingNumber(floatValue)
        is DoubleFloatingNumber -> DoubleFloatingNumber(doubleValue)
        else -> throw UnavailableOperationExc(
        this::class,
        detailMsg = "`FloatingNumber` memiliki inheritance lain. Programmer harus meng-override method `clone_`."
        )
    }

    /**
     * Mengecilkan ukuran space pada memory yg digunakan untuk menyimpan [primitiveValue].
     */
    override fun trim(): FloatingNumber = when {
        primitiveValue in floatValueRange -> from(floatValue)
        primitiveValue in doubleValueRange ->from(doubleValue)
        else -> throw UnavailableOperationExc(
            this::class,
            "`primitiveValue` : \"$primitiveValue\" memiliki nilai di luar jangkauan angka primitif."
        )
    }

    override fun equals(other: Any?): Boolean = when(other){
        is Float -> floatValue == other
        is Double -> doubleValue == other
        is FloatingNumber -> {
            //other.checkIntegrity() //Gak perlu karena setter untuk nilai masing-masing tipe angka private
            floatValue == other.floatValue
        }
        is WholeNumber -> doubleValue.compareTo(other.longValue) == 0
        else -> super.equals(other)
    }

    override fun hashCode(): Int = primitiveValue.hashCode()
    override fun toString(): String = primitiveValue.toString()
}