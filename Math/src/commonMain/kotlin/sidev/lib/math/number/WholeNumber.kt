package sidev.lib.math.number

import sidev.lib.console.prine
import sidev.lib.exception.UnavailableOperationExc
import sidev.lib.number.plus
import sidev.lib.structure.data.Cloneable
import sidev.lib.range.contains

/**
 * Tipe data untuk angka bulat. Tipe data ini merepresentasikan tipe data primitive pada Java.
 * [longValue] dan [intValue] harus memiliki nilai yg sesuai satu sama lain.
 * Misal nilai [longValue] == 123, maka [intValue] == 123.
 */
abstract class WholeNumber
internal constructor(): Number(), BroaderNumber, Cloneable<WholeNumber> {

    companion object {
        fun from(byte: Byte): WholeNumber = ByteWholeNumber(byte)
        fun from(short: Short): WholeNumber = ShortWholeNumber(short)
        fun from(int: Int): WholeNumber = IntWholeNumber(int)
        fun from(long: Long): WholeNumber = LongWholeNumber(long)
        fun fromAny(number: Number): WholeNumber = when {
            number in byteValueRange -> from(number.toByte())
            number in shortValueRange -> from(number.toShort())
            number in intValueRange -> from(number.toInt())
            number in longValueRange -> from(number.toLong())
            else -> throw UnavailableOperationExc(
                this::class,
                "`number` : \"$number\" memiliki nilai di luar jangkauan angka primitif."
            )
        }
    }

    abstract val byteValue: Byte //= byteValue
    abstract val shortValue: Short //= shortValue
    abstract val intValue: Int //= intValue
    abstract val longValue: Long //= longValue
/*
    init {
        when {
            byteValue.compareTo(0) != 0 -> {
                this.shortValue= byteValue.toShort()
                this.intValue= byteValue.toInt()
                this.longValue= byteValue.toLong()
            }
            shortValue.compareTo(0) != 0 -> {
                this.byteValue= shortValue.toByte()
                this.intValue= shortValue.toInt()
                this.longValue= shortValue.toLong()
            }
            intValue != 0 -> {
                this.byteValue= intValue.toByte()
                this.shortValue= intValue.toShort()
                this.longValue= intValue.toLong()
            }
            longValue.compareTo(0) != 0 -> {
                this.byteValue= longValue.toByte()
                this.shortValue= longValue.toShort()
                this.intValue= longValue.toInt()
            }
        }
    }
 */
/*
    /**
     * Menunjukan bahwa nilai [intValue] belum overflow
     * sehingga nilai [intValue] scr program sama dg nila [longValue].
     */
    override val isStable: Boolean
        get()= intValue.compareTo(longValue) == 0
 */

    /**
     * Menunjukan bahwa objek [WholeNumber] ini memiliki nilai
     * [byteValue], [shortValue], [intValue], dan [longValue] yang sama.
     */
    override val isInGoodIntegrity: Boolean
        get()= byteValue.isSameAs(shortValue)
                && shortValue.isSameAs(intValue)
                && intValue.isSameAs(longValue)


    operator fun plus(other: Float): Float = longValue + other
    operator fun plus(other: Double): Double = longValue + other

    operator fun plus(other: Byte): Byte = (byteValue + other).toByte()
    operator fun plus(other: Short): Short = (shortValue + other).toShort()
    operator fun plus(other: Int): Int = intValue + other
    operator fun plus(other: Long): Long = longValue + other
    abstract operator fun plus(other: WholeNumber): WholeNumber //= from(longValue + other.longValue)
    operator fun plus(other: FloatingNumber): FloatingNumber = FloatingNumber.fromAny(primitiveValue + other.primitiveValue)


    operator fun minus(other: Float): Float = longValue - other
    operator fun minus(other: Double): Double = longValue - other

    operator fun minus(other: Byte): Byte = (byteValue - other).toByte()
    operator fun minus(other: Short): Short = (shortValue - other).toShort()
    operator fun minus(other: Int): Int = intValue - other
    operator fun minus(other: Long): Long = longValue - other
    abstract operator fun minus(other: WholeNumber): WholeNumber //= from(longValue - other.longValue)
    operator fun minus(other: FloatingNumber): FloatingNumber = FloatingNumber.fromAny(primitiveValue + other.primitiveValue)


    operator fun times(other: Float): Float = longValue * other
    operator fun times(other: Double): Double = longValue * other

    operator fun times(other: Byte): Byte = (byteValue * other).toByte()
    operator fun times(other: Short): Short = (shortValue * other).toShort()
    operator fun times(other: Int): Int = intValue * other
    operator fun times(other: Long): Long = longValue * other
    abstract operator fun times(other: WholeNumber): WholeNumber //= from(longValue * other.longValue)
    operator fun times(other: FloatingNumber): FloatingNumber = FloatingNumber.fromAny(primitiveValue + other.primitiveValue)


    operator fun div(other: Float): Float = longValue / other
    operator fun div(other: Double): Double = longValue / other

    operator fun div(other: Byte): Byte = (byteValue / other).toByte()
    operator fun div(other: Short): Short = (shortValue / other).toShort()
    operator fun div(other: Int): Int = intValue / other
    operator fun div(other: Long): Long = longValue / other
    abstract operator fun div(other: WholeNumber): WholeNumber //= from(longValue / other.longValue)
    operator fun div(other: FloatingNumber): FloatingNumber = FloatingNumber.fromAny(primitiveValue + other.primitiveValue)


    operator fun rem(other: Float): Float = longValue % other
    operator fun rem(other: Double): Double = longValue % other

    operator fun rem(other: Byte): Byte = (byteValue % other).toByte()
    operator fun rem(other: Short): Short = (shortValue % other).toShort()
    operator fun rem(other: Int): Int = intValue % other
    operator fun rem(other: Long): Long = longValue % other
    abstract operator fun rem(other: WholeNumber): WholeNumber //= from(longValue % other.longValue)
    operator fun rem(other: FloatingNumber): FloatingNumber = FloatingNumber.fromAny(primitiveValue + other.primitiveValue)


    operator fun compareTo(other: Float): Int = longValue.compareTo(other)
    operator fun compareTo(other: Double): Int = longValue.compareTo(other)

    operator fun compareTo(other: Byte): Int = byteValue.compareTo(other)
    operator fun compareTo(other: Short): Int = shortValue.compareTo(other)
    operator fun compareTo(other: Int): Int = intValue.compareTo(other)
    operator fun compareTo(other: Long): Int = longValue.compareTo(other)
    abstract operator fun compareTo(other: WholeNumber): Int //= primitiveValue.compareTo(other.primitiveValue)
    abstract operator fun compareTo(other: FloatingNumber): Int //= primitiveValue.compareTo(other.primitiveValue)

    override fun toByte(): Byte = byteValue
    override fun toFloat(): Float = longValue.toFloat()
    override fun toInt(): Int = intValue
    override fun toLong(): Long = longValue
    override fun toShort(): Short = shortValue
    override fun toChar(): Char = longValue.toChar()
    override fun toDouble(): Double = longValue.toDouble()
/*
    fun checkIntegrity(){
        if(!isInGoodIntegrity)
            throw NumberFormatException("this: \"$this\" tidak memiliki nilai `byteValue`, `shortValue`, `intValue`, dan `longValue` yg sama.")
    }
 */

    override fun clone_(isShallowClone: Boolean): WholeNumber = when(this){
        is ByteWholeNumber -> ByteWholeNumber(byteValue)
        is ShortWholeNumber -> ShortWholeNumber(shortValue)
        is IntWholeNumber -> IntWholeNumber(intValue)
        is LongWholeNumber -> LongWholeNumber(longValue)
        else -> throw UnavailableOperationExc(
            this::class,
            detailMsg = "`WholeNumber` memiliki inheritance lain. Programmer harus meng-override method `clone_`."
        )
    }

    /**
     * Mengecilkan ukuran space pada memory yg digunakan untuk menyimpan [primitiveValue].
     */
    override fun trim(): WholeNumber = when {
        primitiveValue in byteValueRange -> from(byteValue)
        primitiveValue in shortValueRange -> from(shortValue)
        primitiveValue in intValueRange -> from(intValue)
        primitiveValue in longValueRange -> from(longValue)
        else -> throw UnavailableOperationExc(
            this::class,
            "`primitiveValue` : \"$primitiveValue\" memiliki nilai di luar jangkauan angka primitif."
        )
    }

    override fun equals(other: Any?): Boolean = when(other){
        is Byte -> byteValue == other
        is Short -> shortValue == other
        is Int -> intValue == other
        is Long -> longValue == other
        is WholeNumber -> {
            //other.checkIntegrity() //Gak perlu karena setter untuk nilai masing-masing tipe angka private
            byteValue == other.byteValue
        }
        is FloatingNumber -> longValue.compareTo(other.doubleValue) == 0
        else -> super.equals(other)
    }

    override fun hashCode(): Int = primitiveValue.hashCode()
    override fun toString(): String = primitiveValue.toString()
}