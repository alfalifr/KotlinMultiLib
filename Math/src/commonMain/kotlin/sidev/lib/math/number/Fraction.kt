package sidev.lib.math.number

/**
 * Interface umum untuk tipe data pecahan dg format [numerator] / [denominator].
 */
interface Fraction<T: Number>: Number_ {
    val numerator: T
    val denominator: T
    val realNumber: Double
        get()= numerator.toDouble() / denominator.toDouble()

    override fun toByte(): Byte = realNumber.toByte()
    override fun toChar(): Char = realNumber.toChar()
    override fun toDouble(): Double = realNumber
    override fun toFloat(): Float = realNumber.toFloat()
    override fun toInt(): Int = realNumber.toInt()
    override fun toLong(): Long = realNumber.toLong()
    override fun toShort(): Short = realNumber.toShort()

    fun makeCommonFractions(other: Fraction<T>): Pair<Fraction<T>, Fraction<T>>
    fun simply(): Fraction<T>

    operator fun plus(other: Fraction<T>): Fraction<T>
    operator fun minus(other: Fraction<T>): Fraction<T>
    operator fun times(other: Fraction<T>): Fraction<T>
    operator fun div(other: Fraction<T>): Fraction<T>

    operator fun plus(other: Byte): Fraction<T>
    operator fun plus(other: Short): Fraction<T>
    operator fun plus(other: Int): Fraction<T>
    operator fun plus(other: Long): Fraction<T>
    operator fun plus(other: Float): Fraction<T>
    operator fun plus(other: Double): Fraction<T>

    operator fun minus(other: Byte): Fraction<T>
    operator fun minus(other: Short): Fraction<T>
    operator fun minus(other: Int): Fraction<T>
    operator fun minus(other: Long): Fraction<T>
    operator fun minus(other: Float): Fraction<T>
    operator fun minus(other: Double): Fraction<T>

    operator fun times(other: Byte): Fraction<T>
    operator fun times(other: Short): Fraction<T>
    operator fun times(other: Int): Fraction<T>
    operator fun times(other: Long): Fraction<T>
    operator fun times(other: Float): Fraction<T>
    operator fun times(other: Double): Fraction<T>

    operator fun div(other: Byte): Fraction<T>
    operator fun div(other: Short): Fraction<T>
    operator fun div(other: Int): Fraction<T>
    operator fun div(other: Long): Fraction<T>
    operator fun div(other: Float): Fraction<T>
    operator fun div(other: Double): Fraction<T>

//    operator fun rem(other: Fraction<T>): Fraction<T> //Gak masuk akal pecahan punya sisah bilangan.
}