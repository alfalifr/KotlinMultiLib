package sidev.lib.math.arithmetic

import sidev.lib.exception.IllegalStateExc

/**
 * Elemen dasar pada ekuasi yang menghasilkan angka scr individu jika diberi input melalui [calculate].
 */
interface Calculable {
    /**
     * Jumlah input yang diperlukan untuk di-pass ke [calculate]
     */
    val nInput: Int
/*
    fun calculate(vararg nums: Number): Number
    operator fun invoke(vararg nums: Number) = calculate(*nums)
 */

    fun calculate(vararg namedNums: Pair<String, Number>): Number
    operator fun invoke(vararg namedNums: Pair<String, Number>) = calculate(*namedNums)
//    fun getOpposite(): Calculable

    operator fun plus(element: Calculable): Calculable
    operator fun minus(element: Calculable): Calculable
    operator fun times(element: Calculable): Calculable
    operator fun div(element: Calculable): Calculable
    operator fun rem(element: Calculable): Calculable
}


internal object NullCalculable: Calculable {
    override val nInput: Int = 0
    override fun calculate(vararg namedNums: Pair<String, Number>): Number =
        throw IllegalStateExc(detMsg = "Tidak dapat menghitung `NullCalculable`, harap ganti objek ini dengan `Calculable` lainnya.")

    override fun plus(element: Calculable): Calculable =
        throw IllegalStateExc(detMsg = "Tidak dapat menghitung `NullCalculable`, harap ganti objek ini dengan `Calculable` lainnya.")

    override fun minus(element: Calculable): Calculable =
        throw IllegalStateExc(detMsg = "Tidak dapat menghitung `NullCalculable`, harap ganti objek ini dengan `Calculable` lainnya.")

    override fun times(element: Calculable): Calculable =
        throw IllegalStateExc(detMsg = "Tidak dapat menghitung `NullCalculable`, harap ganti objek ini dengan `Calculable` lainnya.")

    override fun div(element: Calculable): Calculable =
        throw IllegalStateExc(detMsg = "Tidak dapat menghitung `NullCalculable`, harap ganti objek ini dengan `Calculable` lainnya.")

    override fun rem(element: Calculable): Calculable =
        throw IllegalStateExc(detMsg = "Tidak dapat menghitung `NullCalculable`, harap ganti objek ini dengan `Calculable` lainnya.")

    override fun toString(): String = "NullCalculable"
}