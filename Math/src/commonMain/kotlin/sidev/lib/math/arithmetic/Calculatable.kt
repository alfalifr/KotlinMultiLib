package sidev.lib.math.arithmetic

/**
 * Elemen dasar pada ekuasi yang menghasilkan angka scr individu, yaitu [Constant] atau [Variable].
 */
interface Calculatable {
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
//    fun getOpposite(): Calculatable
}

internal object NullCalculatable: Calculatable {
    override val nInput: Int = 0
    override fun calculate(vararg namedNums: Pair<String, Number>): Number = 0
}