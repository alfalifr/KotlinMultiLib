package sidev.lib.math.arithmetic

import sidev.lib.exception.IllegalStateExc
import sidev.lib.structure.data.Cloneable

/**
 * Elemen dasar pada ekuasi yang menghasilkan angka scr individu jika diberi input melalui [calculate].
 */
interface Calculable: Cloneable<Calculable> {
    /**
     * Jumlah input yang diperlukan untuk di-pass ke [calculate]
     */
    val nInput: Int
/*
    fun calculate(vararg nums: Number): Number
    operator fun invoke(vararg nums: Number) = calculate(*nums)
 */

    /**
     * Menghitung hasil dari `this` [Calculable] dg mengganti variabel dg nilai sesuai [namedNums].
     * [namedNums] harus menyediakan setiap variabel dg nama berbeda yg ada pada `this`
     * sehingga hasil akhir berupa [Number] dapat dihitung.
     */
    fun calculate(vararg namedNums: Pair<String, Number>): Number

    /**
     * Menghitung hasil dari `this` [Calculable] dg mengganti variabel dg nilai sesuai [namedCalculable].
     * [namedCalculable] tidak harus menyediakan setiap variabel dg nama berbeda yg ada pada `this`.
     * Fungsi ini menghasilkan [Calculable] bentuk lain.
     */
    fun replaceVars(vararg namedCalculable: Pair<String, Calculable>): Calculable

    operator fun invoke(vararg namedNums: Pair<String, Number>): Number = calculate(*namedNums)
//    operator fun invoke(vararg namedCalculable: Pair<String, Calculable>): Calculable = calculate(*namedCalculable)
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

    override fun replaceVars(vararg namedCalculable: Pair<String, Calculable>): Calculable =
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

    override fun clone_(isShallowClone: Boolean): Calculable = this

    override fun toString(): String = "NullCalculable"
}